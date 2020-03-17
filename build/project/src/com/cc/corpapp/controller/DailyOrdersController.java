package com.cc.corpapp.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Address;
import com.cc.corpapp.model.AssignedDailyOrder;
import com.cc.corpapp.model.Customer;
import com.cc.corpapp.model.DailyOrder;
import com.cc.corpapp.model.DeliveryExecutive;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

public class DailyOrdersController extends Controller {
	
	private Customer customer;
	private DeliveryExecutive delivery_executive;
	
	private static Stage childStage;
	
	@FXML
	private TableView<DailyOrder> daily_orders_list;
	
	@FXML
	private TableColumn<DailyOrder, Integer> id;
	@FXML
	private TableColumn<DailyOrder, String> customer_name;
	@FXML
	private TableColumn<DailyOrder, String> pet_name;
	@FXML
	private TableColumn<DailyOrder, String> product_name;
	@FXML
	private TableColumn<DailyOrder, String> address_name;
	@FXML
	private TableColumn<DailyOrder, Date> start_date;
	@FXML
	private TableColumn<DailyOrder, Date> till_date;
	@FXML
	private TableColumn<DailyOrder, Date> hold_start_date;
	@FXML
	private TableColumn<DailyOrder, Date> hold_end_date;
	@FXML
	private TableColumn<DailyOrder, Double> price;
	
	@FXML
	private TextField daily_orders_search_field;

	private ObservableList<DailyOrder> daily_orders = FXCollections.observableArrayList();
	
	public DailyOrdersController() {}

	public DailyOrdersController(Customer customer) {
		this.customer = customer;
		childStage = new Stage();
		childStage.setAlwaysOnTop(true);
	}
	
	public DailyOrdersController(DeliveryExecutive del) {
		this.delivery_executive = del;
		childStage = new Stage();
		childStage.setAlwaysOnTop(true);
	}
	
	@Override
	public void back() throws IOException {
		if (customer != null || delivery_executive != null) {
			childStage.hide();
			childStage = null;
		}
		else
			super.back();
	}

	@Override
	public void initView() throws IOException {
		
		if (customer != null) {
			initViewForCustomer();
			return;
		}
		
		if (delivery_executive != null) {
			initViewForDeliveryExecutive();
			return;
		}
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/dailyorders.fxml"));
		
		Scene dailyorders = new Scene(root);
		
		getStage().setScene(dailyorders);
		
	}
	
