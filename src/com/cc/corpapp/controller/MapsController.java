package com.cc.corpapp.controller;

import java.io.IOException;
import java.util.Timer;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.DeliveryExecutive;
import com.cc.corpapp.tasks.MapsControllerTask;
import com.cc.corpapp.tasks.TimerHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapsController extends Controller {
	
	private static Stage childStage;

	
	private DeliveryExecutive del_exec;
	
	private MapsControllerTask task;
	private Timer timer;
	
	public MapsController() {}

	public MapsController(DeliveryExecutive del_exec) {
		this.del_exec = del_exec;
	}
	
	@Override
	public void initView() throws IOException {
		
		task = new MapsControllerTask(del_exec);
		timer = new Timer();
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/map.fxml"));
		
		Scene map = new Scene(root);
		
		childStage = new Stage();
		
		childStage.setScene(map);
		
		childStage.setAlwaysOnTop(true);
		
		childStage.show();
		
		timer.scheduleAtFixedRate(task, 1000, 1000 * 60);
		
		TimerHandler.addTimer(timer);
		
	}
	
	public void initialize() {
	}
	
	@Override
	public void back() {
		childStage.hide();
		childStage = null;
	}

}
