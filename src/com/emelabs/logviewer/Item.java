package com.emelabs.logviewer;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;

public class Item {
	private int level;
	private Date timestamp;
	private String tag;
	private String message;
	
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

	public Item() {
		super();
	}

	public Item(String level, Date timestamp, String tag, String message) {
		super();
		this.level = LogUtil.getPriority(level);
		this.timestamp = timestamp;
		this.tag = tag;
		this.message = message;
	}
	
	public Item(int level, Date timestamp, String tag, String message) {
		super();
		this.level = level;
		this.timestamp = timestamp;
		this.tag = tag;
		this.message = message;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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
		return "Item [level=" + level + ", timestamp=" + timestamp + ", tag="
				+ tag + ", message=" + message + "]";
	}
	
}
