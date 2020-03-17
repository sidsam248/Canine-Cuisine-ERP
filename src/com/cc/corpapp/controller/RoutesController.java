package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Route;

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
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class RoutesController extends Controller {
	
	@FXML
	private TableView<Route> routes_list;
	
	@FXML
	private TableColumn<Route, Integer> id;
	@FXML
	private TableColumn<Route, String> name;
	
	@FXML
	private TextField new_route_field;
	
	private ObservableList<Route> routes = FXCollections.observableArrayList();

	@Override
	public void initView() throws IOException {
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/routes.fxml"));
		
		Scene dogs = new Scene(root);
		
		getStage().setScene(dogs);
		
	}
	
	public void initialize() {
		routes_list.setItems(routes);
		
		routes.addAll(Route.getAllRoutes());
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		routes_list.setRowFactory(new Callback<TableView<Route>, TableRow<Route>>() {
			
			ContextMenu menu = null;

			@Override
			public TableRow<Route> call(TableView<Route> param) {
				
				final TableRow<Route> route = new TableRow<>();
				
				route.setOnMouseClicked(e -> {
					
					if (e.getButton() == MouseButton.SECONDARY) {
						
						if (menu != null)
							menu.hide();
						
						menu = new ContextMenu();
						
						MenuItem item = new MenuItem("View Daily Orders assigned to this route");
						
						item.setOnAction(a -> {
							
							try {
								
								new DailyOrdersController(routes_list.getSelectionModel().getSelectedItem()).initView();
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
								
							}
						});
						
						menu.getItems().add(item);
						
						menu.show(getStage(), e.getScreenX(), e.getScreenY());
					}
				});
				
				return route;
			}
		});
	}
	
	@FXML
	public void addNewRoute() {
		String name = new_route_field.getText();
		
		Route r = Route.addNewRoute(name);
		
		if (r != null) {
			routes.add(r);
			new_route_field.clear();
		}
	}
	

}
