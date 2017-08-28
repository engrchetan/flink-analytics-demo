package com.che.analytics.function;

import org.apache.flink.api.common.functions.RichMapFunction;

import com.che.analytics.domain.Product;

public class IterationIncrementer extends RichMapFunction<Product, Product> {

	@Override
	public Product map(Product value) throws Exception {
		value.setIterationId(value.getIterationId() + 1);
		return value;
	}
}
