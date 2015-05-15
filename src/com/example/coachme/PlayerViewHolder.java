package com.example.coachme;

import android.widget.CheckBox;
import android.widget.TextView;

public class PlayerViewHolder {

	private CheckBox cb;
	private TextView tv;

	public PlayerViewHolder(TextView tv, CheckBox cb) {
		this.cb = cb;
		this.tv = tv;
	}

	public CheckBox getCheckbox() {
		return cb;
	}

	public void setCheckbox(CheckBox cb) {
		this.cb = cb;
	}

	public TextView getTextView() {
		return tv;
	}

	public void setTextView(TextView tv) {
		this.tv = tv;
	}

}
