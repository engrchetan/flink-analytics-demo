package com.che.analytics.domain;

import java.io.Serializable;

import org.apache.flink.api.java.functions.KeySelector;

public class MarketData implements Serializable {
	public static final KeySelector<MarketData, String> ID_KEY = new KeySelector<MarketData, String>() {

		@Override
		public String getKey(MarketData value) throws Exception {
			return value.getId();
		}
	};
	private String id;
	private double buyPrice;
	private double sellPrice;
	private long timestamp;
	private String type;

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
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MarketData [id=" + id + ", buyPrice=" + buyPrice + ", sellPrice=" + sellPrice + ", timestamp="
				+ timestamp + ", type=" + type + "]";
	}

}
