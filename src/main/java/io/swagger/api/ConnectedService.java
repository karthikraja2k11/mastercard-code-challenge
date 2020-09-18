package io.swagger.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ConnectedService {

	private ConnectedServiceHelper helper;
	
	private Map<String, Set<String>> citiesMap;
	
	public ConnectedService() {
		helper = new ConnectedServiceHelper();
		citiesMap = helper.getCitiesMap();
	}
	
	public boolean areConnected(String origin, String destination) {
		boolean isRouteAvailable = searchByKey(origin, destination, new ArrayList<String>());
		if(!isRouteAvailable) {
			isRouteAvailable = searchByKey(destination, origin, new ArrayList<String>());
		}
		return isRouteAvailable;
	}
	
	private boolean searchByKey(String origin, String destination, List<String> visitedRoutes) {
//		if(null == destination)
		if(citiesMap.containsKey(origin)) {
			Set<String> connections = citiesMap.get(origin);
			visitedRoutes.add(origin);
			if(null != connections) {
				if(connections.contains(destination)) {
					return true;
				} else {
					for (String connection : connections) {
						if(!visitedRoutes.contains(connection) && citiesMap.containsKey(connection)) {
							boolean isMatch = searchByKey(connection, destination, visitedRoutes);
							if(isMatch) {
								return true;
							} else {
								continue;
							}
						} else {
//							return false;
							continue;
						}
					}
				}
			}
		}
		return false;
	}
	
}
