package io.swagger.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectedServiceHelper {

	private Map<String, Set<String>> citiesMap;
	
    private static final Logger logger = LoggerFactory.getLogger(ConnectedServiceHelper.class);

	public ConnectedServiceHelper() {
		if(null == citiesMap) {
			citiesMap = new HashMap<String, Set<String>>();
			loadCities();
		}
	}
	
	public Map<String, Set<String>> getCitiesMap() {
		return citiesMap;
	}
	
	private void loadCities() {
		InputStream iStream = ConnectedService.class.getResourceAsStream("/city.txt");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(iStream))) {
			String currLine;
			while ((currLine = br.readLine()) != null) {
				String[] values = currLine.split(",");
				if(null != values && values.length == 2) {
					addRoute(values[0].trim(), values[1].trim());
				}
	        }
		} catch(IOException ioEx) {
			logger.error(ioEx.getMessage(), ioEx);
		}
	}
	
	private void addRoute(String origin, String destination) {
		if(citiesMap.containsKey(origin)) {
			citiesMap.get(origin).add(destination);
		} else {
			citiesMap.put(origin, Stream.of(destination).collect(Collectors.toSet()));
		}
		if(citiesMap.containsKey(destination)) {
			citiesMap.get(destination).add(origin);
		} else {
			citiesMap.put(destination, Stream.of(origin).collect(Collectors.toSet()));
		}
	}


}
