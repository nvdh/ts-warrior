package be.nvdh.ts.report;

import java.util.Map;

import be.nvdh.ts.domain.FetchResult;
import be.nvdh.ts.exception.ReportException;

public interface Reporter {
	
	/**
	 * Init method that is called before the publish to enable the reporter to properly initialize itself with the configuration parameters from the JSON config.
	 * 
	 * @param config The map with the configured key-value pairs
	 */
	void init(Map<String, String> config);
	
	/**
	 * Called when the fetch completed.
	 * 
	 * @param fetchResult The result of the fetch, contains the prestations and metadata.
	 * @param context The context that can be used to pass parameters between Reporters.
	 * @throws ReportException Whenever an exception occurs in the Reporter.
	 */
	void publish(FetchResult fetchResult, Map<String, String> context) throws ReportException;
	
	/**
	 * The name of the Reporter. This name is used to lookup the Reporter by name using the configured reporter type in the JSON config.
	 * 
	 * @return the name of the Reporter
	 */
	String getName();

}
