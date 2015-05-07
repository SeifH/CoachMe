package com.example.coachme;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Path;

public class UserContacts {
	
	private static ArrayList<String> teamNames;
	private static Context context;
	private static String name;
	private static String email;
	private static Path path;
	
	public static void save(Context context){
		
		String path = context.getFilesDir().getAbsolutePath();
		
		File contactList = new File(path, "/contactList.txt");
	}
	
	
	
	
	
	
}
