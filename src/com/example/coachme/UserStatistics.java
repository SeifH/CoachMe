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

public class UserStatistics {

	private static ArrayList<Games> games = new ArrayList<Games>();
	private static Context context;
	private static String name;
	private static Games currentGame;

	public static void saveGame(Games game, String n, Context contxt) {
		
		setName(n);
		context = contxt;
		currentGame = game;
		
		//checks if an image with same name already exists
		if (fileExists(n)) {
			overwriteGamePrompt();
			Log.e("msg", "exists");
			return;
		}


		try {
			File root = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/com.example.coachme/");
			if (!root.exists())
				root.mkdirs();

			File file = new File(root, "userStats.txt");

			FileWriter out = new FileWriter(file, true);

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
			out.write("\n" + String.valueOf(game.getAwayYellow())+ "\n");

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

	public static boolean load() {
		
		games = new ArrayList<Games>();
				
		File root = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.example.coachme/");

		File file = new File(root, "userStats.txt");

		if (!file.exists())
			return false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;

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

				games.add(new Games(n, hPossession, hShots, hGoals, hYellow,
						hRed, aPossession, aShots, aGoals, aYellow, aRed));
				

			}
			reader.close();

			
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

	public static void setGames(ArrayList<Games> g) {
		games = g;
	}

	public static ArrayList<Games> getGames() {
		return games;
	}
	
	public static void setName (String n){
		name = n;
	}
	public static String getName (){
		return name;
	}
	
	/**
	 * Asks user if they want to overwrite a file with the same name
	 */
	private static void overwriteGamePrompt() {
		
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(context);
		saveDialog.setTitle("File Already Exist");
		saveDialog.setMessage("Do you want to overwrite the existing file?");

		saveDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)

					{
						//user wants to overwrite file
						dialog.cancel();
						
						//delete existing file
						for(int i = 0; i < games.size(); i++){
					        if(games.get(i).getName().equals(getName())){
					        	//delete file
					        	 games.remove(i);
					        	 overwriteFiles();
					        	break;
					        }
					    }
						
						for(int i = 0; i < games.size(); i++){
					        Log.e("name", games.get(i).getName());					    }
						
						//save file
						saveGame(currentGame, getName(), context);
						load();
					}

				});
		saveDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//user does not want to overwrite file
						dialog.cancel();
					}
				});
		saveDialog.show();
		
	}
	
	public static boolean fileExists (String n){
		for(int i = 0; i < games.size(); i++){
	        if(games.get(i).getName().equals(n)){
		       return true;
	        }
	    }
		
		return false;
	}
	
	private static void overwriteFiles (){
				
			try {
				File root = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/com.example.coachme/");
				if (!root.exists())
					root.mkdirs();

				File file = new File(root, "userStats.txt");

				FileWriter out = new FileWriter(file, false);

				for (int i = 0; i <games.size(); i++){
					
				out.write(games.get(i).getName());

				out.write("\n" + String.valueOf(games.get(i).getHomePossession()));
				out.write("\n" + String.valueOf(games.get(i).getAwayPossession()));
				out.write("\n" + String.valueOf(games.get(i).getHomeGoals()));
				out.write("\n" + String.valueOf(games.get(i).getAwayGoals()));
				out.write("\n" + String.valueOf(games.get(i).getHomeShots()));
				out.write("\n" + String.valueOf(games.get(i).getAwayShots()));
				out.write("\n" + String.valueOf(games.get(i).getHomeRed()));
				out.write("\n" + String.valueOf(games.get(i).getAwayRed()));
				out.write("\n" + String.valueOf(games.get(i).getHomeYellow()));
				out.write("\n" + String.valueOf(games.get(i).getAwayYellow())+ "\n");
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
	
	public static Games getGame (String n){
		for (int i = 0; i < games.size(); i ++){
			if (games.get(i).getName().trim().equals(n.trim())){

				return games.get(i);

			}
		}
		
		return null;
		
	}
	
	public static void deleteGame (String n){
		for (int i = 0; i < games.size(); i ++){
			if (games.get(i).getName().trim().equals(n.trim())){
				games.remove(i);
			}
		}
		overwriteFiles();
		load();
		
		
	}
	

}
