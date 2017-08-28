package com.che.analytics.function;

import java.util.HashMap;
import java.util.Map;

import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

import com.che.analytics.domain.MarketData;
import com.che.analytics.domain.Product;

public class CrossAssetNPHardFunction extends RichCoFlatMapFunction<Product, MarketData, Product>{
	private Map<String, Product> productByDriver = new HashMap<>();
	
	@Override
	public void flatMap1(Product value, Collector<Product> out) throws Exception {
		productByDriver.put(value.getPriceDriver(), value);
	}

	@Override
	public void flatMap2(MarketData marketData, Collector<Product> out) throws Exception {
		if(productByDriver.containsKey(marketData.getId())) {
			Product value = productByDriver.remove(marketData.getId());
			Thread.sleep(10); // Abstract slow computations
			value.setBuyPrice(marketData.getBuyPrice() * 1.33);
			value.setSellPrice(marketData.getSellPrice() * 1.33);
			out.collect(value);
		}	
	}

}
