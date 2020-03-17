package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Route extends Model {
	
	private final int id;
	private String route_name;
	
	public Route(final int id, String route_name) {
		this.id = id;
		this.route_name = route_name;
	}
	
	public final int getId() {
		return id;
	}
	
	public String getName() {
		return route_name;
	}

	public static List<Route> getAllRoutes() {
		
		List<Route> routes = new ArrayList<>();
		
		String result = getRequest("routes", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray routes_json = json.getJSONArray("routes");
		
		for (int i = 0; i < routes_json.length(); i++) {
			
			JSONObject route_json = routes_json.getJSONObject(i);
			
			final int id = route_json.getInt("id");
			String name = route_json.getString("name");
			
			Route route = new Route(id, name);
			
			routes.add(route);
		}
		
		return routes;
		
	}

	public static Route addNewRoute(String name) {
		Map<String, String> params = new HashMap<>();
		
		params.put("name", name);
		
		String result = postRequest("routes", params, false);
		
		JSONObject obj = new JSONObject(result);
		
		if (!obj.getBoolean("success")) {
			return null;
			
		}
		
		JSONObject route = obj.getJSONObject("route");
		
		return new Route(route.getInt("id"), route.getString("name"));
	}
	
	public void assign(String name, int id) {
		Map<String, String> params = new HashMap<>();
		
		params.put("name", name);
		params.put("id", String.valueOf(id));
		params.put("route_id", String.valueOf(this.id));
		
		postRequest("assign", params, false);
	}
	
	public void unassign(String name, int id) {
		Map<String, String> params = new HashMap<>();
		
		params.put("name", name);
		params.put("id", String.valueOf(id));
		params.put("route_id", String.valueOf(this.id));
		
		postRequest("unassign", params, false);
	}

}
