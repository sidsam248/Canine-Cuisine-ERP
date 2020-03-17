package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Address extends Model {
	
	private final int id;
	private final Customer customer;
	
	private final String name;
	private final String state;
	private int pincode;
	private String city;
	private int house_no;
	private String locality;
	private String street_name;
	
	public Address(final int id, final Customer customer, final String name, final String state, int pincode, String city, int house_no, String locality, String street_name) {
		this.id = id;
		this.customer = customer;
		
		this.name = name;
		this.state = state;
		this.pincode = pincode;
		this.city = city;
		this.house_no = house_no;
		this.locality = locality;
		this.street_name = street_name;
	}
	
	public final int getId() {
		return id;
	}
	
	public final Customer getCustomer() {
		return customer;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getState() {
		return state;
	}
	
	public int getPincode() {
		return pincode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("city", city);
		
		patchRequest("addresses/" + this.id, params, false);
		
		this.city = city;
	}
	
	public void setPincode(int pincode) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("pincode", String.valueOf(pincode));
		
		patchRequest("addresses/" + this.id ,params, false);
		
		this.pincode = pincode;
	}
	
	public int getHouseNo() {
		return house_no;
	}
	
	public void setHouseNo(int house_no) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("house_no", String.valueOf(house_no));
		
		patchRequest("addresses/" + this.id , params, false);
		
		this.house_no = house_no;
	}
	
	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("locality", locality);
		
		patchRequest("addresses/" + this.id, params, false);
		
		this.locality = locality;
	}
	
	public String getStreetName() {
		return street_name;
	}
	
	public void setStreetName(String street_name) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("street_name", street_name);
		
		patchRequest("addresses/" + this.id, params, false);
		
		this.street_name = street_name;
	}
	
	public static List<Address> getAllAddresses() {
		
		List<Address> addresses = new ArrayList<>();
		
		String result = getRequest("addresses", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray addresses_list = json.getJSONArray("addresses");
		
		for (int i = 0; i < addresses_list.length(); i++) {
			
			JSONObject address = addresses_list.getJSONObject(i);
			
			int id = address.getInt("id");
			int customer_id = address.getInt("customer_id");
			
			Customer customer = Customer.getAllCustomers().stream().filter(c -> c.getId() == customer_id).findAny().get();
			
			String name = address.getString("name");
			String state = address.getString("state");
			int pincode = address.getInt("pincode");
			String city = address.getString("city");
			int house_no = address.getInt("house_no");
			String locality = address.getString("locality");
			String street_name = address.getString("street_name");
			
			addresses.add(new Address(id, customer, name, state, pincode, city, house_no, locality, street_name));
		}
		
		return addresses;
	}
	
	

}
