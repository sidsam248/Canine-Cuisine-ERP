package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Dog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class DogsController extends Controller {
	
	@FXML
	private TableView<Dog> dogs_list;
	
	@FXML
	private TableColumn<Dog, Integer> id;
	@FXML
	private TableColumn<Dog, String> breed;
	@FXML
	private TableColumn<Dog, Integer> weight;
	@FXML
	private TableColumn<Dog, Integer> product_weight;
	@FXML
	private TableColumn<Dog, Double> price;
	
	@FXML
	private TextField new_breed_field;
	@FXML
	private TextField new_price_field;
	@FXML
	private TextField new_weight_field;
	@FXML
	private TextField new_product_weight_field;
	
	@FXML
	private TextField search_breed_field;
	
	private ObservableList<Dog> dogs = FXCollections.observableArrayList();

	@Override
	public void initView() throws IOException {
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/dogs.fxml"));
		
		Scene dogs = new Scene(root);
		
		getStage().setScene(dogs);
		
	}
	
	public void initialize() {
		
		dogs_list.setItems(dogs);
		
		Dog.getAllDogs().forEach(d -> {
			dogs.add(d);
		});
		
		weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		product_weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		breed.setCellValueFactory(new PropertyValueFactory<>("breed"));
		weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		product_weight.setCellValueFactory(new PropertyValueFactory<>("productWeight"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
	
	@FXML
	public void editPrice(TableColumn.CellEditEvent<Dog, Double> cell) {
		Dog d = cell.getRowValue();
		
		d.setPrice(cell.getNewValue());
	}
	
	@FXML
	public void editWeight(TableColumn.CellEditEvent<Dog, Integer> cell) {
		Dog d = cell.getRowValue();
		
		d.setWeight(cell.getNewValue());
	}
	
	@FXML
	public void editProductWeight(TableColumn.CellEditEvent<Dog, Integer> cell) {
		Dog d = cell.getRowValue();
		
		d.setProductWeight(cell.getNewValue());
	}
	
	@FXML
	public void addNewDog() {
		String breed = new_breed_field.getText();
		Double price = Double.valueOf(new_price_field.getText());
		int weight = Integer.valueOf(new_weight_field.getText());
		int product_weight = Integer.valueOf(new_product_weight_field.getText());
		
		Dog d = Dog.addNewDog(breed, weight, product_weight, price);
		
		if (d != null)
			dogs.add(d);
	}
	
	@FXML
	public void delete() {
		Dog d = dogs_list.getSelectionModel().getSelectedItem();
		
		if(d.delete()) {
			
			new_breed_field.clear();
			new_weight_field.clear();
			new_price_field.clear();
			
			dogs.remove(d);
			
		}
	}
	
	@FXML
	public void search() {
		String breed = search_breed_field.getText();
		
		dogs_list.getItems().stream().filter(d ->d.getBreed().equalsIgnoreCase(breed)).findAny().ifPresent(d -> {
			dogs_list.getSelectionModel().select(d);
			dogs_list.scrollTo(d);
		});
	}

}
