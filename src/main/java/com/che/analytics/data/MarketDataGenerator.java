package com.che.analytics.data;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import com.che.analytics.domain.MarketData;

public class MarketDataGenerator extends RichSourceFunction<MarketData> {
	private volatile boolean isRunning = true;

	@Override
	public void run(SourceContext<MarketData> ctx)
			throws Exception {
		while(isRunning) {
			long seed = System.currentTimeMillis();
			MarketData marketData = randomMarketGenerator(seed, 5, "Stock#A", "EQ", 105.0d);
			if(marketData != null) {
				ctx.collect(marketData);
			}
			
			marketData = randomMarketGenerator(seed, 7, "Stock#B", "EQ", 120.0d);
			if(marketData != null) {
				ctx.collect(marketData);
			}
			
			marketData = randomMarketGenerator(seed, 13, "Oil", "COMMODITY", 30.0d);
			if(marketData != null) {
				ctx.collect(marketData);
			}
		}
	}

	@Override
	public void cancel() {	
		isRunning = false;
	}

	private static MarketData randomMarketGenerator(long seed, int factor, String id, String type, double base) {
		if(seed % factor == 0) {
			MarketData marketData = new MarketData();
			marketData.setId(id);
			marketData.setType(type);
			marketData.setBuyPrice(base - (0.5d * Math.random()));
			marketData.setSellPrice(base + (0.7d * Math.random()));
			marketData.setTimestamp(System.currentTimeMillis());
			return marketData;
		} else {
			return null;
		}
	}
}
