package com.che.analytics.function;

import java.util.HashMap;
import java.util.Map;

import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

import com.che.analytics.domain.MarketData;
import com.che.analytics.domain.Product;

public class AveragePriceCopier extends RichCoFlatMapFunction<Product, MarketData, Product>{
	private Map<String, MarketData> marketPriceById = new HashMap<>();
	
	@Override
	public void flatMap1(Product value, Collector<Product> out) throws Exception {
		if(marketPriceById.containsKey(value.getPriceDriver())) {
			MarketData marketData = marketPriceById.get(value.getPriceDriver());
			value.setBuyPrice(marketData.getBuyPrice());
			value.setSellPrice(marketData.getSellPrice());
		}
		
		out.collect(value);
	}

	@Override
	public void flatMap2(MarketData value, Collector<Product> out) throws Exception {
		marketPriceById.put(value.getId(), value);
	}

}
