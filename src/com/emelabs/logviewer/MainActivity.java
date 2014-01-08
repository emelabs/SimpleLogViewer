package com.emelabs.logviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private ListView listView;
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "[onCreate] entering...");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(getIntent() != null && getIntent().getData() != null){
			final String encodedPath = getIntent().getData().getEncodedPath();

			File file = new File(encodedPath);
			Log.d(TAG, "file " + file);

			List<Item> itemList = new ArrayList<Item>();
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null){
					try {
						Log.d(TAG, "[onCreate] line:" + line);
						String[] split = line.split("\\|");
						
						String strLevel = split[0].trim();
						
						String strDate = split[1].trim();

						String text = split[2];
						
						int pos = text.indexOf("-");
						String tag = text.substring(0, pos).trim();
						String message = text.substring(pos+2).trim();
						
						Log.d(TAG, "level:" + strLevel + " | date:" + strDate + " | tag:" + tag + " | message:" + message);
						
						itemList.add(new Item(strLevel, formatter.parse(strDate), tag, message));
					} catch (ParseException e) {
						Log.e(TAG, "[onCreate]", e);
					}
				}
				
				reader.close();

			} catch (FileNotFoundException e) {
				Log.e(TAG, "[onCreate]", e);
			} catch (IOException e) {
				Log.e(TAG, "[onCreate]", e);
			}
			
			Log.v(TAG, "[onCreate] ");
			
			Adapter adapter = new Adapter(this, R.layout.row, itemList);
			
			listView = (ListView) findViewById(R.id.listview);
			listView.setAdapter(adapter);
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
