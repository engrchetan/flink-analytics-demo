package com.che.analytics.data;

import com.che.analytics.domain.Product;
import com.che.analytics.domain.ProductGrid;

public class ProductConfigData {

	public static final ProductGrid PRODUCT_GRID = new ProductGrid();
	
	static {
		addProductsToGrid();
	}

	private static void addProductsToGrid() {
		createProduct("Product#1", "Stock#A", "Market", 0.0);
		createProduct("Product#2", "Stock#B", "Market", 0.0);
		createProduct("Product#3", "Product#1", "Internal", 0.5);
		createProduct("Product#4", "Oil", "SlowAlgo", 0.8);
	}

	private static void createProduct(String id, String source, String model, double spread) {
		Product product = new Product();
		product.setId(id);
		product.setPriceDriver(source);
		product.setStrategy(model);
		product.setSpread(spread);
		product.setIterationId(1);
		
		PRODUCT_GRID.getProductsToPrice().put(id, product);
	}
	
}
