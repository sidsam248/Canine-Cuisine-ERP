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

public class AssignedDailyOrder extends Model {
	
	private DeliveryExecutive del;
	private List<DailyOrder> orders;
	
	public AssignedDailyOrder(DeliveryExecutive del, List<DailyOrder> orders) {
		this.del = del;
		this.orders = orders;
	}
	
	public DeliveryExecutive getDeliveryExecutive() {
		return del;
	}
	
	public List<DailyOrder> getDailyOrders() {
		return orders;
	}
	
	public String getDeliveryExecutiveEmail() {
		return del.getEmail();
	}
	
	public static List<DailyOrder> getAssignedDailyOrderById(int del_id) {
		
		List<DailyOrder> assigned_daily_orders = new ArrayList<>();
		
		try {
		
			String result = getRequest("assigned_daily_orders/" + del_id, null, false);
			
			JSONObject json = new JSONObject(result);
			
			JSONArray assigned_daily_orders_query = json.getJSONArray("assigned_orders");
			
			for (int i = 0; i < assigned_daily_orders_query.length(); i++) {
				
				JSONObject obj = assigned_daily_orders_query.getJSONObject(i);
				SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
				
				int id = obj.getInt("id");
				
				int customer_id = obj.getInt("customer_id");
				Customer customer = Customer.getAllCustomers().stream().filter(c -> c.getId() == customer_id).findFirst().get();
				
				int product_id = obj.getInt("product_id");
				Product product = Product.getAllProducts().stream().filter(p -> p.getId() == product_id).findFirst().get();
				
				int pet_id = obj.getInt("pet_id");
				Pet pet = Pet.getAllPets().stream().filter(p -> p.getId() == pet_id).findFirst().get();
				
				int address_id = obj.getInt("address_id");
				Address address = Address.getAllAddresses().stream().filter(a -> a.getId() == address_id).findFirst().get();
				
				double price = obj.getDouble("price");
				
				Date start_date = date_format.parse(obj.getString("start"));
				Date till_date = date_format.parse(obj.getString("till"));
				
				Date hold_start_date = !obj.isNull(String.valueOf(obj.get("hold_start"))) ? date_format.parse(obj.getString("hold_start")) : null;
				Date hold_end_date = !obj.isNull(String.valueOf(obj.get("hold_end"))) ? date_format.parse(obj.getString("hold_end")) : null;
				
				assigned_daily_orders.add(new DailyOrder(id, customer, pet, product, address, start_date, till_date, hold_start_date, hold_end_date, price));
			}
		
		} catch (JSONException | ParseException e) {
			
			e.printStackTrace();
			
		}
		
		return assigned_daily_orders;
	}
	
	public static void assign(DailyOrder order, DeliveryExecutive exec) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("delivery_executive_id", String.valueOf(exec.getId()));
		params.put("daily_order_id", String.valueOf(order.getId()));
		
		postRequest("assigned_daily_orders", params, false);
		
	}
	
	public static boolean unassign(DailyOrder order, DeliveryExecutive exec) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("delivery_executive_id", String.valueOf(exec.getId()));
		params.put("daily_order_id", String.valueOf(order.getId()));
		
		String result = deleteRequest("unassign_del_exec_from_daily_order", params);
		
		JSONObject json = new JSONObject(result);
		
		return json.getBoolean("success");
	}

}
