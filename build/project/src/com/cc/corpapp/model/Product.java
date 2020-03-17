package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cc.corpapp.model.property.AGE_TYPE;

public class Product extends Model {
	
	private final int id;
	private final int categoryId;
	private String name;
	private AGE_TYPE age_type;
	private double price;
	
	public Product(final int id, final int categoryId, String name, AGE_TYPE age_type, double price) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.age_type = age_type;
		this.price = price;
	}
	
	public final int getId() {
		return id;
	}
	
	public final int getCategoryId() {
		return categoryId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		
		Map<String, String> params = new HashMap<>();
		
		params.put("name", name);
		
		patchRequest("products/" + this.id, params, false);
	}
	
	public AGE_TYPE getAgeType() {
		return age_type;
	}
	
	public void setAgeType(AGE_TYPE age_type) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("age_type", age_type.toString());
		
		patchRequest("products/" + this.id, params, false);
		
		this.age_type = age_type;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("price", String.valueOf(price));
		
		patchRequest("products/" + this.id, params, false);
		
		this.price = price;
	}
	
	public static List<Product> getAllProducts() {
		
		List<Product> products = new ArrayList<>();
		
		String result = getRequest("products", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray products_query_result = json.getJSONArray("products");
		
		for (int i = 0; i < products_query_result.length(); i++) {
			JSONObject product = products_query_result.getJSONObject(i);
			
			int id = product.getInt("id");
			int category_id = product.getInt("category_id");
			
			String name = product.getString("name");
			AGE_TYPE type = AGE_TYPE.valueOf(product.getString("age_type"));
			
			double price = product.getDouble("price");
			
			products.add(new Product(id, category_id, name, type, price));
		}
		
		return products;
	}
	
	public static Product addNewProduct(int cat_id, String name, AGE_TYPE age_type, double price) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("category_id", String.valueOf(cat_id));
		params.put("name", name);
		params.put("age_type", age_type.toString());
		params.put("price", String.valueOf(price));
		
		String result = postRequest("products", params, false);
		
		JSONObject json = new JSONObject(result);
		
		if (!json.getBoolean("success")) {
			System.out.println(json.get("errors"));
			return null;
		}
		
		JSONObject product = json.getJSONObject("product");
		
		int id = product.getInt("id");
		int new_cat_id = product.getInt("category_id");
		String new_name = product.getString("name");
		AGE_TYPE new_age_type = AGE_TYPE.valueOf(product.getString("age_type"));
		double new_price = product.getDouble("price");
		
		return new Product(id, new_cat_id, new_name, new_age_type, new_price);
	}

	public boolean delete() {
		
		String result = deleteRequest("products", this.id);
		
		JSONObject json = new JSONObject(result);
		
		return json.getBoolean("success");
	}

}
