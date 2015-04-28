package be.nvdh.ts.config;

import java.util.List;

/**
 * JSON object mapper for the configuration.
 */
public class Configuration {

	private FetchConfiguration fetcher;
	private List<ReportConfiguration> reporters;
	private String pluginDirectory;

	public FetchConfiguration getFetcher() {
		return fetcher;
	}

	public void setFetcher(FetchConfiguration fetcher) {
		this.fetcher = fetcher;
	}

	public List<ReportConfiguration> getReporters() {
		return reporters;
	}

	public void setReporters(List<ReportConfiguration> reporters) {
		this.reporters = reporters;
	}

	public String getPluginDirectory() {
		return pluginDirectory;
	}

	public void setPluginDirectory(String pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
	}

}
