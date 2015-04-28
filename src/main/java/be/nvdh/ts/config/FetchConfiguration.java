package be.nvdh.ts.config;

import java.util.Map;

/**
 * JSON object mapper for the fetcher configuration.
 */
public class FetchConfiguration {
	
	private String type;
	private Map<String, String> config;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getConfig() {
		return config;
	}
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

}
