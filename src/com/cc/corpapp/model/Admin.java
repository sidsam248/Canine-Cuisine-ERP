package com.cc.corpapp.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.cc.corpapp.Main;

public class Admin extends Model {
	
	private String email;
	
	private String auth_token;
	
	public Admin(String email) {
		this.email = email;
	}
	
	public boolean authenticate(String password) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("email", email);
		params.put("password", password);
		
		String result = postRequest("admins/login", params, true);
		
		JSONObject json = new JSONObject(result);
		
		if (!json.getBoolean("success")) return false;
		
		this.auth_token = json.getString("token");
		
		setCurrentAdmin();
		
		return true;
	}
	
	public void logout() {
		removeCurrentAdmin();
	}
	
	public String getAuthToken() {
		return auth_token;
	}
	
	private void setCurrentAdmin() {
		Main.setCurrentAdmin(this);
	}
	
	private void removeCurrentAdmin() {
		Main.setCurrentAdmin(null);
	}

}
