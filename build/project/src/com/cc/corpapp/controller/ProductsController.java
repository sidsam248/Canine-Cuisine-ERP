package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Product;
import com.cc.corpapp.model.property.AGE_TYPE;
import com.cc.corpapp.model.property.AgeTypeStringConverter;

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

public class ProductsController extends Controller {
	
	@FXML
	private TableView<Product> products_list;
	
	@FXML
	private TableColumn<Product, Integer> id;
	@FXML
	private TableColumn<Product, Integer> category_id;
	@FXML
	private TableColumn<Product, String> name;
	@FXML
	private TableColumn<Product, AGE_TYPE> age_type;
	@FXML
	private TableColumn<Product, Double> price;
	
	@FXML
	private TextField new_category_id_field;
	@FXML
	private TextField new_name_field;
	@FXML
	private TextField new_age_type_field;
	@FXML
	private TextField new_price_field;
	
	@FXML
	private TextField search_name_field;
	
	private ObservableList<Product> products = FXCollections.observableArrayList();

	@Override
	public void initView() throws IOException {
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/products.fxml"));
		
		Scene products = new Scene(root);
		
		getStage().setScene(products);
		
	}
	
	public void initialize() {
		products_list.setItems(products);
		
		Product.getAllProducts().forEach(p -> {
			products.add(p);
		});
		
		name.setCellFactory(TextFieldTableCell.forTableColumn());
		age_type.setCellFactory(TextFieldTableCell.forTableColumn(new AgeTypeStringConverter()));
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		category_id.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		age_type.setCellValueFactory(new PropertyValueFactory<>("ageType"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
	
	@FXML
	public void editName(TableColumn.CellEditEvent<Product, String> cell) {
		Product p = cell.getRowValue();
		
		p.setName(cell.getNewValue());
	}
	
	@FXML
	public void editAgeType(TableColumn.CellEditEvent<Product, AGE_TYPE> cell) {
		Product p = cell.getRowValue();
		
		p.setAgeType(AGE_TYPE.valueOf(cell.getNewValue().name()));
	}
	
	@FXML
	public void editPrice(TableColumn.CellEditEvent<Product, Double> cell) {
		Product p = cell.getRowValue();
		
		p.setPrice(cell.getNewValue());
	}
	
	@FXML
	public void addNewProduct() {
		
		int cat_id = Integer.valueOf(new_category_id_field.getText());
		String name = new_name_field.getText();
		AGE_TYPE age_type = AGE_TYPE.valueOf(new_age_type_field.getText());
		double price = Double.valueOf(new_price_field.getText());
		
		Product p = Product.addNewProduct(cat_id, name, age_type, price);
		
		if (p != null)
			products.add(p);
	}
	
	@FXML
	public void delete() {
		Product p = products_list.getSelectionModel().getSelectedItem();
		
		if (p.delete())
			products.remove(p);
	}
	
	@FXML
	public void search() {
		String name = search_name_field.getText();
		
		products_list.getItems().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findAny().ifPresent(p -> {
			products_list.getSelectionModel().select(p);
			products_list.scrollTo(p);
		});
	}

}
