package com.example.coachme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Player> {

	Player[] playersList = null;
	Context context;

	public CustomAdapter(Context c, Player[] resource) {
		super(c, R.layout.row, resource);
		// TODO Auto-generated constructor stub
		this.context = c;
		this.playersList = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		//use xml row to format each view
		convertView = inflater.inflate(R.layout.row, parent, false);
		//inflate the layout with the row
		TextView name = (TextView) convertView.findViewById(R.id.textView);
		
		CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
		
		convertView.setTag(new PlayerViewHolder(name,cb));
		
		name.setText(playersList[position].getName());
	
		
		Player p = (Player)this.getItem(position);
		
		cb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v;
				
				Player p = (Player) cb.getTag();
				
				p.setSelected(cb.isChecked());
			}
		});
		
		cb.setTag(p);
		cb.setChecked(p.isSelected());
		
//		if (playersList[position].isSelected())
//			cb.setChecked(true);
//		else
//			cb.setChecked(false);
		return convertView;
	}

}
