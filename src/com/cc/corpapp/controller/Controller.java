package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.tasks.TimerHandler;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class Controller {
	
	public abstract void initView() throws IOException;
	
	@FXML
	public void logout() {
		
		Main.getCurrentAdmin().logout();
		
		try {
			
			TimerHandler.stopAllTimers();
			Main.getRootController().initView();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	@FXML
	public void back() throws IOException {
		
		TimerHandler.stopAllTimers();
		Main.getDashboardController().initView();
	}
	
	protected Stage getStage() {
		return Main.getStage();
	}

}
