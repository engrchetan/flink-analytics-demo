package com.che.analytics.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductGrid implements Serializable {
	private ConcurrentHashMap<String, Product> productsToPrice = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Product> pricingResults = new ConcurrentHashMap<>();
	private long iterationStartTime;
	private long iterationEndTime;
	private long interationId;
	
	public Map<String, Product> getProductsToPrice() {
		return productsToPrice;
	}
	public void setProductsToPrice(ConcurrentHashMap<String, Product> productsToPrice) {
		this.productsToPrice = productsToPrice;
	}
	public Map<String, Product> getPricingResults() {
		return pricingResults;
	}
	public void setPricingResults(ConcurrentHashMap<String, Product> pricingResults) {
		this.pricingResults = pricingResults;
	}
	public long getIterationStartTime() {
		return iterationStartTime;
	}
	public void setIterationStartTime(long iterationStartTime) {
		this.iterationStartTime = iterationStartTime;
	}
	public long getIterationEndTime() {
		return iterationEndTime;
	}
	public void setIterationEndTime(long iterationEndTime) {
		this.iterationEndTime = iterationEndTime;
	}
	public long getInterationId() {
		return interationId;
	}
	public void setInterationId(long interationId) {
		this.interationId = interationId;
	}
}
