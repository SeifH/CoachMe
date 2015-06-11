package com.example.coachme;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Create a Fragment instance for the navigation drawer
 * 
 * @author Seif Hassan & Olivia Perryman
 * @since Friday, May 15 2015
 *
 */
public class DrawerFragment extends Fragment {

	// textView contains items of navigation drawer
	TextView text;

	/**
	 * This method instantiates the user interface view of the fragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle args) {

		// Defines the xml file for the fragment
		View view = inflater.inflate(R.layout.drawer, container, false);
		// get back arguments
		String menu = getArguments().getString("Menu");
		// set up text view that contains list of items
		text = (TextView) view.findViewById(R.id.detail);
		text.setText(menu);
		// returns view
		return view;
	}

}// end class
