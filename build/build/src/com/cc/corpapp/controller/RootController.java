package com.cc.corpapp.controller;

import java.io.IOException;

import com.cc.corpapp.Main;
import com.cc.corpapp.model.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RootController extends Controller {
	
	@FXML
	private TextField admin_email;
	@FXML
	private PasswordField admin_pwd;
	
	@Override
	public void initView() throws IOException {
		
		Parent root = FXMLLoader.load(Main.getMainClass().getClass().getResource("view/login.fxml"));
		
		Scene initial = new Scene(root);
		
		getStage().setScene(initial);
		
		Main.getStage().setResizable(false);
	}
	
	@FXML
	public void login() {
		
		if(admin_email.getText().isEmpty() || admin_pwd.getText().isEmpty()) return;
		
		Admin a = new Admin(admin_email.getText());
		
		if(a.authenticate(admin_pwd.getText())) {
			
			try {
				
				Main.getDashboardController().initView();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
		}
	}
	
}
