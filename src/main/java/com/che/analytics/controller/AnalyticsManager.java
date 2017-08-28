package com.che.analytics.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream.ConnectedIterativeStreams;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.che.analytics.data.MarketDataGenerator;
import com.che.analytics.data.ProductConfigData;
import com.che.analytics.domain.MarketData;
import com.che.analytics.domain.Product;
import com.che.analytics.domain.ProductGrid;
import com.che.analytics.function.AveragePriceCopier;
import com.che.analytics.function.CrossAssetNPHardFunction;
import com.che.analytics.function.GridToProduct;
import com.che.analytics.function.IterationIncrementer;
import com.che.analytics.function.IterationResultConsolidator;
import com.che.analytics.function.ModelFilter;
import com.che.analytics.function.SpreadToPrice;

public class AnalyticsManager {
	private AnalyticsResult analyticsResult = new AnalyticsResult();
	private DataStreamSource<ProductGrid> allProductGrid;
	private DataStreamSource<MarketData> marketDataSource;
	private ConnectedIterativeStreams<ProductGrid,Product> connectedIterativeStream;
	private DataStream<ProductGrid> consolidatedResults;
	private DataStream<Product> productStream;
	private Map<String, DataStream<Product>> productStreamByModel = new HashMap<>();
	
	public void startPricing(StreamExecutionEnvironment executionEnvironment) {
		marketDataSource = executionEnvironment.addSource(new MarketDataGenerator(), "MARKET_STREAM");
		allProductGrid = executionEnvironment.fromElements(ProductConfigData.PRODUCT_GRID);
		connectedIterativeStream = allProductGrid.name("PRODUCT_CONFIG_STREAM").setParallelism(1).iterate().withFeedbackType(Product.class);
		consolidatedResults = connectedIterativeStream.flatMap(new IterationResultConsolidator()).name("GRID_RESULT_CONSOLIDATOR").forceNonParallel();
		productStream = consolidatedResults.flatMap(new GridToProduct()).name("DISTRIBUTE_PRODUCTS_TO_PRICE").rebalance();

		DataStream<Product> marketModelPrice = 
				productStream.filter(new ModelFilter("Market")).name("MARKET_MODEL_FILTER")
					.connect(marketDataSource).keyBy(Product.DRIVER_KEY, MarketData.ID_KEY)
					.flatMap(new AveragePriceCopier()).name("Market_PRICE_COPIER");
		productStreamByModel.put("Market", marketModelPrice);
		
		DataStream<Product> slowAlgoPrice = 
				productStream.filter(new ModelFilter("SlowAlgo")).name("SLOW_ALGO_FILTER")
					.connect(marketDataSource).keyBy(Product.DRIVER_KEY, MarketData.ID_KEY)
					.flatMap(new CrossAssetNPHardFunction()).name("SLOW_ALGO_FUNCTION");
		productStreamByModel.put("SlowAlgo", slowAlgoPrice);
		
		DataStream<Product> internalModelPrice = 
				productStream.filter(new ModelFilter("Internal")).name("INTERNAL_MODEL_FILTER")
					.connect(marketModelPrice)
					.keyBy(Product.DRIVER_KEY, Product.ID_KEY)
					.flatMap(new SpreadToPrice()).name("SPREAD_PRICE");
		productStreamByModel.put("Internal", internalModelPrice);
		
		DataStream<Product> allProcessedProductStream = 
				marketModelPrice.union(slowAlgoPrice).union(internalModelPrice)
				.map(new IterationIncrementer())
				.name("ITERATION_INCREMENTER")
				.forceNonParallel();
		
		connectedIterativeStream.closeWith(allProcessedProductStream);
		
		allProcessedProductStream.print().name("PRODUCT_PRICE_RESULT").setParallelism(1);
		consolidatedResults.map(productGrid -> "Iteration" + productGrid.getInterationId() + " finished in " + 
									(productGrid.getIterationEndTime() - productGrid.getIterationStartTime()) + " ms")
						   .print().name("ITERATION_STATISTICS");
		
	}
}
