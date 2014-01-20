package com.emelabs.logviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements TextWatcher{

	private static final String TAG = "MainActivity";
	
	private ListView listView = null;
	private LogAdapter adapter = null;
	
	private List<Item> itemList;
	private List<Item> filterList;
	
	private EditText mySearch;
	private String searchString;
	
	private LogParser parser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "[onCreate] entering...");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (getIntent() != null && getIntent().getData() != null) {
			final String encodedPath = getIntent().getData().getEncodedPath();

			File file = new File(encodedPath);
			parser = new LogParserImp();
			List<Item> parseList = parser.parseFile(file);

			filterList = new ArrayList<Item>(parseList);
			itemList = new ArrayList<Item>(parseList);

			adapter = new LogAdapter(this, R.layout.row, filterList);

			listView = (ListView) findViewById(R.id.listview);
			listView.setAdapter(adapter);

			// priority spinner
			Spinner prioritySpinner = (Spinner) findViewById(R.id.spinnerPriorities);
			String[] objects = getResources().getStringArray(R.array.priorityNames);
			int[] images = getPriorityIcons();
			
			SpinnerAdapter dataAdapter = new SpinnerAdapter(this, R.layout.spinner_row, objects, images);
//			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			prioritySpinner.setAdapter(dataAdapter);			
			prioritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							String name = (String) parent.getSelectedItem();
							filterPriority(name);
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
						}
					});

			// search edittext
			mySearch = (EditText) findViewById(R.id.input_search_query);
			mySearch.addTextChangedListener(this);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void filterPriority(String priorityName){
		Log.v(TAG, "[filterPriority] entering with priority:" + priorityName);
		
		filterList.clear();
		int selectedPriority = parser.getPriorityByName(priorityName);
		
		for (Item item : itemList) {
			if(item.isShowInPriority(selectedPriority)){
				filterList.add(item);
			}
		}
		
		setAdapterToListview(filterList);
	}
	
	private void setAdapterToListview(List<Item> listToAdapter){
		adapter = new LogAdapter(this, R.layout.row, listToAdapter);
		listView.setAdapter(adapter);
	}

	private int[] getPriorityIcons(){
		TypedArray ar = getResources().obtainTypedArray(R.array.priorityIcons);
		int len = ar.length();
		int[] resIds = new int[len];
		for (int i = 0; i < len; i++){
		 
		    resIds[i] = ar.getResourceId(i, 0);
		}
		ar.recycle();
		return resIds;
	}
	
	//***********************************************************************************
	//*********************************** TextWatcher ***********************************
	//***********************************************************************************
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
	
	//***********************************************************************************
	//********************************* Spinner Adapter *********************************
	//***********************************************************************************
	public class SpinnerAdapter extends ArrayAdapter<String>{

		String[] objects;
		int[] images;
		
		public SpinnerAdapter(Context context, int textViewResourceId, String[] objects, int[] images) {
	      super(context, textViewResourceId, objects);
	      this.objects = objects;
	      this.images = images;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
		
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
		
		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.spinner_row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.tvSpinnerText);
			label.setText(this.objects[position]);

			ImageView iv = (ImageView) row.findViewById(R.id.ivSpinnerPriority);
			iv.setImageResource(this.images[position]);

			return row;
		}
	}
	
}
