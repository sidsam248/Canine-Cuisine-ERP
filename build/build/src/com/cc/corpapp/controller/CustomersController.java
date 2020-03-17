package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Customer;

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
import javafx.util.Callback;
import javafx.util.converter.LongStringConverter;

public class CustomersController extends Controller {
	
	@FXML
	private TableView<Customer> customers_list;
	
	@FXML
	private TableColumn<Customer, Integer> id;
	@FXML
	private TableColumn<Customer, String> email;
	@FXML
	private TableColumn<Customer, Long> phone_no;
	@FXML
	private TableColumn<Customer, String> first_name;
	@FXML
	private TableColumn<Customer, String> last_name;
	
	@FXML
	private TextField customer_search_field;
	
	private ObservableList<Customer> customers = FXCollections.observableArrayList();

	@Override
	public void initView() throws IOException {
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/customers.fxml"));
		
		Scene customers = new Scene(root);
		
		getStage().setScene(customers);
	}
	
	public void initialize() {
		customers_list.setItems(customers);
		
		Customer.getAllCustomers().forEach(c -> {
			customers.add(c);
		});
		
		customers_list.setRowFactory(new Callback<TableView<Customer>, TableRow<Customer>>() {
			
			ContextMenu menu = null;
			
			@Override
			public TableRow<Customer> call(TableView<Customer> param) {
				final TableRow<Customer> row = new TableRow<>();
				
				row.setOnMouseClicked(e -> {
					if (e.getButton() == MouseButton.SECONDARY) {
						
						if (menu != null)
							menu.hide();
						
						menu = new ContextMenu();
						
						MenuItem addresses = new MenuItem("View Customer's Addresses");
						
						
						addresses.setOnAction(a -> {
							
							try {
								
								new AddressesController(customers_list.getSelectionModel().getSelectedItem()).initView();
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
								
							}
							
						});
						
						MenuItem pets = new MenuItem("View Customer's Pets");
						
						
						pets.setOnAction(a -> {
							try {
								
								new PetsController(customers_list.getSelectionModel().getSelectedItem()).initView();
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
								
							}
						});
						
						MenuItem daily_orders = new MenuItem("View Customer's Daily Orders");
						
						daily_orders.setOnAction(a -> {
							
							try {
								
								new DailyOrdersController(customers_list.getSelectionModel().getSelectedItem()).initView();
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
								
							}
						});
						
						menu.getItems().addAll(addresses, pets, daily_orders);
						
						menu.show(row, e.getScreenX(), e.getScreenY());
						
					}
				});
				
				return row;
			}
		});
		
		email.setCellFactory(TextFieldTableCell.forTableColumn());
		phone_no.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		phone_no.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
		first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
	}
	
	@FXML
	public void editEmail(TableColumn.CellEditEvent<Customer, String> cell) {
		Customer c = cell.getRowValue();
		
		c.setEmail(cell.getNewValue());
	}
	
	@FXML
	public void editPhoneNo(TableColumn.CellEditEvent<Customer, Long> cell) {
		Customer c = cell.getRowValue();
		
		c.setPhoneNo(cell.getNewValue());
	}
	
	@FXML
	public void search() {
		customers_list.getItems().stream().filter(c -> c.getEmail().equals(customer_search_field.getText())).findAny().ifPresent(c -> {
			customers_list.getSelectionModel().select(c);
			customers_list.scrollTo(c);
		});
	}

}
