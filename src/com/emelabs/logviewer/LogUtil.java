package com.emelabs.logviewer;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class LogUtil {
	
	private static Map<String, Integer> priorities = new HashMap<String, Integer>();
	static{
		priorities.put("V", Log.VERBOSE);
		priorities.put("D", Log.DEBUG);
		priorities.put("I", Log.INFO);
		priorities.put("W", Log.WARN);
		priorities.put("E", Log.ERROR);
	}
	
	
	public static int getPriority(String priorityName){
		return priorities.get(priorityName);
	}
}
