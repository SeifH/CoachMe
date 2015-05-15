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

import android.os.Environment;
import android.util.Log;

public class UserStatistics {

	private static ArrayList<Games> games = new ArrayList<Games>();

	public static void saveGame(Games game) {

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
			out.write( "\n" + String.valueOf(game.getHomeRed()));
			out.write("\n" + String.valueOf(game.getAwayRed()));
			out.write("\n" + String.valueOf(game.getHomeYellow()));
			out.write("\n" + String.valueOf(game.getAwayYellow())+ "\n");

			out.flush();
			out.close();

//			BufferedReader in;
//			try {
//				in = new BufferedReader(new FileReader(new File (root,"userStats.txt")));
//				String line;
//				while ((line = in.readLine()) != null) {
//					Log.e("mm", line);
//				}
//				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		

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

				Log.e("msg", "loaded");
			}
			
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
}
