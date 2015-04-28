import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import be.nvdh.ts.config.Configuration;
import be.nvdh.ts.config.FetchConfiguration;
import be.nvdh.ts.config.ReportConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class JacksonTest {

	@Test
	public void testObjectMapping() throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String json = mapper.writeValueAsString(configuration());
		System.out.println(json);
	}
	
	@Test
	public void testParseConfigurationMapping() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Configuration configuration = mapper.readValue("{'fetcher':{'type':'some.class.location.class','config':{}},'reporters':[{'type':'ome.reporter.class.type.class','config':{}}]}", Configuration.class);
		System.out.println(configuration.getFetcher());
	}

	@Test
	public void testParseConfigurationMappingFromFile() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Configuration configuration = mapper.readValue(new File(getClass().getResource("sample.config.json").getPath()), Configuration.class);
		System.out.println(configuration.getFetcher());
	}

	private Configuration configuration() {
		Configuration configuration = new Configuration();
		FetchConfiguration fetcher = new FetchConfiguration();
		fetcher.setType("some.class.location.class");
		fetcher.setConfig(new HashMap<String, String>());
		configuration.setFetcher(fetcher);
		ArrayList<ReportConfiguration> reporters = new ArrayList<ReportConfiguration>();
		ReportConfiguration reporter = new ReportConfiguration();
		reporter.setType("ome.reporter.class.type.class");
		reporter.setConfig(new HashMap<String, String>());
		reporters.add(reporter);
		configuration.setReporters(reporters);
		return configuration;
	}
	
}

