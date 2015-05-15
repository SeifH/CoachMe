package com.example.coachme;

public class Player {
	private String email;
	private String name;
	private boolean selected = false;

	public Player(String n, String e, boolean s) {
		super();
		this.email = e;
		this.name = n;
		this.selected = s;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void toggleChecked() {
		// TODO Auto-generated method stub
		selected = !selected;
		
	}

}
