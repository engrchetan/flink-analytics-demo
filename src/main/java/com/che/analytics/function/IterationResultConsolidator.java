package com.che.analytics.function;

import java.util.HashMap;
import java.util.Map;

import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

import com.che.analytics.domain.Product;
import com.che.analytics.domain.ProductGrid;

public class IterationResultConsolidator extends RichCoFlatMapFunction<ProductGrid, Product, ProductGrid>{
	private ProductGrid initialGrid = null;
	private Map<String, Product> productPrice = new HashMap<>(); // Consolidate results
	
	
	@Override
	public void flatMap1(ProductGrid grid, Collector<ProductGrid> out) throws Exception {
		if(initialGrid == null) {
			out.collect(grid); // Start first iteration
		}
		initialGrid = grid;
	}

	@Override
	public void flatMap2(Product product, Collector<ProductGrid> out) throws Exception {
		productPrice.put(product.getId(), product);
		notifyIfAggregated(out);
	}

	private void notifyIfAggregated(Collector<ProductGrid> out) {
		if (initialGrid != null && initialGrid.getProductsToPrice().size() == productPrice.size()) {
			initialGrid.getPricingResults().clear();
			initialGrid.getPricingResults().putAll(productPrice);
			Product firstProduct = productPrice.values().iterator().next();
			initialGrid.setIterationStartTime(firstProduct.getIterationStartTime());
			initialGrid.setIterationEndTime(System.currentTimeMillis());
			initialGrid.setInterationId(firstProduct.getIterationId());
			productPrice.clear();
			out.collect(initialGrid); // Feedback for next iteration
		}
	}

}
