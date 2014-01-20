package com.emelabs.logviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.util.Log;

public class LogParserImp implements LogParser{
	
	private static final String TAG = "LogParserImp";
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
	
	/**
	 * Priorities definition
	 */
	private static Map<String, Integer> priorities = new HashMap<String, Integer>();
	static{
		priorities.put("V", Log.VERBOSE);
		priorities.put("D", Log.DEBUG);
		priorities.put("I", Log.INFO);
		priorities.put("W", Log.WARN);
		priorities.put("E", Log.ERROR);
	}
	
	
	public int getPriority(String priorityKey){
		return priorities.get(priorityKey);
	}
	
	public int getPriorityByName(String priorityName){
		return getPriority(priorityName.substring(0,1));
	}
	
	public boolean isException(String priorityName){
		return priorityName.equals("E");
	}

	
	/**
	 *	Line : Priority | date | Tag | message
	 *	Line example: V | 01-17 16:22:54.053 | MainActivity - [onStart] entering...
	 */
	@Override
	public List<Item> parseFile(File file) {
		List<Item> itemsList = new ArrayList<Item>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null){
				try {
					Log.d(TAG, "[onCreate] line:" + line);
					String[] split = line.split("\\|");
					
					String strPriority = split[0].trim();
					
					String strDate = split[1].trim();

					String text = split[2];
					
					int pos = text.indexOf("-");
					String tag = text.substring(0, pos).trim();
					String message = text.substring(pos+2).trim();
					
					Log.d(TAG, "priority:" + strPriority + " | date:" + strDate + " | tag:" + tag + " | message:" + message);
					
					if(isException(strPriority)){
						line = reader.readLine();
						message += "\n" + line;
						
						while((line = reader.readLine()).trim().startsWith("at")){
							message += "\n" + line;
						}
					}
					Item item = new Item(getPriority(strPriority), strPriority, formatter.parse(strDate), tag, message);
					itemsList.add(item);					
					
				} catch (ParseException e) {
					Log.e(TAG, "[onCreate] with line:[" + line + "]", e);
				} catch (Exception e) {
					Log.e(TAG, "[onCreate] with line:[" + line + "]", e);
				}
			}
			
			reader.close();

		} catch (FileNotFoundException e) {
			Log.e(TAG, "[onCreate]", e);
		} catch (IOException e) {
			Log.e(TAG, "[onCreate]", e);
		}
		return itemsList;
	}
}
