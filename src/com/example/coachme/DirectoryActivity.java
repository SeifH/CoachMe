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

public class DirectoryActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private Button newPlayer, sendEmail, delete;
	private static ArrayList<Player> players = new ArrayList<Player>();
	ListView listview;
	ArrayAdapter<String> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_directory);

		players = UserDirectory.getPlayer();

		newPlayer = (Button) findViewById(R.id.newPlayer);
		newPlayer.setOnClickListener(this);

		sendEmail = (Button) findViewById(R.id.sendEmail);
		sendEmail.setOnClickListener(this);

		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);

		// Display mode of the ListView

		listview = (ListView) findViewById(R.id.listView);
		listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);

		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked,
				UserDirectory.getNames());
		listview.setAdapter(listAdapter);

		// text filtering
		listview.setTextFilterEnabled(true);

	}

	public void onListItemClick(ListView parent, View v, int position, long id) {

	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;

		if (b.getId() == R.id.newPlayer) {

			addNewPlayer();

		} else if (b.getId() == R.id.sendEmail) {

			if (listview.getCheckedItemCount() <= 0) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"No Players Selected", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			ArrayList<Player> selectedPlayers = new ArrayList<Player>();

			SparseBooleanArray checked = listview.getCheckedItemPositions();
			for (int i = 0; i < listview.getCount(); i++)
				if (checked.get(i)) {
					selectedPlayers.add(new Player(players.get(i).getName(),
							players.get(i).getEmail(), false));
				}

			sendEmail(selectedPlayers);

		} else if (b.getId() == R.id.delete) {

			if (listview.getCheckedItemCount() <= 0) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"No Players Selected", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			deletePlayer();

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	private void deletePlayer() {
		// warning message
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Delete");
		builder.setMessage("Are you sure you want to delete the selected players?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ArrayList<Player> selectedPlayers = new ArrayList<Player>();

				SparseBooleanArray checked = listview.getCheckedItemPositions();
				for (int i = 0; i < listview.getCount(); i++)
					if (checked.get(i)) {
						selectedPlayers.add(new Player(
								players.get(i).getName(), players.get(i)
										.getEmail(), false));
					}

				for (int i = 0; i < selectedPlayers.size(); i++) {
					UserDirectory.deletePlayer(
							selectedPlayers.get(i).getName(), selectedPlayers
									.get(i).getEmail());
				}

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

	private void addNewPlayer() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("New Player");

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		final EditText inputName = new EditText(this);
		inputName.setHint("Player's name");
		inputName.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		layout.addView(inputName);

		final EditText inputEmail = new EditText(this);
		inputEmail.setHint("Player's email");
		inputEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		layout.addView(inputEmail);

		alert.setView(layout);

		alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String playerName = inputName.getText().toString();
				String playerEmail = inputEmail.getText().toString();
				UserDirectory.savePlayer(playerName, playerEmail);

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
					button.setEnabled(true);
				} else {
					button.setEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

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
					button.setEnabled(true);
				} else {
					button.setEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void updateData() {
		UserDirectory.load();
		players = UserDirectory.getPlayer();

		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				UserDirectory.getNames());
		listview.setAdapter(listAdapter);
	}

	private void sendEmail(ArrayList<Player> list) {

		Player p;
		final String[] emails = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {

			p = (Player) list.get(i);
			System.out.println(p.getName() + "\n" + p.getEmail());
			emails[i] = p.getEmail();
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

		alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String subject = inputSubject.getText().toString();
				String message = inputMessage.getText().toString() + "\n\nSent from CoachME";

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, emails);
				i.putExtra(Intent.EXTRA_SUBJECT, subject);
				i.putExtra(Intent.EXTRA_TEXT, message);
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
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

}
