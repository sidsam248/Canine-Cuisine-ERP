package com.cc.corpapp.tasks;

import java.util.TimerTask;

import com.cc.corpapp.model.DeliveryExecutive;

public class MapsControllerTask extends TimerTask {
	
	private DeliveryExecutive del_exec;
	
	public MapsControllerTask(DeliveryExecutive del_exec) {
		this.del_exec = del_exec;
	}

	@Override
	public void run() {
		
		del_exec.updateLocation();
		
	}

}
