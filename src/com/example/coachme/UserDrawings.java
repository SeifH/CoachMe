package com.example.coachme;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

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

public class UserDrawings {

	private static ArrayList<String> fileNames;
	private static Context context;
	private static String name;
	private static Bitmap bmpFile;
	

	public static File getSavePath() {
		File path;
		if (hasSDCard()) { // SD card
			path = new File(getSDCardPath() + "/CoachMe/");
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

		Log.e("path", path);
		try {
			File file = new File(path);
			if (!file.exists()) {
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

		Log.e("path2", path);

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
		}

	}

	public static void deleteFile(String fileName) {

		String path = getFilePath(fileName);

		File file = new File(path);

		file.delete();

	}

	public static ArrayList<String> getFileNames() {
		return fileNames;
	}

	public static void saveDrawings(Context contxt, Bitmap bmp, String fileName) {
		
		context = contxt;
		
		bmpFile = bmp.copy(Bitmap.Config.ARGB_8888, false);
		name = fileName;
	

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

	private static void overwriteDrawingPrompt() {
		
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(context);
		saveDialog.setTitle("File Already Exist");
		saveDialog.setMessage("Do you want to overwrite the existing file?");

		saveDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)

					{
						dialog.cancel();
						deleteFile(name);
						
						saveDrawings(context, bmpFile.copy(Bitmap.Config.ARGB_8888, false), name);
					}

				});
		saveDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		saveDialog.show();
		
//		if (overwrite){
//			deleteFile(name);
//			saveDrawings(context, bmp, name);
//
//		}

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
