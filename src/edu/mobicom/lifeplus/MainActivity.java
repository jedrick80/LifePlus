package edu.mobicom.lifeplus;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private Fragment daily_quest_fragment;
	private Fragment todo_list_fragment;
	private Fragment indulgences_fragment;
	private Fragment profile_fragment;
	private DatabaseManager db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		deleteDatabase(Task.DATABASE_NAME);
		overridePendingTransition(0, 0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		db = new DatabaseManager(this, Task.DATABASE_NAME, null, 1);

		daily_quest_fragment = CustomListFragment.newInstance(1,
				db.getDailyQuests());
		daily_quest_fragment.setHasOptionsMenu(true);

		todo_list_fragment = CustomListFragment
				.newInstance(2, db.getTodoList()); 
		todo_list_fragment.setHasOptionsMenu(true);

		indulgences_fragment = IndulgencesFragment.newInstance(3,
				db.getIndulgenceList());
		indulgences_fragment.setHasOptionsMenu(true);
		
		profile_fragment = ProfileFragment.newInstance(4);
		profile_fragment.setHasOptionsMenu(true);

		switch (position) {
		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.container, daily_quest_fragment).commit();
			break;
		case 1:
			fragmentManager.beginTransaction()
					.replace(R.id.container, todo_list_fragment).commit();
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.container, indulgences_fragment).commit();
			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.container, profile_fragment).commit();
			break;
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		case 7:
			mTitle = getString(R.string.title_section7);
			break;
		case 8:
			mTitle = getString(R.string.title_section8);
			break;
		case 9:
			mTitle = getString(R.string.title_section9);
			break;
		case 10:
			mTitle = getString(R.string.title_section10);
			break;
		case 11:
			mTitle = getString(R.string.title_section11);
			break;
		case 12:
			mTitle = getString(R.string.title_section12);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();

			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			
		}
		
		return super.onOptionsItemSelected(item);
	}

}
