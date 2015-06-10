package com.example.coachme;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import android.os.Environment;

/**
 * This class controls the saving, loading and deleting players in the Player
 * Directory Screen
 * 
 * @author Seif Hassan & Olivia Perryman
 * @since Wednesday, June 3 2015
 *
 */

public class UserDirectory {

	// ArrayList of Player's object to store names and emails
	private static ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * Stores a new player in the text file
	 * 
	 * @param Player
	 *            's name (String)
	 * @param Player
	 *            's email (String)
	 */
	public static void savePlayer(String name, String email) {

		// load current data in the text file to the players arrayList
		load();

		// adds a new player to the ArrayList
		players.add(new Player(name, email, false));

		// saves the ArrayList to the text file
		save();

	}

	/**
	 * This method load's the stored players (name and email) into an arrayList
	 * 
	 * @return if the file was loaded successfully or not
	 */
	public static boolean load() {

		players = new ArrayList<Player>();

		// get the path
		File root = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.example.coachme/");

		File file = new File(root, "userDirectory.txt");

		// check if a file exists
		if (!file.exists())
			return false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;

			// read the file line by line
			while ((line = reader.readLine()) != null) {

				// get the name
				String userName = line;

				// get the email
				String userEmail = reader.readLine();

				// add a new player
				players.add(new Player(userName, userEmail, false));

			}

			// sort the players alphabetically
			Collections.sort(players);

		} catch (IOException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException e) {
			return false;
		}

		// successfully loaded the file
		return true;
	}

	/**
	 * This method saves the players to a text file
	 */
	public static void save() {

		try {
			// gets the path to the text file
			File root = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/com.example.coachme/");

			if (!root.exists())
				root.mkdirs();

			// opens the file
			File file = new File(root, "userDirectory.txt");

			PrintWriter out = new PrintWriter(file);

			for (int i = 0; i < players.size(); i++) {
				// saves the name
				out.println(players.get(i).getName());
				// saves the email
				out.println(players.get(i).getEmail());
			}

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the players ArrayList
	 * 
	 * @param p
	 *            ArrayList containing the players
	 */
	public static void setPlayer(ArrayList<Player> p) {
		players = p;
	}

	/**
	 * Returns the ArrayList with all the players
	 * 
	 * @return players
	 */
	public static ArrayList<Player> getPlayer() {
		load();
		return players;
	}

	/**
	 * Removes a stored player
	 * 
	 * @param n
	 *            name of player to be deleted
	 * @param email
	 *            email of player to be deleted
	 */
	public static void deletePlayer(String n, String email) {
		for (int i = 0; i < players.size(); i++) {
			// find the player to be removed with matching name and email
			if (players.get(i).getName().trim().equals(n.trim())
					&& players.get(i).getEmail().trim().equals(email.trim())) {
				// removes player
				players.remove(i);
			}
		}
		// saves players to file
		save();

		// loads saved players
		load();

	}

	/**
	 * Returns the name of all the players store in the text file
	 * 
	 * @return ArrayList<String> names of players
	 */
	public static ArrayList<String> getNames() {
		// loads all the players
		load();

		// ArrayList to store all the names
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < players.size(); i++) {
			// adds a player's name
			names.add(players.get(i).getName());
		}

		// returns the names
		return names;

	}

}// end class
