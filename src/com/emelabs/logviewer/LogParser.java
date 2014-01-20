package com.emelabs.logviewer;

import java.io.File;
import java.util.List;

public interface LogParser {
	
	/**
	 * 
	 * @param priorityName
	 * @return code for priority
	 */
	public int getPriority(String priorityName);
	
	/**
	 * @param priorityName
	 * @return <code>true</code> if priority is an Exception
	 */
	public boolean isException(String priorityName);
	
	/**
	 * Parse log file and build a list with each log line as item
	 * @param file Log file will be parse
	 * @return List items 
	 */
	public List<Item> parseFile(File file);
}
