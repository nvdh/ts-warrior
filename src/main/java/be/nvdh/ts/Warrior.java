package be.nvdh.ts;

import static java.util.ServiceLoader.load;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import be.nvdh.ts.config.Configuration;
import be.nvdh.ts.config.ReportConfiguration;
import be.nvdh.ts.domain.FetchResult;
import be.nvdh.ts.exception.FetchException;
import be.nvdh.ts.exception.ReportException;
import be.nvdh.ts.fetch.Fetcher;
import be.nvdh.ts.report.Reporter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Warrior {

	private static final String DEFAULT_PLUGIN_DIRECTORY = "plugins";

	private Configuration configuration;
	private Date dateToFetch;
	
	private Fetcher fetcher;
	private List<Reporter> reporters;
	
	private List<Fetcher> availableFetchers = new ArrayList<Fetcher>();
	private List<Reporter> availableReporters = new ArrayList<Reporter>();
	
	private static final Logger log  = LogManager.getLogger(Warrior.class);
	
	public Warrior(String configurationFile, Date dateToFetch) {
		this.dateToFetch = dateToFetch;
		this.configuration = parseConfiguration(configurationFile);
	}

	public void start() {
		init();
		log.info("Start fetching.");
		FetchResult result = fetch();
		log.info("Fetching completed. Creating report.");
		report(result);
		log.info("Succesfully completed.");
	}

	private void init() {
		loadPlugins();
		fetcher = selectFetcher();
		reporters = selectReporters();
		log.info("Using fetcher: " + fetcher + " and reporters: " + reporters);
	}
	
	private FetchResult fetch() {
		try {
			Map<String, String> config = configuration.getFetcher().getConfig();
			fetcher.init(config);
			return fetcher.fetch(dateToFetch);
		} catch (FetchException e) {
			throw new RuntimeException("A problem occured while fetching the data.", e);
		}
	}
	
	private void report(FetchResult fetchResult) {
		Map<String, String> context = new HashMap<String, String>();
		for (Reporter reporter : reporters) {
			try {
				reporter.init(config(reporter));
				reporter.publish(fetchResult, context);
			} catch (ReportException e) {
				log.error("Problem occured while reporting." + e.getMessage());
			}
		}
	}

	private Map<String, String> config(Reporter reporter) {
		Map<String, String> config = new HashMap<String, String>();
		List<ReportConfiguration> reporterConfigurationList = configuration.getReporters();
		for (ReportConfiguration reportConfiguration : reporterConfigurationList) {
			if (reporter.getName().equalsIgnoreCase(reportConfiguration.getType())){
				return reportConfiguration.getConfig();
			}
		}
		return config;
	}

	private List<Reporter> selectReporters() {
		List<ReportConfiguration> reportConfigurations = configuration.getReporters();
		List<Reporter> reporters = new ArrayList<Reporter>();
		
		for (ReportConfiguration reportConfiguration : reportConfigurations){
			Reporter reporter = reporterFor(reportConfiguration.getType());
			if (reporter != null){
				reporters.add(reporter);
			}
		}
		
		return reporters;
	}

	private Reporter reporterFor(String type) {
		for (Reporter availableReporter : availableReporters){
			if (availableReporter.getName().equalsIgnoreCase(type)){
				return availableReporter;
			}
		}
		return null;
	}

	private Fetcher selectFetcher() {
		String fetcherType = configuration.getFetcher().getType();
		for (Fetcher availableFetcher : availableFetchers) {
			if (matches(availableFetcher, fetcherType) ){
				return availableFetcher;
			}
		}
		throw new IllegalArgumentException(errorMessageNoFetcher());
	}

	private boolean matches(Fetcher fetcher, String fetcherType) {
		return !isEmpty(fetcher.getName()) && (fetcher.getName().equals(fetcherType) || fetcher.getClass().getName().equals(fetcherType));
	}
	
	private void loadPlugins() {
		try {
			log.info("loading plugins..");
			URLClassLoader urlClassLoader = new URLClassLoader(urls(configuration.getPluginDirectory()));
			loadAvailableFetchers(urlClassLoader);
			loadAvailableReporters(urlClassLoader);
		} catch (MalformedURLException e) {
			new RuntimeException("Error loading plugins from plugin directory ", e);
		}
	}

	private void loadAvailableReporters(URLClassLoader urlClassLoader) {
		ServiceLoader<Reporter> fetcherServiceLoader = load(Reporter.class, urlClassLoader);
		Iterator<Reporter> reporters = fetcherServiceLoader.iterator();
		while (reporters.hasNext()){
			availableReporters.add(reporters.next());
		}
	}

	private void loadAvailableFetchers(URLClassLoader urlClassLoader) {
		ServiceLoader<Fetcher> fetcherServiceLoader = load(Fetcher.class, urlClassLoader);
		Iterator<Fetcher> fetchers = fetcherServiceLoader.iterator();
		while (fetchers.hasNext()){
			availableFetchers.add(fetchers.next());
		}
	}

	private Configuration parseConfiguration(String configurationFile) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			return mapper.readValue(new File(configurationFile), Configuration.class);
		} catch (JsonParseException e) {
			throw new RuntimeException("The JSON config you provided could not be parsed.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Could not interprete the JSON config you provided, check exception message for more details.", e);
		} catch (IOException e) {
			throw new RuntimeException("There was a problem reading your configuration file: " + configurationFile, e);
		}
	}
	
	private String errorMessageNoFetcher() {
		return "No Fetcher found for the configured fetcher " + configuration.getFetcher().getType() + ". Make sure the fetcher plugins are loaded and there are no typos in the name of the fetcher. Available fetchers are: " + availableFetchers;
	}
	
	private URL[] urls(String pluginDirectory) throws MalformedURLException {
		Collection<File> jars = listFiles(new File(pluginLocation(pluginDirectory)), new String[]{"jar"}, true);
		return urlsFrom(jars).toArray(new URL[jars.size()]);
	}

	private List<URL> urlsFrom(Collection<File> jars) throws MalformedURLException {
		List<URL> urls = new ArrayList<URL>();
		for (File jar : jars) {
			urls.add(jar.toURI().toURL());
		}
		return urls;
	}
	
	private String pluginLocation(String pluginDirectory) {
		return isEmpty(pluginDirectory) ? DEFAULT_PLUGIN_DIRECTORY : pluginDirectory;
	}
	
}
