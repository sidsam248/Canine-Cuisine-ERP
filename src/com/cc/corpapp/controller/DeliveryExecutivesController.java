package com.cc.corpapp.controller;


import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.DeliveryExecutive;
import com.cc.corpapp.model.Route;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class DeliveryExecutivesController extends Controller {
	
	@FXML
	private TableView<DeliveryExecutive> delivery_executives_list;
	
	private ObservableList<DeliveryExecutive> delivery_executives = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<DeliveryExecutive, Integer> id;
	@FXML
	private TableColumn<DeliveryExecutive, String> email;

	@Override
	public void initView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.getMainClass().getClass().getResource("view/delivery_executives.fxml"));
		
		Scene scene = new Scene(loader.load());
		
		getStage().setScene(scene);
		
	}
	
	public void initialize() {
		
		delivery_executives_list.setItems(delivery_executives);
		
		DeliveryExecutive.getAllDeliveryExecutives().forEach(d -> {
			delivery_executives.add(d);
		});
		
		delivery_executives_list.setRowFactory(new Callback<TableView<DeliveryExecutive>, TableRow<DeliveryExecutive>>() {
			
			ContextMenu menu = null;

			@Override
			public TableRow<DeliveryExecutive> call(TableView<DeliveryExecutive> param) {
				
				final TableRow<DeliveryExecutive> delivery_executive = new TableRow<>();
				
				delivery_executive.setOnMouseClicked(e -> {
					
					if (e.getButton() == MouseButton.SECONDARY) {
						
						if (menu != null)
							menu.hide();
						
						menu = new ContextMenu();
						
						DeliveryExecutive del_exec = delivery_executives_list.getSelectionModel().getSelectedItem();
						
						if (del_exec.getRoute() != null) {
							
							MenuItem assigned = new MenuItem("Order assigned to route: -> " + del_exec.getRoute().getName());
							menu.getItems().add(assigned);
							
							MenuItem unassign = new MenuItem("Unassign order from route: -> " + del_exec.getRoute().getName());
							menu.getItems().add(unassign);
							
							unassign.setOnAction(a -> {
								del_exec.getRoute().unassign("delivery_executive", del_exec.getId());
							});
							
						} else {
						
							MenuItem assign_route = new MenuItem("Assign route");
							
							assign_route.setOnAction(a -> {
								ContextMenu routes = new ContextMenu();
								
								Route.getAllRoutes().forEach(r -> {
									MenuItem route = new MenuItem(r.getName());
									route.setOnAction(e1 -> {
										r.assign("delivery_executive", delivery_executives_list.getSelectionModel().getSelectedItem().getId());
									});
									routes.getItems().add(route);
								}); 
								
								routes.show(getStage(), e.getScreenX(), e.getScreenY());
							});
							
							menu.getItems().add(assign_route);
						
						}
						
						MenuItem track = new MenuItem("Track delivery executive");
						
						track.setOnAction(a -> {
							try {
								new MapsController(del_exec).initView();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						});
						
						menu.getItems().addAll(track);
						
						menu.show(getStage(), e.getScreenX(), e.getScreenY());
						
					}
					
				});
				
				return delivery_executive;
			}
			
		});
		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		
	}

}
