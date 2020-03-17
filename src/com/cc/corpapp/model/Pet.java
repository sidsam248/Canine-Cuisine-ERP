package com.cc.corpapp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cc.corpapp.model.property.AGE_TYPE;
import com.cc.corpapp.model.property.GENDER;

public class Pet extends Model {
	
	private final int id;
	private final Customer customer;
	
	private final String name;
	private final String breed;
	private int weight;
	private final Date birthday;
	
	private AGE_TYPE ageType;
	private final GENDER gender;
	
	private String breedRef;
	
	public Pet(final int id, final Customer customer,final String name, final String breed, int weight, final Date birthday, AGE_TYPE ageType, final GENDER gender, String breedRef) {
		this.id = id;
		this.customer = customer;
		this.name = name;
		this.breed = breed;
		this.weight = weight;
		this.birthday = birthday;
		this.ageType = ageType;
		this.gender = gender;
		this.breedRef = breedRef;
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
	
	public final String getBreed() {
		return breed;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("weight", String.valueOf(weight));
		
		patchRequest("pets/" + this.id, params, false);
		
		this.weight = weight;
	}
	
	public final Date getBirthday() {
		return birthday;
	}
	
	public AGE_TYPE getAgeType() {
		return ageType;
	}
	
	public void setAgeType(AGE_TYPE age_type) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("age_type", age_type.toString());
		
		patchRequest("pets/" + this.id, params, false);
		
		this.ageType = age_type;
	}
	
	public final GENDER getGender() {
		return gender;
	}
	
	public String getBreedRef() {
		return breedRef;
	}
	
	public void setBreedRef(String breed_ref) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("breed_ref", breed_ref);
		
		patchRequest("pets/" + this.id, params, false);
		
		this.breedRef = breed_ref;
	}
	
	public static List<Pet> getAllPets() {
		
		List<Pet> pets = new ArrayList<>();
		
		try {
			
			String result = getRequest("pets", null, false);
			
			SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			
			JSONObject obj = new JSONObject(result);
			
			JSONArray result_query_list = obj.getJSONArray("pets");
			
			for (int i = 0; i < result_query_list.length(); i++) {
				
				JSONObject pet = result_query_list.getJSONObject(i);
				
				int id = pet.getInt("id");
				
				int customer_id = pet.getInt("customer_id");
				Customer customer = Customer.getAllCustomers().stream().filter(c -> c.getId() == customer_id).findFirst().get();
				
				String name = pet.getString("name");
				String breed = pet.getString("breed");
				int weight = !pet.isNull("weight") ? pet.getInt("weight") : 0;
				Date birthday = date_format.parse(pet.getString("birthday"));
				AGE_TYPE age_type = AGE_TYPE.valueOf(pet.getString("age_type"));
				GENDER gender = GENDER.valueOf(pet.getString("gender"));
				String breed_ref = !pet.isNull("breed_ref") ? pet.getString("breed_ref") : "";
				
				pets.add(new Pet(id, customer, name, breed, weight, birthday, age_type, gender, breed_ref));
			}
			
			return pets;
			
		} catch (JSONException | ParseException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
