package com.cc.corpapp;

import com.cc.corpapp.controller.CategoriesController;
import com.cc.corpapp.controller.CustomersController;
import com.cc.corpapp.controller.DailyOrdersController;
import com.cc.corpapp.controller.DashboardController;
import com.cc.corpapp.controller.DeliveryExecutivesController;
import com.cc.corpapp.controller.DogsController;
import com.cc.corpapp.controller.ProductsController;
import com.cc.corpapp.controller.RootController;
import com.cc.corpapp.model.Admin;
import com.cc.corpapp.tasks.TimerHandler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stage;
	private static Main instance;
	private static Admin admin;
	
	private static RootController root_controller;
	private static DashboardController dashboard_controller;
	private static DailyOrdersController daily_orders_controller;
	private static CustomersController customers_controller;
	private static ProductsController products_controller;
	private static DogsController dogs_controller;
	private static CategoriesController categories_controller;
	private static DeliveryExecutivesController delivery_executives_controller;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		instance = this;
		
		init(stage);
		
		root_controller.initView();
		
		stage.show();
		
	}
	
	private void init(Stage stage) {
		
		stage.setTitle("Canine Cuisine");
		
		stage.setResizable(true);
		
		stage.setOnCloseRequest(e -> {
			TimerHandler.stopAllTimers();
			Platform.exit();
			System.exit(0);
		});
		
		Main.stage = stage;
		
		root_controller = new RootController();
		
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void setCurrentAdmin(Admin a) {
		Main.admin = a;
	}
	
	public static Admin getCurrentAdmin() {
		return admin;
	}
	
	public static RootController getRootController() {
		return root_controller;
	}
	
	public static DashboardController getDashboardController() {
		if (dashboard_controller == null)
			dashboard_controller = new DashboardController();
		
		return dashboard_controller;
	}
	
	public static DailyOrdersController getDailyOrdersController() {
		if (daily_orders_controller == null)
			daily_orders_controller = new DailyOrdersController();
		
		return daily_orders_controller;
	}
	
	public static CustomersController getCustomersController() {
		if (customers_controller == null)
			customers_controller = new CustomersController();
		
		return customers_controller;
	}
	
	public static ProductsController getProductsController() {
		if (products_controller == null)
			products_controller = new ProductsController();
		
		return products_controller;
	}
	
	public static DogsController getDogsController() {
		if (dogs_controller == null)
			dogs_controller = new DogsController();
		
		return dogs_controller;
	}
	
	public static CategoriesController getCategoriesController() {
		if (categories_controller == null)
			categories_controller = new CategoriesController();
		
		return categories_controller;
	}
	
	public static DeliveryExecutivesController getDeliveryExecutivesController() {
		if (delivery_executives_controller == null)
			delivery_executives_controller = new DeliveryExecutivesController();
		
		return delivery_executives_controller;
	}
	
	
	public static Main getMainClass() {
		return instance;
	}
	

}
