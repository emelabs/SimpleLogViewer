package com.emelabs.logviewer;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Item {
	
	private int priority;
	private String stringPriority;
	private Date timestamp;
	private String tag;
	private String message;
	
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

	public Item() {
		super();
	}

	public Item(String priority, Date timestamp, String tag, String message) {
		super();
		this.stringPriority = priority;
		this.priority = LogUtil.getPriority(priority);
		this.timestamp = timestamp;
		this.tag = tag;
		this.message = message;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStringPriority() {
		return stringPriority;
	}

	public void setStringPriority(String stringPriority) {
		this.stringPriority = stringPriority;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	
	public boolean isShowInPriority(int selectedPriority) {
		return this.priority >= selectedPriority;
	}
	
}
