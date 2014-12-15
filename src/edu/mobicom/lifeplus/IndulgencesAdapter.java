package edu.mobicom.lifeplus;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class IndulgencesAdapter extends BaseAdapter{

	Context context;
	ArrayList<Indulgence> data;
    private static LayoutInflater inflater = null;

    public IndulgencesAdapter(Context context, ArrayList<Indulgence> data) {
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
    public Indulgence getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        
        if (vi == null)
            vi = inflater.inflate(R.layout.row_indulgences, null);
        
        final TextView name = (TextView) vi.findViewById(R.id.indulgencesName);
        TextView desc = (TextView) vi.findViewById(R.id.indulgencesDesc);
        TextView price = (TextView) vi.findViewById(R.id.indulgencesPrice);
        Button buy = (Button) vi.findViewById(R.id.button_buy);
        
        name.setText(data.get(position).getName());
        desc.setText(data.get(position).getDesc());
        price.setText(String.valueOf(data.get(position).getPrice()));
        
        buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(v.getContext());
            	builder.setTitle("Purchase indulgence");
        		builder.setMessage("Are you sure you want to purchase \"" + name.getText().toString() + "\"?");
        		
        		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        			/*Depends on how you implement profile since update to profile requires a get from db maybe make it somehow global?
        			 * just call db.updateCredits(Profile) for update to credits and db.gainExp to update XP*/
        			@Override
        			public void onClick(DialogInterface dialog, int which) {
        					Toast.makeText(v.getContext(), "Purchase \"" + name.getText().toString() + ".", Toast.LENGTH_SHORT).show();
    				}
        				
    			});
        		
        		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        			
        			@Override
        			public void onClick(DialogInterface dialog, int which) {
        			}
        			
        		});
        		
        		builder.create().show();
			}
		});
        
        return vi;
    }	
	
}
