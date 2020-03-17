package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeliveryExecutive extends Model {
	
	private final int id;
	private String email;
	
	private double current_lat, current_lng;
	
	public DeliveryExecutive(final int id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public final int getId() {
		return id;
	}
	
	public double getCurrentLat() {
		return current_lat;
	}
	
	public double getCurrentLng() {
		return current_lng;
	}
	
	public String getEmail() {
		return email;
	}

	public static List<DeliveryExecutive> getAllDeliveryExecutives() {
		
		List<DeliveryExecutive> delivery_executives = new ArrayList<>();
		
		String result = getRequest("delivery_executives", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray array = json.getJSONArray("delivery_executives");
		
		for (int i = 0; i < array.length(); i++) {
			
			JSONObject del_exec = array.getJSONObject(i);
			
			delivery_executives.add(new DeliveryExecutive(del_exec.getInt("id"), del_exec.getString("email")));
		}
		
		return delivery_executives;
	}

}