	private void initViewForDeliveryExecutive() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.getMainClass().getClass().getResource("view/dailyorders.fxml"));
		
		loader.setControllerFactory((Class<?> c) -> {
			
			for (Constructor<?> constructor : c.getConstructors()) {
				
				if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0].equals(DeliveryExecutive.class))
					
					try {
						
						return constructor.newInstance(delivery_executive);
						
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						
						e.printStackTrace();
						
					}
				
			}
			
			return c;
		});
		
		Scene scene = new Scene(loader.load());
		
		childStage.setTitle(delivery_executive.getEmail() + " - Assigned Daily Orders");
		
		childStage.setScene(scene);
		
		childStage.show();
		
	}
	
	private void initViewForCustomer() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.getMainClass().getClass().getResource("view/dailyorders.fxml"));
		
		loader.setControllerFactory((Class<?> c) -> {
			
			for (Constructor<?> constructor : c.getConstructors()) {
				
				if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0].equals(Customer.class))
					
					try {
						
						return constructor.newInstance(customer);
						
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						
						e.printStackTrace();
						
					}
				
			}
			
			return c;
		});
		
		Scene scene = new Scene(loader.load());
		
		childStage.setTitle(customer.getEmail() + " - Daily Orders");
		
		childStage.setScene(scene);
		
		childStage.show();
		
	}
	
	public void initialize() {
		
		//DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		daily_orders_list.setItems(daily_orders);
		
		if (customer == null && delivery_executive == null) {
			
			DailyOrder.getAllDailyOrders().forEach(i -> {
				daily_orders.add(i);
			});
		
		} else if (customer != null) {
			
			DailyOrder.getAllDailyOrders().forEach(i -> {
				if (i.getCustomer().getId() == customer.getId())
					daily_orders.add(i);
			});
			
		} else if (delivery_executive != null) {
			AssignedDailyOrder.getAssignedDailyOrderById(delivery_executive.getId()).forEach(a -> {
				daily_orders.add(a);
			});
		}
		
		daily_orders_list.setRowFactory(new Callback<TableView<DailyOrder>, TableRow<DailyOrder>>() {
			
			ContextMenu menu = null;
			ContextMenu delivery_executives = null;

			@Override
			public TableRow<DailyOrder> call(TableView<DailyOrder> param) {
				
				final TableRow<DailyOrder> row = new TableRow<>();
				
				row.setOnMouseClicked(e -> {
					
					if (e.getButton() == MouseButton.SECONDARY) {
						
						if(menu != null)
							menu.hide();
						
						if(delivery_executives != null)
							delivery_executives.hide();
						
						menu = new ContextMenu();
						
						DeliveryExecutive exec = daily_orders_list.getSelectionModel().getSelectedItem().getDeliveryExecutive();
						
						if (exec != null) {
							
							MenuItem assigned = new MenuItem("Order assigned to -> " + exec.getEmail());
							menu.getItems().add(assigned);
							
							MenuItem unassign = new MenuItem("Unassign order from ->" + exec.getEmail());
							menu.getItems().add(unassign);
							
							unassign.setOnAction(a -> {
								if (AssignedDailyOrder.unassign(daily_orders_list.getSelectionModel().getSelectedItem(), exec))
									if (delivery_executive != null)
										daily_orders.remove(daily_orders_list.getSelectionModel().getSelectedItem());
							});
							
						} else {
						
							MenuItem assign = new MenuItem("Assign Order to Delivery Executive ->");
						
							assign.setOnAction(a -> {
								
								delivery_executives = new ContextMenu();
								
								DeliveryExecutive.getAllDeliveryExecutives().forEach(d -> {
									
									MenuItem item = new MenuItem(d.getEmail());
									
									item.setOnAction(assign_order -> {
										AssignedDailyOrder.assign(daily_orders_list.getSelectionModel().getSelectedItem(), d);
									});
									
									delivery_executives.getItems().add(item);
									
									delivery_executives.show(getStage(), e.getScreenX(), e.getScreenY());
									
								});
							
							});
							
							menu.getItems().add(assign);
						
						}
						
						menu.show(getStage(), e.getScreenX(), e.getScreenY());
						
					}
					
				});
				return row;
				
			}
			
		});
			
					
		
		address_name.setCellFactory(TextFieldTableCell.forTableColumn());
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		/*till_date.setCellFactory((Callback<TableColumn<DailyOrder,Date>,TableCell<DailyOrder,Date>>) t -> {
			return new DatePickerCell<DailyOrder, Date>();
		});*/
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		pet_name.setCellValueFactory(new PropertyValueFactory<>("petName"));
		product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
		address_name.setCellValueFactory(new PropertyValueFactory<>("addressName"));
		
		start_date.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		till_date.setCellValueFactory(new PropertyValueFactory<>("tillDate"));
		hold_start_date.setCellValueFactory(new PropertyValueFactory<>("holdStartDate"));
		hold_end_date.setCellValueFactory(new PropertyValueFactory<>("holdEndDate"));
		
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		
	}
	
	@FXML
	public void editAddressId(TableColumn.CellEditEvent<DailyOrder, Integer> cell) {
		
		DailyOrder order = cell.getRowValue();
		
		Address address = Address.getAllAddresses().stream().filter(a -> a.getCustomer().getId() == order.getCustomer().getId() && a.getId() == cell.getNewValue()).findFirst().get();
		
		order.setAddress(address);
		
	}
	
	@FXML
	public void editPrice(TableColumn.CellEditEvent<DailyOrder, Double> cell) {
		DailyOrder order = cell.getRowValue();
		
		order.setPrice(cell.getNewValue());
	}
	
	@FXML
	public void search() {
		daily_orders_list.getItems().stream().filter(i -> i.getCustomer().getEmail().equalsIgnoreCase(daily_orders_search_field.getText())).findAny()
		.ifPresent(item -> {
			daily_orders_list.getSelectionModel().select(item); 
			daily_orders_list.scrollTo(item);
		});
	}
}