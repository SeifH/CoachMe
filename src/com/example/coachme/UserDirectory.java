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
import android.os.Environment;
import android.util.Log;

public class UserDirectory {

	private static ArrayList<Player> players = new ArrayList<Player>();
	private static String name;

	public static void savePlayer(String name, String email) {
		
		load ();
		players.add(new Player (name, email, false));
		
		save ();

	}

	public static boolean load() {

		players = new ArrayList<Player>();

		File root = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.example.coachme/");

		File file = new File(root, "userDirectory.txt");

		if (!file.exists())
			return false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;

			while ((line = reader.readLine()) != null) {
				
				String userName = line;
				String userEmail= reader.readLine();
				
				players.add(new Player(userName, userEmail, false));
				
				System.out.println (userName + " " + userEmail);
			}
			
			Collections.sort(players);

		} catch (IOException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static void save() {

		try {
			File root = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/com.example.coachme/");
			if (!root.exists())
				root.mkdirs();
			File file = new File(root, "userDirectory.txt");

			PrintWriter out = new PrintWriter(file);

			for (int i = 0; i < players.size(); i++) {
				out.println(players.get(i).getName());
				out.println(players.get(i).getEmail());
			}

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static boolean load() {
//		
//		players = new ArrayList<Player>();
//				
//		File root = new File(Environment.getExternalStorageDirectory()
//				+ "/Android/data/com.example.coachme/");
//
//		File file = new File(root, "userDirectory.txt");
//
//		if (!file.exists())
//			return false;
//
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//
//			String line = null;
//
//			while ((line = reader.readLine()) != null) {
//
//				String name = line;
//				String email= reader.readLine();
//
//				players.add(new Player(name, email, false));
//				
//				System.out.println (name + " " + email);
//				
//			}
//			reader.close();
//			
//			Collections.sort(players);
//
//		}
//
//		catch (IOException e) {
//			return false;
//		} catch (NullPointerException e) {
//			return false;
//		} catch (NumberFormatException e) {
//			return false;
//		}
//		return true;
//	}

	public static void setPlayer(ArrayList<Player> p) {
		players = p;
	}

	public static ArrayList<Player> getPlayer() {
		return players;
	}
	
	public static void setName (String n){
		name = n;
	}
	public static String getName (){
		return name;
	}
	
	
	
	private static void overwriteFiles (){
				
			try {
				File root = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/com.example.coachme/");
				if (!root.exists())
					root.mkdirs();

				File file = new File(root, "userDirectory.txt");

				FileWriter out = new FileWriter(file, false);

				for (int i = 0; i <players.size(); i++){
					
				out.write(players.get(i).getName());
				out.write ("\n" + players.get(i).getEmail());
				
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
	
	
	public static void deletePlayer (String n, String email){
		for (int i = 0; i < players.size(); i ++){
			if (players.get(i).getName().trim().equals(n.trim()) && players.get(i).getEmail().trim().equals(email.trim())){
				players.remove(i);
			}
		}
		save ();
		load();
		
		
	}
	
	public static ArrayList<String> getNames (){
		load();
		
		ArrayList<String> names =  new ArrayList<String>();
		
		for (int i = 0; i < players.size(); i++){
			names.add(players.get(i).getName());
			System.out.println ("hho" + players.get(i).getName() );
			
		}
		return names;
		
	}
}
