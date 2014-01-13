package com.emelabs.logviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity implements TextWatcher{

	private static final String TAG = "MainActivity";
	private ListView listView = null;
	private Adapter adapter = null;
	
	private List<Item> itemList = new ArrayList<Item>();
	private List<Item> filterList = new ArrayList<Item>();
	
	private EditText mySearch;
	private String searchString;
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "[onCreate] entering...");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(getIntent() != null && getIntent().getData() != null){
			final String encodedPath = getIntent().getData().getEncodedPath();

			File file = new File(encodedPath);
			parseFile(file);

			adapter = new Adapter(this, R.layout.row, filterList);
			
			listView = (ListView) findViewById(R.id.listview);
			listView.setAdapter(adapter);
			
			//priority selector
			Spinner prioritySpinner = (Spinner) findViewById(R.id.spinnerPriorities);
			ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.priorityNames, android.R.layout.simple_spinner_item);

			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			prioritySpinner.setAdapter(dataAdapter);
			prioritySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					String name = (String) parent.getSelectedItem();
					
					filterPriority(name);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
				
			});
			
			//search edittext
			mySearch = (EditText) findViewById(R.id.input_search_query);
			mySearch.addTextChangedListener(this);
		}

	}
	
	private void parseFile(File file){
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
					
					
					Item item = new Item(strLevel, formatter.parse(strDate), tag, message);
					itemList.add(item);
					filterList.add(item);
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
	}
	
	private void filterPriority(String priority){
		Log.v(TAG, "[filterPriority] entering with priority:" + priority);
		
		filterList.clear();
		int selectedPriority = LogUtil.getPriority(priority.substring(0,1));
		
		for (Item item : itemList) {
			if(item.isShowInPriority(selectedPriority)){
				filterList.add(item);
			}
		}
		
		setAdapterToListview(filterList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void afterTextChanged(Editable s) {
		filterList.clear();
		searchString = mySearch.getText().toString().trim().replaceAll("\\s", "").toLowerCase();
		if (searchString.length() > 0) {
			for (Item item : itemList) {
				if(item.getTag().toLowerCase().contains(searchString) || 
						item.getMessage().toLowerCase().contains(searchString)){
					filterList.add(item);
				}
			}
			
			Log.d(TAG, "[afterTextChanged] setting filter list");
			setAdapterToListview(filterList);
		}else{
			Log.d(TAG, "[afterTextChanged] setting item list");
			filterList.clear();
			setAdapterToListview(itemList);
		}
	}
	
	private void setAdapterToListview(List<Item> listToAdapter){
		adapter = new Adapter(this, R.layout.row, listToAdapter);
		listView.setAdapter(adapter);
	}
}
