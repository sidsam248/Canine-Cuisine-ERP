package com.cc.corpapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Category extends Model {
	
	private final int id;
	private String categoryType;
	
	public Category(final int id, String category_type) {
		this.id = id;
		this.categoryType = category_type;
	}
	
	public final int getId() {
		return id;
	}
	
	public void setCategoryType(String category_type) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("category_type", category_type);
		
		patchRequest("categories/" + this.id, params, false);
		
		this.categoryType = category_type;
	}
	
	public String getCategoryType() {
		return categoryType;
	}
	
	public boolean delete() {
		
		String result = deleteRequest("categories", this.id);
		
		JSONObject json = new JSONObject(result);
		
		return json.getBoolean("success");
		
	}
	
	public static List<Category> getAllCategories() {
		
		List<Category> categories = new ArrayList<>();
		
		String result = getRequest("categories", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray categories_array = json.getJSONArray("categories");
		
		for (int i = 0; i < categories_array.length(); i++) {
			JSONObject category = categories_array.getJSONObject(i);
			
			int id = category.getInt("id");
			String name = category.getString("category_type");
			
			categories.add(new Category(id, name));
		}
		
		return categories;
	}
	
	public static Category addNewCategory(String name) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("category_type", name);
		
		String result = postRequest("categories", params, false);
		
		JSONObject obj = new JSONObject(result);
		
		if (!obj.getBoolean("success")) {
			return null;
			
		}
		
		JSONObject cat = obj.getJSONObject("category");
		
		return new Category(cat.getInt("id"), cat.getString("category_type"));
	}

}
