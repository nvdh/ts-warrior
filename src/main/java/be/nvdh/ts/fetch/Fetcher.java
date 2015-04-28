package be.nvdh.ts.fetch;

import java.util.Date;
import java.util.Map;

import be.nvdh.ts.domain.FetchResult;
import be.nvdh.ts.exception.FetchException;

public interface Fetcher {

	/**
	 * Fetches the prestations for the given date or time interval around the
	 * date, e.g. the month. This method is called only once, so it should fetch
	 * everything.
	 * 
	 * @param dateToFetch The date to fetch
	 * @param config The configuration for the fetcher
	 * @return FetchResult with a list of the prestations for the specified date
	 * @throws FetchException
	 *             when something goes wrong
	 */
	FetchResult fetch(Date dateToFetch) throws FetchException;

	/**
	 * Initialize the Fetcher with the configuration from the JSON config file. 
	 * Keys and values should be documented per implementation.
	 * 
	 * @param config The key-value pairs of the configuration
	 */
	void init(Map<String, String> config);
	
	/**
	 * Returns the name of the Fetcher that can be used in the JSON
	 * configuration file.
	 * 
	 * @return the name of the fetcher
	 */
	String getName();

}
