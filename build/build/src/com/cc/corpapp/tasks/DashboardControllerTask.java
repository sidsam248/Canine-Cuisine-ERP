package com.cc.corpapp.tasks;

import java.util.TimerTask;

import com.cc.corpapp.controller.DashboardController;
import com.cc.corpapp.model.Customer;
import com.cc.corpapp.model.DailyOrder;

public class DashboardControllerTask extends TimerTask {
	
	private DashboardController controller;
	
	public DashboardControllerTask(DashboardController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.getDashCustField().setText(String.valueOf(Customer.getCustomerSize()));
		controller.getDashDailyOrdersField().setText(String.valueOf(DailyOrder.getDailyOrderSize()));
		controller.getDashComplaintsField().setText(String.valueOf(0));
	}

}
