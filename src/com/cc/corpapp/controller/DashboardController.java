package com.cc.corpapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Address;
import com.cc.corpapp.model.Customer;
import com.cc.corpapp.model.DailyOrder;
import com.cc.corpapp.model.Dog;
import com.cc.corpapp.model.Pet;
import com.cc.corpapp.model.Product;
import com.cc.corpapp.tasks.DashboardControllerTask;
import com.cc.corpapp.tasks.TimerHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class DashboardController extends Controller {
	
	private Scene dashboard;
	
	private DashboardControllerTask task;
	private Timer timer;

	@Override
	public void initView() throws IOException {
		
		task = new DashboardControllerTask(this);
		timer = new Timer();
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/dashboard.fxml"));
		
		dashboard = new Scene(root);
		
		getStage().setScene(dashboard);
		
		timer.scheduleAtFixedRate(task, 1000, 1000 * 60);
		
		TimerHandler.addTimer(timer);
		
	}
	
	@FXML
	public void initDailyOrdersView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getDailyOrdersController().initView();
	}
	
	@FXML
	public void initCustomersView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getCustomersController().initView();
	}
	
	@FXML
	public void initProductsView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getProductsController().initView();
	}
	
	@FXML
	public void initDogsView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getDogsController().initView();
	}
	
	@FXML
	public void initCategoriesView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getCategoriesController().initView();
	}
	
	@FXML
	public void initDeliveryExecutivesView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getDeliveryExecutivesController().initView();
	}
	
	@FXML
	public void initRoutesView() throws IOException {
		TimerHandler.stopAllTimers();
		Main.getRoutesController().initView();
	}
	
	@FXML
	public void createDispatchList() {
		List<DailyOrder> daily_orders = DailyOrder.getAllValidDailyOrders();
		
		DirectoryChooser chooser = new DirectoryChooser();
		
		chooser.setTitle("Select where you want to save the dispatch list");
		
		File dir = chooser.showDialog(getStage().getOwner());
		FileOutputStream out = null;
		
		Workbook wb = new HSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Dispatch List " + LocalDate.now().toString());
		
		Row rows;
		
		List<Customer> customers = Customer.getAllCustomers();
		List<Pet> pets = Pet.getAllPets();
		List<Product> products = Product.getAllProducts();
		List<Address> addresses = Address.getAllAddresses();
		List<Dog> dogs = Dog.getAllDogs();
		
		Map<Integer, List<String>> dispatch_list = new HashMap<>();
		
		String[] headers = {"id", "customer name", "pet name", "product", "extras", "weight", "locality", "order packed", "order dispatched"};
		
		dispatch_list.put(0, Arrays.asList(headers));
		
		for (int i = 0; i < daily_orders.size(); i++) {
			
			DailyOrder order = daily_orders.get(i);
			
			int id = order.getId();
			
			String customer_name = customers.stream().filter(c -> c.getId() == order.getCustomer().getId()).findFirst().get().getFirstName();
			
			String pet_name = pets.stream().filter(p -> p.getId() == order.getPet().getId()).findFirst().get().getName();
			
			String product_name = products.stream().filter(p -> p.getId() == order.getProduct().getId()).findFirst().get().getName();

			String extras = "";

			Pet pet_obj = pets.stream().filter(p -> p.getId() == order.getPet().getId()).findFirst().get();
			int weight = dogs.stream().filter(d -> d.getBreed().equalsIgnoreCase(pet_obj.getBreed())).findFirst().get().getProductWeight();
			
			String editted_weight = weight >= 1000 ? String.valueOf((double)(weight / 1000)) + "kg" : String.valueOf(weight) + "g";

			String locality = addresses.stream().filter(a -> a.getId() == order.getAddress().getId()).findFirst().get().getLocality();
			
			String order_packed = "";
			String order_dispatched = "";
			
			String[] values = {String.valueOf(id), customer_name, pet_name, product_name, extras, editted_weight, locality, order_packed, order_dispatched};
			
			dispatch_list.put(i + 1, Arrays.asList(values));
			
		}
		
		int row = 0;
		
		Set<Integer> list_ids = dispatch_list.keySet();
		
		for (int i : list_ids) {
			
			rows = sheet.createRow(row++);
			
			List<String> values = dispatch_list.get(i);
			
			int cell_id = 0;
			
			for (String v : values) {
				
				Cell cell = rows.createCell(cell_id++);
				
				if (i == 0) {
					
					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					font.setBold(true);
					font.setFontHeightInPoints((short) 14);
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setFont(font);
					
					cell.setCellStyle(style);
				}
				
				cell.setCellValue(v);
						
			}
			
		}
		
		for (int i = 0; i < headers.length; i++)
			sheet.autoSizeColumn(i);
		
		try {
			
			out = new FileOutputStream(dir.getAbsolutePath() + "/" + LocalDate.now().toString() + " Dispatch - list.xls");
			
			wb.write(out);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
			
				out.close();
				
				wb.close();
			
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
	}
	
	public Text getDashCustField() {
		return (Text) dashboard.lookup("#dash_cust_field");
	}
	
	public Text getDashDailyOrdersField() {
		return (Text) dashboard.lookup("#dash_daily_orders_field");
	}
	
	public Text getDashComplaintsField() {
		return (Text) dashboard.lookup("#dash_complaints_field");
	}

}
