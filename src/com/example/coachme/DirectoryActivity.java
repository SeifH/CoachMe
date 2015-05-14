package com.example.coachme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.CheckBox;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;

public class DirectoryActivity extends Activity implements OnClickListener {

	private Button newPlayer, sendEmail;
	private File contacts;
	private String playerName, playerEmail, path, subject, message;
	private ListView lv;
	private ArrayList<Player> playersList;
	private Player[] playersArray;
	private CheckBox cb;
	private ArrayAdapter<Player> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.directory);

		newPlayer = (Button) findViewById(R.id.newPlayer);
		newPlayer.setOnClickListener(this);

		sendEmail = (Button) findViewById(R.id.sendEmail);
		sendEmail.setOnClickListener(this);

		// String path = this.getFilesDir().getAbsolutePath();
		contacts = new File(this.getFilesDir(), "contacts");

		// cb = (CheckBox) findViewById(R.id.checkBox);
		// cb.setOnCheckedChangeListener(this);

		lv = (ListView) findViewById(R.id.listView);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Player player = listAdapter.getItem(position);
				player.toggleChecked();
				PlayerViewHolder viewHolder = (PlayerViewHolder) view.getTag();

				viewHolder.getCheckbox().setChecked(player.isSelected());

				// lv.setItemChecked(position, true);
				// playersList.get(position).setSelected(true);
				// playersArray[position].setSelected(true);
				// When clicked, set selected to true
				// Player player = (Player) parent.getItemAtPosition(position);
				// player.setSelected(true);

			}
		});

		fillList();

		// readFile(contacts);

		// String filename = "contacts";
		// String string = "\nHello world!";
		// clear the file
