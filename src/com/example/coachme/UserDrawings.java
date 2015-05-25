package com.example.coachme;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * This class controls the saving and loading of images in the Formations Screen
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since Thursday, April 13 2015
 *
 */

public class UserDrawings {

	//stores names of images
	private static ArrayList<String> fileNames;
	
	private static Context context;
	private static String name;
	private static Bitmap bmpFile;
	
	private static double startTime;

	private static double endTime;
	

	/**
	 * Gets the path to which the image will be saved
	 * @return File - with correct path to save image
	 */
	public static File getSavePath() {
		File path;
		if (hasSDCard()) { // SD card
			
			//creates path to SD card
			path = new File(getSDCardPath() + "/CoachMe/");
			path.mkdir();
		} else {
			
			//creates path to user's data directory
			path = Environment.getDataDirectory();
		}
		return path;
	}

	/**
	 * Gets path to specific image
	 * 
	 * @param fileName -  String
	 * @return String - file path
	 */
	public static String getFilePath(String fileName) {
		File file = getSavePath();
		return file.getAbsolutePath() + "/" + fileName + ".png";
	}

	/**
	 * Loads saved image from storage
	 * @param imgName - String (name of image)
	 * @return Bitmap - image that is being loaded
	 */
	public static Bitmap loadFromFile(String imgName) {

		String path = getFilePath(imgName);

		Log.e("path", path);
		
		try {
			File file = new File(path);
			
			//check if image exits
			if (!file.exists()) {
				return null;
			}
			
			//decodes img to bitmap
			Bitmap bmp = BitmapFactory.decodeFile(path);
			
			//returns img
			return bmp;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Saves image to storage
	 * 
	 * @param imgName - name of image
	 * @param bmp - Bitmap
	 * @return Boolean - save success or failure
	 */
	public static boolean saveToFile(String imgName, Bitmap bmp) {

		// get the path
		String path = getFilePath(imgName);

		try {
			// attempt to save
			FileOutputStream out = new FileOutputStream(path);
			bmp.compress(CompressFormat.PNG, 100, out);
			out.flush();
			out.close();

			// successfully saved image
			return true;
		} catch (Exception e) {
			// failed to save
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if SD Card can be accessed
	 * 
	 * @return Boolean -  whether SD card can be accessed
	 */
	public static boolean hasSDCard() { // SD
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * Gets path to SD card
	 * @return String - path to SD Card
	 */
	public static String getSDCardPath() {
		File path = Environment.getExternalStorageDirectory();
		return path.getAbsolutePath();
	}

	/**
	 * Adds name to ArrayList containing names
	 * 
	 * @param name - String (name to be added)
	 */
	public static void addFileName(String name) {
		fileNames.add(name);
	}

	/**
	 * Checks whether the specific image exits in the user's storage
	 * 
	 * @param imgName - String
	 * @return boolean - file exits or not
	 */
	public static boolean fileExists(String imgName) {

		String path = getFilePath(imgName);

		File file = new File(path);

		//file does not exist
		if (!file.exists() || file == null) {
			return false;
		}

		//file exists
		return true;
	}

	/**
	 * Loads all names stored in the folder containing the images
	 */
	public static void loadFileNames() {

		fileNames = new ArrayList<String>();

		File file = new File(getSavePath().getAbsolutePath());
		File[] fileList = file.listFiles();
		if (fileList != null) {// load all file name
			for (File f : fileList) {
				fileNames.add(f.getName()
						.substring(0, f.getName().length() - 4)); // add file
																	// name
			}
			
			//sort names alphabetically
			startTime = System.nanoTime();
			Collections.sort(fileNames, String.CASE_INSENSITIVE_ORDER);
			endTime = System.nanoTime();
			
			Log.e("time comparison", "" + (endTime - startTime));
			
		}
		
		

	}

	/**
	 * Deletes specific file from storage
	 * 
	 * @param fileName - String
	 */
	public static void deleteFile(String fileName) {

		//get file path
		String path = getFilePath(fileName);

		//retrieve file
		File file = new File(path);

		//delete file
		file.delete();

	}

	/**
	 * Returns ArrayList fileNames
	 * 
	 * @return fileNames - ArrayList<String>
	 */
	public static ArrayList<String> getFileNames() {
		return fileNames;
	}

	/**
	 * Saves Bitmap to storage
	 * 
	 * @param contxt - Context
	 * @param bmp - Bitmap
	 * @param fileName - String
	 */
	public static void saveDrawings(Context contxt, Bitmap bmp, String fileName) {
		
		context = contxt;
		
		bmpFile = bmp.copy(Bitmap.Config.ARGB_8888, false);
		name = fileName;
	

		//checks if an image with same name already exists
		if (fileExists(fileName)) {
			overwriteDrawingPrompt();
			return;
		}


		// attempt to save
		if (saveToFile(fileName, bmp)) {
			// user feedback - save success
			Toast savedToast = Toast.makeText(context, "Formation saved!",
					Toast.LENGTH_SHORT);
			savedToast.show();
		} else {

			// user feedback - save failed
			Toast unsavedToast = Toast.makeText(context, "Failed to save!",
					Toast.LENGTH_SHORT);
			unsavedToast.show();
		}
	}

	/**
	 * Asks user if they want to overwrite a file with the same name
	 */
	private static void overwriteDrawingPrompt() {
		
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
						deleteFile(name);
						
						//save new drawing
						saveDrawings(context, bmpFile.copy(Bitmap.Config.ARGB_8888, false), name);
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

	// REMOVE LATER//
	public static void deleteAllFiles() {

		File file = new File(getSavePath().getAbsolutePath());
		File[] fileList = file.listFiles();
		if (fileList != null) {// load all file name
			for (File f : fileList) {

				String path = getFilePath(f.getName());

				File file2 = new File(path);

				file2.delete();
			}
		}
	}

}
