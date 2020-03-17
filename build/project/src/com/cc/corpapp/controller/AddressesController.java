package com.cc.corpapp.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Address;
import com.cc.corpapp.model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class AddressesController extends Controller {
	
	private Customer customer;
	
	public AddressesController(Customer customer) {
		this.customer = customer;
	}
	
	@FXML
	private TableView<Address> addresses_list;
	
	@FXML
	private TableColumn<Address, String> name;
	@FXML
	private TableColumn<Address, String> state;
	@FXML
	private TableColumn<Address, Integer> pincode;
	@FXML
	private TableColumn<Address, String> city;
	@FXML
	private TableColumn<Address, Integer> house_no;
	@FXML
	private TableColumn<Address, String> locality;
	@FXML
	private TableColumn<Address, String> street_name;
	
	@FXML
	private TextField search_text_field;
	
	private ObservableList<Address> addresses = FXCollections.observableArrayList();

	@Override
	public void initView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.getMainClass().getClass().getResource("view/addresses.fxml"));
		
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
		
		Stage childStage = new Stage();
		
		childStage.setTitle(customer.getEmail() + " - Addresses");
		
		childStage.setScene(new Scene(loader.load()));
		
		childStage.show();
	}
	
	public void initialize() {
		
		addresses_list.setItems(addresses);
		
		Address.getAllAddresses().forEach(a -> {
			if (a.getCustomer().getId() == customer.getId()) {
				addresses.add(a);
			}
		});
		
		city.setCellFactory(TextFieldTableCell.forTableColumn());
		pincode.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		city.setCellFactory(TextFieldTableCell.forTableColumn());
		house_no.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		locality.setCellFactory(TextFieldTableCell.forTableColumn());
		street_name.setCellFactory(TextFieldTableCell.forTableColumn());
		
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		state.setCellValueFactory(new PropertyValueFactory<>("state"));
		pincode.setCellValueFactory(new PropertyValueFactory<>("pincode"));
		city.setCellValueFactory(new PropertyValueFactory<>("city"));
		house_no.setCellValueFactory(new PropertyValueFactory<>("houseNo"));
		locality.setCellValueFactory(new PropertyValueFactory<>("locality"));
		street_name.setCellValueFactory(new PropertyValueFactory<>("streetName"));
	}
	
	@FXML
	public void editCity(TableColumn.CellEditEvent<Address, String> cell) {
		Address a = cell.getRowValue();
		
		a.setCity(cell.getNewValue());
	}
	
	@FXML
	public void editPincode(TableColumn.CellEditEvent<Address, Integer> cell) {
		Address a = cell.getRowValue();
		
		a.setPincode(cell.getNewValue());
	}
	
	@FXML
	public void editHouseNo(TableColumn.CellEditEvent<Address, Integer> cell) {
		Address a = cell.getRowValue();
		
		a.setHouseNo(cell.getNewValue());
	}
	
	@FXML
	public void editLocality(TableColumn.CellEditEvent<Address, String> cell) {
		Address a = cell.getRowValue();
		
		a.setLocality(cell.getNewValue());
	}
	
	@FXML
	public void editStreetName(TableColumn.CellEditEvent<Address, String> cell) {
		Address a = cell.getRowValue();
		
		a.setStreetName(cell.getNewValue());
	}

}