//		 String string = "";
//		 FileOutputStream outputStream;
//		
//		 try {
//		 outputStream = openFileOutput("contacts", Context.MODE_PRIVATE);
//		 outputStream.write(string.getBytes());
//		 outputStream.close();
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }

		readFile(contacts);
	}

	public void onCheckboxClicked(View view) {
		// boolean checked = ((CheckBox) view).isChecked();
		//
		// switch (view.getId()) {
		// case R.id.checkBox:
		// if (checked) {
		// System.out.println("view: " + view);
		// // System.out.println("String:" + view.toString());
		// System.out.println("Tag: " + view.getTag());
		// // Player p = (Player) view.getTag();
		// // p.setSelected(true);
		// } else {
		// // Player p = (Player) view.getTag();
		// // p.setSelected(false);
		// }
		// break;
		// }
	}

	private void fillList() {
		playersList = new ArrayList<Player>();
		// fill the arraylist
		playersList = readFiletoArray(contacts);

		// convert to array
		playersArray = new Player[playersList.size()];
		playersArray = playersList.toArray(playersArray);

		CustomAdapter adapter = new CustomAdapter(this, playersArray);
		lv.setAdapter(adapter);
	}

	private ArrayList<Player> readFiletoArray(File f) {

		ArrayList<Player> newPlayersList = new ArrayList<Player>();
		String name = "";
		String email = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			//the first line was blank
			line = br.readLine();
			
			while ((line = br.readLine()) != null) {
				//first line has name
				name = line;
				//next line has email
				line = br.readLine();
				email = line;

				System.out.println("name: " + name);
				System.out.println("Email: " + email);
				
				newPlayersList.add(new Player(name, email, false));
			}
			br.close();

			return newPlayersList;

		} catch (IOException e) {
			// You'll need to add proper error handling here
		}
		return null;
	}

	private String readFile(File f) {
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
				// System.out.println(line);
			}
			br.close();
			System.out.println(text);
			return text.toString();

		} catch (IOException e) {
			// You'll need to add proper error handling here
		}
		return null;
	}

	// private void readFile(File f) {
	//
	// // String fileName = "core-sample/src/main/resources/data.txt";
	//
	// try {
	// List<String> lines = Files.readAllLines(Paths.get(path),
	// Charset.defaultCharset());
	// for (String line : lines) {
	// System.out.println(line);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;

		if (b.getId() == R.id.newPlayer) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("New Player");

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);

			final EditText inputName = new EditText(this);
			inputName.setHint("Player's name");
			layout.addView(inputName);

			final EditText inputEmail = new EditText(this);
			inputEmail.setHint("Player's email");
			inputEmail
					.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			layout.addView(inputEmail);

			alert.setView(layout);

			// alert.setMessage("Player's information: ");
			//
			// //edit text for user input
			// final EditText input = new EditText(this);
			// alert.setView(input);

			alert.setPositiveButton("Okay",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							playerName = "\n" + inputName.getText().toString();
							playerEmail = "\n"
									+ inputEmail.getText().toString();

							FileOutputStream outputStream;

							try {
								outputStream = openFileOutput("contacts",
										Context.MODE_APPEND);
								outputStream.write(playerName.getBytes());
								outputStream.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								outputStream = openFileOutput("contacts",
										Context.MODE_APPEND);
								outputStream.write(playerEmail.getBytes());
								outputStream.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							fillList();
							readFile(contacts);

						}

					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// cancelled

						}
					});

			alert.show();

		} else if (b.getId() == R.id.sendEmail) {

			ArrayList<Player> selectedPlayers = new ArrayList<Player>();

			// SparseBooleanArray checked = lv.getCheckedItemPositions();
			// for (int i = 0; i< lv.getAdapter().getCount();i++)
			// {
			// if (checked.get(i))
			// {
			// selectedPlayers.add(playersList.get(i));
			// }
			// }

			for (int i = 0; i < playersList.size(); i++) {
				if (playersList.get(i).isSelected()) {
					selectedPlayers.add(playersList.get(i));
				}
			}

			// for (int i = 0; i < lv.getCount(); i++) {
			// if (lv.getChildAt(i).isChecked()) {
			// selectedPlayers.add(playersList.get(i));
			// }
			// }

//			System.out.println("Selected: " + selectedPlayers.toString());
//			
//			for(int i = 0; i< selectedPlayers.size(); i++)
//			{
//				System.out.println(selectedPlayers.get(i).getName());
//			}
			
			sendEmail(selectedPlayers);
			
		}

	}

	public void sendEmail(ArrayList list) {
		
		Player p;
		final String[] emails = new String[list.size()];
		
		for (int i = 0; i< list.size(); i++)
		{
			
			p = (Player)list.get(i);
			System.out.println(p.getName() + "\n" + p.getEmail());
			emails[i] = p.getEmail();
			//System.out.println(emails[i]);
		}
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Send Email");

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		final EditText inputSubject = new EditText(this);
		inputSubject.setHint("Subject");
		layout.addView(inputSubject);

		final EditText inputMessage = new EditText(this);
		inputMessage.setHint("Message");
		layout.addView(inputMessage);

		alert.setView(layout);

		// alert.setMessage("Player's information: ");
		//
		// //edit text for user input
		// final EditText input = new EditText(this);
		// alert.setView(input);

		alert.setPositiveButton("Send",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						subject = inputSubject.getText().toString();
						message = inputMessage.getText().toString();

						 Intent i = new Intent(Intent.ACTION_SEND);
							 i.setType("message/rfc822");
							 i.putExtra(Intent.EXTRA_EMAIL , emails);
							 i.putExtra(Intent.EXTRA_SUBJECT, subject);
							 i.putExtra(Intent.EXTRA_TEXT , message);
							 try {
							 startActivity(Intent.createChooser(i, "Send mail..."));
							 } catch (android.content.ActivityNotFoundException ex) {
							 Toast.makeText(DirectoryActivity.this,
							 "There are no email clients installed.", Toast.LENGTH_SHORT).show();
							 }
							 System.out.println(emails.toString());
							 System.out.println(subject);
							 System.out.println(message);
						
					}

				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// cancelled

					}
				});

		alert.show();
		
	}

	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// // TODO Auto-generated method stub
	//
	// CheckBox b = (CheckBox)buttonView;
	//
	// if(isChecked)
	// {
	// b.setChecked(true);
	// Player p = (Player) b.getTag();
	// p.setSelected(true);
	//
	// System.out.println(p.getName());
	// }
	// else
	// {
	// b.setChecked(false);
	// Player p = (Player) b.getTag();
	// p.setSelected(false);
	// }
	//
	// }
	//
}
