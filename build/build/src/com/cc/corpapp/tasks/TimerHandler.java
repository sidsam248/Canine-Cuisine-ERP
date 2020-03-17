package com.cc.corpapp.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class TimerHandler {
	
	private static List<Timer> timers = new ArrayList<>();
	
	public static void addTimer(Timer t) {
		timers.add(t);
	}
	
	public static void stopAllTimers() {
		
		if (timers.isEmpty()) return;
		
		timers.forEach(t -> {
			t.cancel();
		});
		
		timers.clear();
	}
}
