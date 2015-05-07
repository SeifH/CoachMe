package com.example.coachme;

public class Player {
	private String email;
	private String name;
	private boolean selected = false;

	public Player(String name, String email, boolean selected) {
		super();
		this.email = email;
		this.name = name;
		this.selected = selected;
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

}
