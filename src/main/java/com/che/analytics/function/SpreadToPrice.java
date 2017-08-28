package com.che.analytics.function;

import java.util.HashMap;
import java.util.Map;

import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

import com.che.analytics.domain.Product;

public class SpreadToPrice extends RichCoFlatMapFunction<Product, Product, Product>{
	private Map<String, Product> driverPriceById = new HashMap<>();
	
	@Override
	public void flatMap1(Product value, Collector<Product> out) throws Exception {
		if (driverPriceById.containsKey(value.getPriceDriver())) {
			Product driverProduct = driverPriceById.get(value.getPriceDriver());
			value.setBuyPrice(driverProduct.getBuyPrice() - value.getSpread());
			value.setSellPrice(driverProduct.getSellPrice() + value.getSpread());
		}
		out.collect(value);
	}

	@Override
	public void flatMap2(Product value, Collector<Product> out) throws Exception {
		driverPriceById.put(value.getId(), value);
	}

}
