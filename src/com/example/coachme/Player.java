package com.example.coachme;
/**
 * 
 * Object that is a player and has attributes of name and email and selected. Compared alphabetically
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since May 1, 2015
 *
 */
public class Player implements Comparable<Player>{
	
	//set up attributes
	private String email;
	private String name;
	private boolean selected = false;

	/**
	 * create a new player object
	 * @param player's name
	 * @param player's email
	 * @param whether they are selected or not
	 */
	public Player(String n, String e, boolean s) {
		super();
		this.email = e;
		this.name = n;
		this.selected = s;
	}

	/**
	 * 
	 * @return player's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Changes player's email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * change player's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * check if player is selected
	 * @return true if selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * change if player is selected
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * change selected to opposite of what it currently is.
	 */
	public void toggleChecked() {
		// TODO Auto-generated method stub
		selected = !selected;
		
	}


	/**
	 * Allow players to be sorted alphabetically by name
	 */
	@Override
	public int compareTo(Player another) {
		
		return this.getName().compareToIgnoreCase(another.getName());
		
	}

}
