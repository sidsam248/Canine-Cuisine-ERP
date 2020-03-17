package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dog extends Model {
	
	private final int id;
	private final String breed;
	private int weight;
	private int product_weight;
	private double price;
	
	public Dog(final int id, final String breed, int weight, int product_weight, double price) {
		this.id = id;
		this.breed = breed;
		this.weight = weight;
		this.product_weight = product_weight;
		this.price = price;
	}
	
	public final int getId() {
		return id;
	}
	
	public final String getBreed() {
		return breed;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("weight", String.valueOf(weight));
		
		patchRequest("dogs/" + this.id, params, false);
		
		this.weight = weight;
	}
	
	public int getProductWeight() {
		return product_weight;
	}
	
	public void setProductWeight(int product_weight) {
		Map<String, String> params = new HashMap<>();
		
		params.put("product_weight", String.valueOf(weight));
		
		patchRequest("dogs/" + this.id, params, false);
		
		this.product_weight = product_weight;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("price", String.valueOf(price));
		
		patchRequest("dogs/" + this.id, params, false);
		
		this.price = price;
	}
	
	public static List<Dog> getAllDogs() {
		
		List<Dog> dogs = new ArrayList<>();
		
		String result = getRequest("dogs", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray array = json.getJSONArray("dogs");
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject dog = array.getJSONObject(i);
			
			int id = dog.getInt("id");
			String breed = dog.getString("breed");
			double price = dog.getDouble("price");
			int weight = dog.getInt("weight");
			int product_weight = dog.getInt("product_weight");
			
			dogs.add(new Dog(id, breed, weight, product_weight, price));
		}
		
		return dogs;
	}
	
	public static Dog addNewDog(String breed, int weight, int product_weight, double price) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("breed", breed);
		params.put("weight", String.valueOf(weight));
		params.put("product_weight", String.valueOf(product_weight));
		params.put("price", String.valueOf(price));
		
		String result = postRequest("dogs", params, false);
		
		JSONObject json = new JSONObject(result);
		
		if (!json.getBoolean("success")) {
			System.out.println(json.get("errors"));
			return null;
		}
		
		JSONObject dog = json.getJSONObject("dog");
		
		int id = dog.getInt("id");
		String new_breed = dog.getString("breed");
		int new_weight = dog.getInt("weight");
		int new_product_weight = dog.getInt("product_weight");
		double new_price = dog.getDouble("price");
		
		return new Dog(id, new_breed, new_weight, new_product_weight, new_price);
	}

	public boolean delete() {
		
		String result = deleteRequest("dogs", this.id);
		
		JSONObject json = new JSONObject(result);
		
		return json.getBoolean("success");
	}

}
