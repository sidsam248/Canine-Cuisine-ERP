package com.cc.corpapp.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Customer;
import com.cc.corpapp.model.Pet;
import com.cc.corpapp.model.property.AGE_TYPE;
import com.cc.corpapp.model.property.AgeTypeStringConverter;
import com.cc.corpapp.model.property.GENDER;

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

public class PetsController extends Controller {
	
	private Customer customer;
	
	@FXML
	private TableView<Pet> pets_list;
	
	@FXML
	private TableColumn<Pet, String> name;
	@FXML
	private TableColumn<Pet, String> breed;
	@FXML
	private TableColumn<Pet, Integer> weight;
	@FXML
	private TableColumn<Pet, Date> birthday;
	@FXML
	private TableColumn<Pet, AGE_TYPE> age_type;
	@FXML
	private TableColumn<Pet, GENDER> gender;
	@FXML
	private TableColumn<Pet, String> breed_ref;
	
	@FXML
	private TextField search_text_field;
	
	private ObservableList<Pet> pets = FXCollections.observableArrayList();

	public PetsController(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void initView() throws IOException {
		
		FXMLLoader loader  = new FXMLLoader(Main.getMainClass().getClass().getResource("view/pets.fxml"));
		
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
		
		Scene pets = new Scene(loader.load());
		
		childStage.setTitle(customer.getEmail() + " - Pets");
		
		childStage.setScene(pets);
		
		childStage.show();
		
	}
	
	public void initialize() {
		pets_list.setItems(pets);
		
		Pet.getAllPets().forEach(p -> {
			if (p.getCustomer().getId() == customer.getId())
				pets.add(p);
		});
		
		weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		age_type.setCellFactory(TextFieldTableCell.forTableColumn(new AgeTypeStringConverter()));
		breed_ref.setCellFactory(TextFieldTableCell.forTableColumn());
		
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		breed.setCellValueFactory(new PropertyValueFactory<>("breed"));
		weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
		age_type.setCellValueFactory(new PropertyValueFactory<>("ageType"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		breed_ref.setCellValueFactory(new PropertyValueFactory<>("breedRef"));
	}
	
	@FXML
	public void editWeight(TableColumn.CellEditEvent<Pet, Integer> cell) {
		Pet pet = cell.getRowValue();
		
		pet.setWeight(cell.getNewValue());
	}
	
	@FXML
	public void editAgeType(TableColumn.CellEditEvent<Pet, AGE_TYPE> cell) {
		Pet pet = cell.getRowValue();
		
		pet.setAgeType(cell.getNewValue());
	}
	
	@FXML
	public void editBreedRef(TableColumn.CellEditEvent<Pet, String> cell) {
		Pet pet = cell.getRowValue();
		
		pet.setBreedRef(cell.getNewValue());
	}

}
