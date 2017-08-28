/**
 * 
 */
package com.che.analytics.domain;

import java.io.Serializable;

import org.apache.flink.api.java.functions.KeySelector;

/**
 * @author chetan
 *
 */
public class Product implements Serializable {
	
	public static final KeySelector<Product, String> DRIVER_KEY = new KeySelector<Product, String>() {

		@Override
		public String getKey(Product value) throws Exception {
			return value.getPriceDriver();
		}
	};

	public static final KeySelector<Product, String> ID_KEY = new KeySelector<Product, String>() {

		@Override
		public String getKey(Product value) throws Exception {
			return value.getId();
		}
	};

	// Input OR user configurations
	private String id;
	private String priceDriver;
	private String strategy;
	private double spread;

	// calculations
	private double risk;
	private double buyPrice;
	private double sellPrice;
	private long iterationId;
	private long iterationStartTime;
	private long iterationEndTime;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getPriceDriver() {
		return priceDriver;
	}
	public void setPriceDriver(String priceDriver) {
		this.priceDriver = priceDriver;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public double getRisk() {
		return risk;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
	public double getSpread() {
		return spread;
	}
	public void setSpread(double spread) {
		this.spread = spread;
	}
	public long getIterationId() {
		return iterationId;
	}
	public void setIterationId(long iterationId) {
		this.iterationId = iterationId;
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
	@Override
	public String toString() {
		return "Product [id=" + id + ", priceDriver=" + priceDriver + ", strategy=" + strategy + ", spread=" + spread
				+ ", risk=" + risk + ", buyPrice=" + buyPrice + ", sellPrice=" + sellPrice + ", iterationId="
				+ iterationId + ", iterationStartTime=" + iterationStartTime + ", iterationEndTime=" + iterationEndTime
				+ "]";
	}

}
