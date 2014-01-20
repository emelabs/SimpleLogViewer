package com.emelabs.logviewer;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Item {
	
	/**
	 * Priority code
	 */
	private int priority;
	
	/**
	 * Priority name
	 */
	private String stringPriority;
	
	/**
	 * Log timestamp
	 */
	private Date timestamp;
	
	/**
	 * Tag message
	 */
	private String tag;
	
	/**
	 * Message
	 */
	private String message;
	
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

	public Item() {
		super();
	}

	public Item(int priority, String strPriority, Date timestamp, String tag, String message) {
		super();
		this.stringPriority = strPriority;
		this.priority = priority;
		this.timestamp = timestamp;
		this.tag = tag;
		this.message = message;
	}

	public int getPriority() {
		return priority;
	}

	public String getStringPriority() {
		return stringPriority;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getTag() {
		return tag;
	}

	public String getMessage() {
		return message;
	}

	public String getTimestampAsString(){
		return formatter.format(timestamp);
	}

	@Override
	public String toString() {
		return "Item [priority=" + priority + ", stringPriority="
				+ stringPriority + ", timestamp=" + timestamp + ", tag=" + tag
				+ ", message=" + message + "]";
	}

	/**
	 * @param selectedPriority
	 * @return
	 */
	public boolean isShowInPriority(int selectedPriority) {
		return this.priority >= selectedPriority;
	}
}
