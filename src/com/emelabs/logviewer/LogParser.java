package com.emelabs.logviewer;

import java.io.File;
import java.util.List;

public interface LogParser {
	
	/**
	 * Return code for priority based on priority key
	 * 
	 * @param priorityKey
	 * @return code for priority based on priority key
	 */
	public int getPriority(String priorityKey);
	
	
	/**
	 * Return code for priority based on priority name
	 * 
	 * @param priorityName
	 * @return code for priority based on priority name
	 */
	public int getPriorityByName(String priorityName);
	
	
	/**
	 * @param priorityName
	 * @return <code>true</code> if priority is an Exception
	 */
	public boolean isException(String priorityName);
	
	
	/**
	 * Parse log file and build a list with each log line as item
	 * 
	 * @param file Log file will be parse
	 * @return List items 
	 */
	public List<Item> parseFile(File file);
}
