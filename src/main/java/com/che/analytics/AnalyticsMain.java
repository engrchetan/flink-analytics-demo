/**
 * 
 */
package com.che.analytics;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.che.analytics.controller.AnalyticsManager;

/**
 * @author chetan
 *
 */
public class AnalyticsMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
		executionEnvironment.setBufferTimeout(1); // Low latency!
		
		AnalyticsManager analyticsManager = new AnalyticsManager();
		analyticsManager.startPricing(executionEnvironment);
		
		executionEnvironment.execute();
	}

}
