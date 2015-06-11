package com.example.coachme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

/**
 * This class controls the saving, loading and deleting games in the Game
 * Statistics Screen
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since Friday, May 15 2015
 *
 */
public class UserStatistics {

	// arrayList contains all stored games
	private static ArrayList<Games> games = new ArrayList<Games>();
	// Application Context
	private static Context context;
	private static String name;
	// current game being saved/loaded
	private static Games currentGame;

	/**
	 * Saves a game to the device
	 * 
	 * @param game
	 *            game with all its values
	 * @param n
	 *            name of game to be saved
	 * @param contxt
	 *            application context
	 */
	public static void saveGame(Games game, String n, Context contxt) {

		// sets the current game name
		setName(n);
		// initiates context and current game
		context = contxt;
		currentGame = game;

		// checks if game with same name already exists
		if (fileExists(n)) {
			// name already exists
			// asks user to overwrite game
			overwriteGamePrompt();
			return;
		}

		try {
			// get path to storage
			File root = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/com.example.coachme/");

			if (!root.exists())
				root.mkdirs();

			// opens file
			File file = new File(root, "userStats.txt");

			// create file writer
			FileWriter out = new FileWriter(file, true);

			// save game values to file
			out.write(game.getName());

			out.write("\n" + String.valueOf(game.getHomePossession()));
			out.write("\n" + String.valueOf(game.getAwayPossession()));
			out.write("\n" + String.valueOf(game.getHomeGoals()));
			out.write("\n" + String.valueOf(game.getAwayGoals()));
			out.write("\n" + String.valueOf(game.getHomeShots()));
			out.write("\n" + String.valueOf(game.getAwayShots()));
			out.write("\n" + String.valueOf(game.getHomeRed()));
			out.write("\n" + String.valueOf(game.getAwayRed()));
			out.write("\n" + String.valueOf(game.getHomeYellow()));
			out.write("\n" + String.valueOf(game.getAwayYellow()) + "\n");

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * Loads all the games stored on device
	 * 
	 * @return boolean -whether successfully loaded gamed
	 */
	public static boolean load() {

		// initial games arrayList to contain all the stored games
		games = new ArrayList<Games>();

		// gets file path
		File root = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.example.coachme/");

		// opens file
		File file = new File(root, "userStats.txt");

		// checks if file doesn't exist
		if (!file.exists())
			return false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;

			// reads line by line data in the file
			while ((line = reader.readLine()) != null) {

				String n = line;

				int hPossession = Integer.parseInt(reader.readLine());
				int aPossession = Integer.parseInt(reader.readLine());

				int hGoals = Integer.parseInt(reader.readLine());
				int aGoals = Integer.parseInt(reader.readLine());

				int hShots = Integer.parseInt(reader.readLine());
				int aShots = Integer.parseInt(reader.readLine());

				int hRed = Integer.parseInt(reader.readLine());
				int aRed = Integer.parseInt(reader.readLine());

				int hYellow = Integer.parseInt(reader.readLine());
				int aYellow = Integer.parseInt(reader.readLine());

				// creates a new saved game
				games.add(new Games(n, hPossession, hShots, hGoals, hYellow,
						hRed, aPossession, aShots, aGoals, aYellow, aRed));

			}
			reader.close();

			// sorts games alphabetically
			Collections.sort(games);

		}

		catch (IOException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the games arrayList
	 * 
	 * @param g
	 *            game arrayList
	 */
	public static void setGames(ArrayList<Games> g) {
		games = g;
	}

	/**
	 * Returns the game arrayList
	 * 
	 * @return all stored games
	 */
	public static ArrayList<Games> getGames() {
		return games;
	}

	/**
	 * Sets the name of current game
	 * 
	 * @param n
	 *            (String)
	 */
	public static void setName(String n) {
		name = n;
	}

	/**
	 * Returns the name of the current game
	 * 
	 * @return name (String)
	 */
	public static String getName() {
		return name;
	}

	/**
	 * Asks user if they want to overwrite a file with the same name
	 */
	private static void overwriteGamePrompt() {
		// warning message
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(context);
		// sets title of warning message
		saveDialog.setTitle("File Already Exist");
		// sets message of warning message
		saveDialog.setMessage("Do you want to overwrite the existing file?");

		saveDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)

					{
						// user wants to overwrite file
						dialog.cancel();

						// delete existing file
						for (int i = 0; i < games.size(); i++) {
							// find file with matching name
							if (games.get(i).getName().equals(getName())) {
								// delete file
								games.remove(i);
								overwriteFiles();
								break;
							}
						}

						// save file and reload all files
						saveGame(currentGame, getName(), context);
						load();
					}

				});
		saveDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// user does not want to overwrite file
						dialog.cancel();
					}
				});
		saveDialog.show();

	}

	/**
	 * Checks if the file already exists
	 * 
	 * @param n
	 *            name of file to be checked
	 * @return boolean whether file exists
	 */
	public static boolean fileExists(String n) {
		for (int i = 0; i < games.size(); i++) {
			// finds file with matching name
			if (games.get(i).getName().equals(n)) {
				// file already exists
				return true;
			}
		}

		// file does not already exist
		return false;
	}

	/**
	 * Overwrites a stored game
	 */
	private static void overwriteFiles() {

		try {
			// gets path to file
			File root = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/com.example.coachme/");

			if (!root.exists())
				root.mkdirs();

			// opens file
			File file = new File(root, "userStats.txt");

			// create file writer
			FileWriter out = new FileWriter(file, false);

			// saves updated games to file
			for (int i = 0; i < games.size(); i++) {

				out.write(games.get(i).getName());

				out.write("\n"
						+ String.valueOf(games.get(i).getHomePossession()));
				out.write("\n"
						+ String.valueOf(games.get(i).getAwayPossession()));
				out.write("\n" + String.valueOf(games.get(i).getHomeGoals()));
				out.write("\n" + String.valueOf(games.get(i).getAwayGoals()));
				out.write("\n" + String.valueOf(games.get(i).getHomeShots()));
				out.write("\n" + String.valueOf(games.get(i).getAwayShots()));
				out.write("\n" + String.valueOf(games.get(i).getHomeRed()));
				out.write("\n" + String.valueOf(games.get(i).getAwayRed()));
				out.write("\n" + String.valueOf(games.get(i).getHomeYellow()));
				out.write("\n" + String.valueOf(games.get(i).getAwayYellow())
						+ "\n");
			}

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * Gets the current game being altered
	 * 
	 * @param n
	 *            name of game
	 * @return game being altered
	 */
	public static Games getGame(String n) {
		for (int i = 0; i < games.size(); i++) {
			// finds game with matching name
			if (games.get(i).getName().trim().equals(n.trim())) {

				// returns game
				return games.get(i);

			}
		}

		// no game found
		return null;

	}

	/**
	 * Deletes a game from device
	 * 
	 * @param n
	 */
	public static void deleteGame(String n) {
		for (int i = 0; i < games.size(); i++) {
			// finds game with matching name
			if (games.get(i).getName().trim().equals(n.trim())) {
				// removes game
				games.remove(i);
			}
		}

		// reloads the file
		overwriteFiles();
		load();

	}

}// end class
