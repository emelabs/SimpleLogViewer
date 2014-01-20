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

public class LogAdapter extends ArrayAdapter<Item>{

	private static final String TAG = "Adapter";
	
	private Context context;
	private List<Item> items = new ArrayList<Item>();
	private Item item;

	public LogAdapter(Context context, int resource, List<Item> items) {
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
		
		int foregroundColor = getPriorityColor(item.getPriority());
		
		holder.tvPriority = (TextView) view.findViewById(R.id.tvPriority);
		holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
		holder.tvMessage = (TextView) view.findViewById(R.id.tvMessage);
		
		holder.tvPriority.setText(item.getStringPriority());
		holder.tvPriority.setTextColor(foregroundColor);
		holder.tvTitle.setText(item.getTag());
		holder.tvTitle.setTextColor(foregroundColor);
		holder.tvDate.setText(item.getTimestampAsString());
		holder.tvDate.setTextColor(foregroundColor);
		holder.tvMessage.setText(item.getMessage());
		holder.tvMessage.setTextColor(foregroundColor);
		
		return view;
	}
	
	private int getPriorityColor(int priority){
		int id;
		switch (priority) {
		case Log.VERBOSE:
			id = R.color.priority_verbose;
			break;
		
		case Log.INFO:
			id = R.color.priority_info;
			break;
		
		case Log.DEBUG:
			id = R.color.priority_debug;
			break;
			
		case Log.WARN:
			id = R.color.priority_warn;
			break;
			
		case Log.ERROR:
			id = R.color.priority_error;
			break;

		default:
			id = R.color.grey;
			break;
		}
	
		return context.getResources().getColor(id);
	}
	 
	
	public class ViewHolder {

		public TextView tvPriority, tvTitle, tvDate, tvMessage;

	}
}
