package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Category;

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

public class CategoriesController extends Controller {
	
	@FXML
	private TableView<Category> categories_list;
	
	@FXML
	private TableColumn<Category, Integer> id;
	@FXML
	private TableColumn<Category, String> name;
	@FXML
	private TextField new_category_field;
	
	private ObservableList<Category> categories = FXCollections.observableArrayList();
	
	@Override
	public void initView() throws IOException {
		
		Parent view = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/categories.fxml"));
		
		Scene cat_view = new Scene(view);
		
		getStage().setScene(cat_view);
		
	}
	
	public void initialize() {
		categories_list.setItems(categories);
		
		Category.getAllCategories().forEach(c -> {
			categories.add(c);
		});
		
		name.setCellFactory(TextFieldTableCell.forTableColumn());
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("categoryType"));
	}
	
	@FXML
	public void editName(TableColumn.CellEditEvent<Category, String> cell) {
		Category c = cell.getRowValue();
		
		c.setCategoryType(cell.getNewValue());
	}
	
	@FXML
	public void addNewCategory() {
		
		Category c = Category.addNewCategory(new_category_field.getText());
		
		if (c != null) {
			new_category_field.clear();
			categories.add(c);
		}
	}
	
	@FXML
	public void delete() {
		Category c = categories_list.getSelectionModel().getSelectedItem();
		
		if(c.delete())
			categories.remove(c);
	}

}
