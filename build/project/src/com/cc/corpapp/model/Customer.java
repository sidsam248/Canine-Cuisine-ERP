package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Customer extends Model {
	
	private final int id;
	private String email;
	private long phone_no;
	
	private String first_name;
	private String last_name;
	
	public Customer(int id, String email, long phone_no, String first_name, String last_name) {
		this.id = id;
		this.email = email;
		this.phone_no = phone_no;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("email", email);
		
		patchRequest("customers/" + this.id, params, false);
		
		this.email = email;
	}
	
	public long getPhoneNo() {
		return phone_no;
	}
	
	public void setPhoneNo(long phone_no) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("phone_no", String.valueOf(phone_no));
		
		patchRequest("customers/" + this.id, params, false);
		
		this.phone_no = phone_no;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public static int getCustomerSize() {
		
		String result = getRequest("customers", null, false);
		
		JSONObject json = new JSONObject(result);
		
		return json.getJSONArray("customers").length();
	}
	
	public static List<Customer> getAllCustomers() {
		
		List<Customer> customers = new ArrayList<>();
		
		String result = getRequest("customers", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray customers_query = json.getJSONArray("customers");
		
		for (int i = 0; i < customers_query.length(); i++) {
			JSONObject customer = customers_query.getJSONObject(i);
			
			int id = customer.getInt("id");
			
			String email = customer.getString("email");
			long phone_no = customer.getLong("phone_no");
			
			String first_name = customer.getString("first_name");
			String last_name = customer.getString("last_name");
			
			customers.add(new Customer(id, email, phone_no, first_name, last_name));
			
		}
		
		return customers;
	}

}
