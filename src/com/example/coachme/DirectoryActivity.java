package com.example.coachme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * This class extends the Activity for the Directory and controls the features
 * in the Player Directory Screen
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since March 28, 2015
 *
 */
public class DirectoryActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	// set up buttons
	private Button newPlayer, sendEmail, delete;
	// set up list of players
	private static ArrayList<Player> players = new ArrayList<Player>();
	ListView listview;
	ArrayAdapter<String> listAdapter;

	/**
	 * onCreate sets up Activity and its components
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setup user interface layout for this Activity
		setContentView(R.layout.activity_directory);

		// get saved list of players from last use of app
		players = UserDirectory.getPlayer();

		// set up button to add a player
		newPlayer = (Button) findViewById(R.id.newPlayer);
		newPlayer.setOnClickListener(this);

		// set up button to send an email
		sendEmail = (Button) findViewById(R.id.sendEmail);
		sendEmail.setOnClickListener(this);

		// set up button to delete a player
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);

		// Display mode of the ListView
		listview = (ListView) findViewById(R.id.listView);
		listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);

		// set the adapter of the ArrayList
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked,
				UserDirectory.getNames());
		listview.setAdapter(listAdapter);

		// text filtering
		listview.setTextFilterEnabled(true);

	}

	/**
	 * onClick handles clickListener
	 */
	@Override
	public void onClick(View v) {

		// cast to a button
		Button b = (Button) v;

		if (b.getId() == R.id.newPlayer) {
			// user wants to add new player
			addNewPlayer();

		} else if (b.getId() == R.id.sendEmail) {
			// user wants to send an email

			// checks if any players are selected
			if (listview.getCheckedItemCount() <= 0) {

				// sends feedback to user that no players are selected
				Toast toast = Toast.makeText(getApplicationContext(),
						"No Players Selected", Toast.LENGTH_SHORT);
				// positions the pop up in the bottom, center of the screen
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			// arrayList to store all check players
			ArrayList<Player> selectedPlayers = new ArrayList<Player>();

			SparseBooleanArray checked = listview.getCheckedItemPositions();
			for (int i = 0; i < listview.getCount(); i++)
				// add player if it is selected
				if (checked.get(i)) {
					// adds selected player
					selectedPlayers.add(new Player(players.get(i).getName(),
							players.get(i).getEmail(), false));
				}

			// sends email to selected players
			sendEmail(selectedPlayers);

		} else if (b.getId() == R.id.delete) {
			// user wants to delete player(s)

			// checks if any players are selected
			if (listview.getCheckedItemCount() <= 0) {
				// no players selected

				// Sends feedback to player that no players are selected
				Toast toast = Toast.makeText(getApplicationContext(),
						"No Players Selected", Toast.LENGTH_SHORT);
				// positions popup in bottom, center of screen
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			// deletes player(s)
			deletePlayer();

		}

	}

	/**
	 * OnItemClick Handles checked items in the adapter
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	/**
	 * Deletes a player from the list
	 */
	private void deletePlayer() {
		// warning message
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// sets title of warning message
		builder.setTitle("Delete");

		// sets message in warning
		builder.setMessage("Are you sure you want to delete the selected players?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			// user wants to delete player(s)
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				// arrayList to store selected players
				ArrayList<Player> selectedPlayers = new ArrayList<Player>();

				SparseBooleanArray checked = listview.getCheckedItemPositions();
				for (int i = 0; i < listview.getCount(); i++)
					
					// add player if it is selected
					if (checked.get(i)) {
						// add selected player
						selectedPlayers.add(new Player(
								players.get(i).getName(), players.get(i)
										.getEmail(), false));
					}

				//checks if there is no checked players
				if (selectedPlayers.size() == 0) {
					//alerts user that no players are selected
					Toast toast = Toast.makeText(getApplicationContext(),
							"No Players Selected", Toast.LENGTH_SHORT);

					//positions the popup on the screen
					toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}

				//deletes each selected player
				for (int i = 0; i < selectedPlayers.size(); i++) {
					//deletes player
					UserDirectory.deletePlayer(
							selectedPlayers.get(i).getName(), selectedPlayers
									.get(i).getEmail());
				}

				//updates adapter
				updateData();

			}

		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// do nothing
			}
		});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

	/**
	 * Adds a new player to the adapter and text file
	 */
	private void addNewPlayer() {
		//window that prompts user to enter name and email
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		//sets title of window
		alert.setTitle("New Player");

		//create layout for window
		LinearLayout layout = new LinearLayout(this);
		
		//vertical orientation of window
		layout.setOrientation(LinearLayout.VERTICAL);

		//edit text for user to input player's name
		final EditText inputName = new EditText(this);
		//hint text displays to tell user to input name
		inputName.setHint("Player's name");
		//prevents name from being multiple lines
		inputName.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		//adds edit text to layout view
		layout.addView(inputName);

		//edit text for user to input player's email
		final EditText inputEmail = new EditText(this);
		//hint text displays to tell user to input email
		inputEmail.setHint("Player's email");
		//prevents user from inputting multiple lines 
		inputEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		//adds edit text to layout view
		layout.addView(inputEmail);

		//make layout the popup's view
		alert.setView(layout);

		alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			// user added a player
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//get input name
				String playerName = inputName.getText().toString();
				//get input email
				String playerEmail = inputEmail.getText().toString();
				
				//saves the new player
				UserDirectory.savePlayer(playerName, playerEmail);

				//updates the data in the list view
				updateData();
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// cancelled

					}
				});

		final AlertDialog alertDialog = alert.show();
		alertDialog.setCanceledOnTouchOutside(true);
		final Button button = alertDialog
				.getButton(AlertDialog.BUTTON_POSITIVE);
		button.setEnabled(false);

		// disable "ok" button is user had not inputed a name of atleast one
		// char
		
		//text listener of edit text for the name
		inputName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// validation condition that name has minimum length of 1 char
				if (inputName.getText().length() > 0
						&& !inputName.getText().toString().trim().equals("")
						&& inputEmail.getText().length() > 0
						&& !inputEmail.getText().toString().trim().equals("")) {
					//minimum of 1 char input for both edit texts
					button.setEnabled(true);
				} else {
					//edit text still empty
					button.setEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		//text listener of edit text for the email
		inputEmail.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// validation condition that name has minimum length of 1 char
				if (inputEmail.getText().length() > 0
						&& !inputEmail.getText().toString().trim().equals("")
						&& inputName.getText().length() > 0
						&& !inputName.getText().toString().trim().equals("")) {
					//minimum of 1 char input for both edit texts
					button.setEnabled(true);
				} else {
					//edit text still empty
					button.setEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Updates the current data in the list view
	 */
	private void updateData() {
		//loads current data
		UserDirectory.load();
		
		//sets the players arrayList to current data
		players = UserDirectory.getPlayer();

		//resets the list view's adapter to display up to date data
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				UserDirectory.getNames());
		listview.setAdapter(listAdapter);
	}

	/**
	 * Sends an email to selected players
	 * @param list players to which will send email
	 */
	private void sendEmail(ArrayList<Player> list) {

		Player p;
		//stores list of emails
		final String[] emails = new String[list.size()];

		//get the email of all the players selected
		for (int i = 0; i < list.size(); i++) {

			p = (Player) list.get(i);
			System.out.println(p.getName() + "\n" + p.getEmail());
			emails[i] = p.getEmail();
		}

		//checks is there are any email in the list
		if (emails.length == 0) {
			//alerts player that no players are selected
			Toast toast = Toast.makeText(getApplicationContext(),
					"No Players Selected", Toast.LENGTH_SHORT);

			//positions pop up in bottom, center of screen
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}

		
		//window to get content of email
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		//title of window
		alert.setTitle("Send Email");

		//set up layout of window
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		//edit text to get subject of the message
		final EditText inputSubject = new EditText(this);
		inputSubject.setHint("Subject");
		layout.addView(inputSubject);

		//edit text to get body of the message
		final EditText inputMessage = new EditText(this);
		inputMessage.setHint("Message");
		layout.addView(inputMessage);

		alert.setView(layout);

		alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			//user wants to send the email
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//gets input subject
				String subject = inputSubject.getText().toString();
				
				// gets input message and adds signature
				String message = inputMessage.getText().toString()
						+ "\n\nSent from CoachME";

				Intent i = new Intent(Intent.ACTION_SEND);
				//prompts email clients only
				i.setType("message/rfc822");
				
				
				//setup message emails
				i.putExtra(Intent.EXTRA_EMAIL, emails);
				//setup message subject
				i.putExtra(Intent.EXTRA_SUBJECT, subject);
				//setup message body
				i.putExtra(Intent.EXTRA_TEXT, message);
				try {
					//the user can choose email client
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					//no email clients on the device
					Toast.makeText(DirectoryActivity.this,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
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

}// end class
