package com.che.analytics.function;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;

import com.che.analytics.domain.Product;
import com.che.analytics.domain.ProductGrid;

public class GridToProduct extends RichFlatMapFunction<ProductGrid, Product>{

	@Override
	public void flatMap(ProductGrid grid, Collector<Product> out) throws Exception {
		if (grid.getPricingResults().isEmpty()) {
			grid.getProductsToPrice().values().forEach(product -> useProduct(product, out));
		} else {
			grid.getPricingResults().values().forEach(product -> useProduct(product, out));
		}
	}
	
	private void useProduct(Product product, Collector<Product> out) {
		product.setIterationStartTime(System.currentTimeMillis());
		out.collect(product);
	}

}
