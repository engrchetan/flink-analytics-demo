package com.che.analytics.function;

import org.apache.flink.api.common.functions.RichFilterFunction;

import com.che.analytics.domain.Product;

public class ModelFilter extends RichFilterFunction<Product> {
	private final String model;
		
	public ModelFilter(String model) {
		this.model = model;
	}

	@Override
	public boolean filter(Product value) throws Exception {
		return model.equals(value.getStrategy());
	}

}
