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

public class DailyOrder extends Model {
	
	private final int id;
	private final Customer customer;
	private final Pet pet;
	private final Product product;
	private Address address;
	
	private final Date startDate;
	private Date tillDate;
	
	private Date holdStartDate;
	private Date holdEndDate;
	
	private double price;
	
	public DailyOrder(final int id, final Customer customer, final Pet pet, final Product product, Address address, final Date start_date, Date till_date, Date hold_start_date, Date hold_end_date, double price) {
		this.id = id;
		this.customer = customer;
		this.pet = pet;
		this.product = product;
		this.address = address;
		
		this.startDate = start_date;
		this.tillDate = till_date;
		
		this.holdStartDate = hold_start_date;
		this.holdEndDate = hold_end_date;
		
		this.price = price;
	}
	
	public final int getId() {
		return id;
	}
	
	public final Customer getCustomer() {
		return customer;
	}

	public final Pet getPet() {
		return pet;
	}

	public final Product getProduct() {
		return product;
	}

	public Address getAddress() {
		return address;
	}
	
	public String getCustomerName() {
		return customer.getFirstName() + " " + customer.getLastName();
	}
	
	public String getPetName() {
		return pet.getName();
	}
	
	public String getProductName() {
		return product.getName();
	}
	
	public String getAddressName() {
		return address.getName();
	}
	
	public void setAddress(Address a) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("address_id", String.valueOf(a.getId()));
		
		patchRequest("daily_orders/" + this.id, params, false);
		
		this.address = a;
	}

	public final Date getStartDate() {
		return startDate;
	}

	public Date getTillDate() {
		return tillDate;
	}
	
	public void setTillDate(Date till_date) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("till_date", String.valueOf(till_date));
		
		patchRequest("daily_orders/" + this.id, params, false);
		
		this.tillDate = till_date;
	}

	public Date getHoldStartDate() {
		return holdStartDate;
	}

	public void setHoldStartDate(Date hold_start_date) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("hold_start_date", String.valueOf(hold_start_date));
		
		patchRequest("daily_orders/" + this.id, params, false);
		
		this.holdStartDate = hold_start_date;
	}

	public Date getHoldEndDate() {
		return holdEndDate;
	}

	public void setHoldEndDate(Date hold_end_date) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("hold_end_date", String.valueOf(hold_end_date));
		
		patchRequest("daily_orders/" + this.id, params, false);
		
		this.holdEndDate = hold_end_date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("price", String.valueOf(price));
		
		patchRequest("daily_orders/" + this.id, params, false);
		
		this.price = price;
	}
	
	public static int getDailyOrderSize() {
		
		String result = getRequest("valid_orders", null, false);
		
		JSONObject json = new JSONObject(result);
		
		return json.getJSONArray("daily_orders").length();	
	}
	
	public static List<DailyOrder> getAllDailyOrders() {
		
		List<DailyOrder> daily_orders = new ArrayList<>();
		
		try {
			
			String result = getRequest("daily_orders", null, false);
			
			JSONObject json = new JSONObject(result);
			
			JSONArray daily_orders_query = json.getJSONArray("daily_orders");
			
			for (int i = 0; i < daily_orders_query.length(); i++) {
				
				JSONObject obj = daily_orders_query.getJSONObject(i);
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
				
				daily_orders.add(new DailyOrder(id, customer, pet, product, address, start_date, till_date, hold_start_date, hold_end_date, price));
			}
			
			return daily_orders;
			
		} catch (JSONException | ParseException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static List<DailyOrder> getAllValidDailyOrders() {
		
		List<DailyOrder> daily_orders = new ArrayList<>();
		
		try {
		
		String result = getRequest("valid_orders", null, false);
		
		JSONObject json = new JSONObject(result);
		
		JSONArray array = json.getJSONArray("daily_orders");
		
		for(int i = 0; i < array.length(); i++) {
			
			JSONObject obj = array.getJSONObject(i);
			
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
			
			daily_orders.add(new DailyOrder(id, customer, pet, product, address, start_date, till_date, hold_start_date, hold_end_date, price));
			
		}
		
		return daily_orders;
		
		} catch(JSONException | ParseException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	public DeliveryExecutive getDeliveryExecutive() {
		
		Map<String, String> params = new HashMap<>();
		
		params.put("daily_order_id", String.valueOf(this.id));
		
		String result = getRequest("get_del_exec_for_daily_order", params, false);
		
		JSONObject del_exec = new JSONObject(result);
		
		if (!del_exec.getBoolean("success")) return null;
		
		return new DeliveryExecutive(del_exec.getInt("id"), del_exec.getString("email"));
		
	}

}
