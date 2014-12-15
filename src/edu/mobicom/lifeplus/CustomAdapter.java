package edu.mobicom.lifeplus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	Context context;
	ArrayList<Task> data;
	private static LayoutInflater inflater = null;

	public CustomAdapter(Context context, ArrayList<Task> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Task getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;

		if (vi == null)
			vi = inflater.inflate(R.layout.row, null);

		final TextView id = (TextView) vi.findViewById(R.id.itemID);
		TextView name = (TextView) vi.findViewById(R.id.itemName);
		TextView desc = (TextView) vi.findViewById(R.id.itemDesc);
		CheckBox check = (CheckBox) vi.findViewById(R.id.itemCheck);
		TextView timeDate = (TextView) vi.findViewById(R.id.itemTimeDate);
		String date = "";
		String time = "";
		String[] monthName = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
				"AUG", "SEP", "OCT", "NOV", "DEC" };

		time = data.get(position).getTime();
		
		if (data.get(position).getStatus() == true) {
			vi.setBackgroundColor(Color.rgb(236, 240, 241));
			name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			switch (data.get(position).getDifficulty()) {
			case 0:
				name.setTextColor(Color.rgb(52, 152, 219));
				break;
			case 1:
				name.setTextColor(Color.rgb(26, 188, 156));
				break;
			case 2:
				name.setTextColor(Color.rgb(46, 204, 113));
				break;
			case 3:
				name.setTextColor(Color.rgb(241, 196, 15));
				break;
			case 4:
				name.setTextColor(Color.rgb(230, 126, 34));
				break;
			case 5:
				name.setTextColor(Color.rgb(231, 76, 60));
				break;
			}
		}	

		if (data.get(position).getDate() == null) {
			if(data.get(position).getType() == 1)
				date = data.get(position).getDuration() + " min.";
			else if (data.get(position).getType() == 2)
				date = "";
		} else {
			String temp = new SimpleDateFormat("yyyyMMdd").format(data.get(
					position).getDate());
			date = monthName[Integer.parseInt(temp.substring(4, 6)) - 1] + " "
					+ temp.substring(6);
		}
		
		if(date.equals(" min."))
			date = "";

		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				DatabaseManager db = new DatabaseManager(context,
						Task.DATABASE_NAME, null, 1);

				db.setChecked(data.get(position).getID(), isChecked);
				data.get(position).setChecked(isChecked);
			}
		});

		id.setText(String.valueOf(data.get(position).getID()));
		name.setText(data.get(position).getName());

		if (data.get(position).getDesc().length() > 30)
			desc.setText(data.get(position).getDesc().substring(0, 30) + "\n"
					+ data.get(position).getDesc().substring(30));
		else
			desc.setText(data.get(position).getDesc());
		check.setChecked(data.get(position).getChecked());

		if (time.equals("00:00"))
			timeDate.setText(date);
		else
			timeDate.setText(time + "\n" + date);

		return vi;
	}

}
