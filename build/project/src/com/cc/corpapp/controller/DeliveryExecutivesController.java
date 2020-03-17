package com.cc.corpapp.controller;


import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.DeliveryExecutive;

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
						
						MenuItem view = new MenuItem("View assigned orders");
						
						view.setOnAction(a -> {
							try {
								new DailyOrdersController(delivery_executives_list.getSelectionModel().getSelectedItem()).initView();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						});
						
						MenuItem track = new MenuItem("Track delivery executive");
						
						track.setOnAction(a -> {
							
							//TODO: Track on google maps using current lat and lng
							
							/*try {
								
							} catch (IOException e2) {
								e2.printStackTrace();
							}*/
						});
						
						menu.getItems().addAll(view, track);
						
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
