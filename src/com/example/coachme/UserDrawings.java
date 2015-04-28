package com.example.coachme;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class UserDrawings {

	private static ArrayList<String> fileNames = new ArrayList<String>();

	public static File getSavePath() {
		File path;
		if (hasSDCard()) { // SD card
			path = new File(getSDCardPath() + "/.CoachMe/");
			path.mkdir();
		} else {
			path = Environment.getDataDirectory();
		}
		return path;
	}

	public static String getFilePath(String fileName) {
		File file = getSavePath();
		return file.getAbsolutePath() + "/" + fileName + ".png";
	}

	public static Bitmap loadFromFile(String imgName) {

		String path = getFilePath(imgName);

		try {
			File f = new File(path);
			if (!f.exists()) {
				return null;
			}
			Bitmap bmp = BitmapFactory.decodeFile(path);
			return bmp;
		} catch (Exception e) {
			return null;
		}
	}

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

	public static boolean hasSDCard() { // SD
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	public static String getSDCardPath() {
		File path = Environment.getExternalStorageDirectory();
		return path.getAbsolutePath();
	}

	public static ArrayList<String> getFileNames() {
		return fileNames;
	}

	public static void addFileName(String name) {
		fileNames.add(name);
	}

	public static boolean fileExists(String imgName) {

		String path = getFilePath(imgName);

		File file = new File(path);
		if (!file.exists() || file == null) {
			return false;
		}
		return true;
	}

	private void saveFileNames() {

	}

	private void loadFileNames() {

	}

}
