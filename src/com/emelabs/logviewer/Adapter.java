package com.emelabs.logviewer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<Item>{

	private static final String TAG = "Adapter";
	
	private Context context;
	private List<Item> items = new ArrayList<Item>();
	private Item item;

	public Adapter(Context context, int resource, List<Item> items) {
		super(context, resource);
		this.context = context;
		this.items = items;
	}
	
	public int getCount(){
		return this.items.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size())){
			return view;
		}
		
		item = items.get(position);
		
		holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		holder.tvTitle.setTextColor(getPriorityColor(item.getLevel()));
		holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
		holder.tvDate.setTextColor(getPriorityColor(item.getLevel()));
		holder.tvMessage = (TextView) view.findViewById(R.id.tvMessage);
		holder.tvMessage.setTextColor(getPriorityColor(item.getLevel()));
		
		holder.tvTitle.setText(item.getTag());
		holder.tvDate.setText(item.getTimestampAsString());
		holder.tvMessage.setText(item.getMessage());
		
		return view;
	}
	
	private int getPriorityColor(int priority){
		int id;
		switch (priority) {
		case Log.VERBOSE:
			id = R.color.grey_verbose;
			break;
		
		case Log.INFO:
			id = R.color.green_info;
			break;
		
		case Log.DEBUG:
			id = R.color.blue_debug;
			break;
			
		case Log.WARN:
			id = R.color.orange_warn;
			break;
			
		case Log.ERROR:
			id = R.color.red_error;
			break;

		default:
			id = R.color.blue;
			break;
		}
		
		return context.getResources().getColor(id);
	}
	 
	
	public class ViewHolder {

		public TextView tvTitle, tvDate, tvMessage;

	}
}
