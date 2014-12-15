package edu.mobicom.lifeplus;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.app.Fragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the
 * ListView with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class CustomListFragment extends Fragment implements
		AbsListView.OnItemClickListener {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ARG_VALUES = "values";

	// TODO: Rename and change types of parameters
	private int mSectionNumber;
	private ArrayList<Task> mValues;
	private SwipeDetector swipe = new SwipeDetector();
	DatabaseManager db;

	private static enum Action {
		LR, // Left to Right
		RL, // Right to Left
		TB, // Top to bottom
		BT, // Bottom to Top
		None // when no action was detected
	}

	private OnFragmentInteractionListener mListener;

	/**
	 * The fragment's ListView/GridView.
	 */
	private AbsListView mListView;

	/**
	 * The Adapter which will be used to populate the ListView/GridView with
	 * Views.
	 */
	private ListAdapter mAdapter;

	// TODO: Rename and change types of parameters
	public static CustomListFragment newInstance(int sectionNumber,
			ArrayList<Task> values) {
		CustomListFragment fragment = new CustomListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putSerializable(ARG_VALUES, values);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CustomListFragment() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
			mValues = (ArrayList<Task>) getArguments().getSerializable(
					ARG_VALUES);
		}

		mAdapter = new CustomAdapter(getActivity(), mValues);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_custom_list, container,
				false);
		db = new DatabaseManager(getActivity(),
				Task.DATABASE_NAME, null, 1);

		// Set the adapter
		mListView = (AbsListView) view.findViewById(android.R.id.list);
		mListView.setAdapter(mAdapter);

		// Set OnItemClickListener 
		mListView.setOnItemClickListener(this);
		mListView.setLongClickable(true);
		mListView.setOnTouchListener(swipe);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				RelativeLayout rl = (RelativeLayout) arg1;
				TextView itemID = (TextView) rl.findViewById(R.id.itemID);
				String sItemID = itemID.getText().toString();

				Task temp = null;
				for (Task t : db.getDailyQuests())
					if (t.getID() == Integer.parseInt(sItemID))
						temp = t;
				for (Task t : db.getTodoList())
					if (t.getID() == Integer.parseInt(sItemID))
						temp = t;

				if (temp.getStatus()) {
					Toast.makeText(getActivity(),
							"Finished tasks can no longer be edited.", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (mSectionNumber == 1) {
						Fragment edit_daily_quest_fragment = EditDailyQuestFragment
								.newInstance(sItemID);
						edit_daily_quest_fragment.setHasOptionsMenu(true);
						getFragmentManager()
								.beginTransaction()
								.replace(R.id.container,
										edit_daily_quest_fragment).commit();
					} else if (mSectionNumber == 2) {
						Fragment edit_todo_fragment = EditToDoFragment
								.newInstance(sItemID);
						edit_todo_fragment.setHasOptionsMenu(true);
						getFragmentManager().beginTransaction()
								.replace(R.id.container, edit_todo_fragment)
								.commit();
					}
				}

				return true;
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = new OnFragmentInteractionListener() {

				@Override
				public void onFragmentInteraction(String id) {
					// TODO Auto-generated method stub

				}
			};
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			// mListener
			// .onFragmentInteraction(DummyContent.ITEMS.get(position).id);
			RelativeLayout rl = (RelativeLayout) view;
			final TextView itemID = (TextView) rl.findViewById(R.id.itemID);
			final TextView name = (TextView) rl.findViewById(R.id.itemName);

			if (swipe.swipeDetected()) {
				if (swipe.getAction() == Action.RL) {
					Builder builder = new Builder(getActivity());
					builder.setTitle("Delete task");
					builder.setMessage("Are you sure you want to delete \""
							+ name.getText().toString() + "\"?");

					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(
											getActivity(),
											"\"" + name.getText().toString()
													+ "\" was deleted.",
											Toast.LENGTH_SHORT).show();
									db.deleteTask(Integer.parseInt(itemID
											.getText().toString()));
									if (mSectionNumber == 1)
										mAdapter = new CustomAdapter(
												getActivity(), db
														.getDailyQuests());
									else if (mSectionNumber == 2)
										mAdapter = new CustomAdapter(
												getActivity(), db.getTodoList());
									mListView.setAdapter(mAdapter);
								}

							});

					builder.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}

							});

					builder.create().show();
				}
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		if (mSectionNumber == 1)
			inflater.inflate(R.menu.list_daily_quest, menu);
		else if (mSectionNumber == 2)
			inflater.inflate(R.menu.list_todo, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_quest) {
			Fragment add_daily_quest_fragment = AddDailyQuestFragment
					.newInstance();
			add_daily_quest_fragment.setHasOptionsMenu(true);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, add_daily_quest_fragment).commit();
		} else if (item.getItemId() == R.id.add_todo) {
			Fragment add_todo_fragment = AddToDoFragment.newInstance();
			add_todo_fragment.setHasOptionsMenu(true);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, add_todo_fragment).commit();
		} else if (item.getItemId() == R.id.marked_delete) {
			showAlertDialog(0);
		} else if (item.getItemId() == R.id.marked_done) {
			showAlertDialog(1);
		}

		return super.onOptionsItemSelected(item);
	}

	public void showAlertDialog(final int type) {
		Builder builder = new Builder(getActivity());

		String alert = null;
		String message = null;

		switch (type) {
		case 0:
			if (mSectionNumber == 1) {
				alert = "Delete all marked daily quests";
				message = "Are you sure you want to delete all marked daily quests?";
			} else if (mSectionNumber == 2) {
				alert = "Delete all marked to-do's";
				message = "Are you sure you want to delete all marked to-do's?";
			}
			break;
		case 1:
			if (mSectionNumber == 1) {
				alert = "Set all marked daily quests to done";
				message = "Are you sure you want to set all marked daily quests to done?";
			} else if (mSectionNumber == 2) {
				alert = "Set all marked to-do's to done";
				message = "Are you sure you want to set all marked to-do's to done?";
			}
			break;
		}

		builder.setTitle(alert);
		builder.setMessage(message);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseManager db = new DatabaseManager(getActivity(),
						Task.DATABASE_NAME, null, 1);

				switch (type) {
				case 0:
					for (int i = 0; i < mAdapter.getCount(); i++) {
						Task temp = (Task) mAdapter.getItem(i);
						if (temp.getChecked())
							db.deleteTask(temp.getID());
					}

					if (mSectionNumber == 1)
						mAdapter = new CustomAdapter(getActivity(), db
								.getDailyQuests());
					else if (mSectionNumber == 2)
						mAdapter = new CustomAdapter(getActivity(), db
								.getTodoList());
					mListView.setAdapter(mAdapter);
					break;
				case 1:
					for (int i = 0; i < mAdapter.getCount(); i++) {
						Task temp = (Task) mAdapter.getItem(i);
						if (temp.getChecked()) {
							db.setAsDone(temp.getID());
							db.setChecked(temp.getID(), false);
						}
					}

					if (mSectionNumber == 1)
						mAdapter = new CustomAdapter(getActivity(), db
								.getDailyQuests());
					else if (mSectionNumber == 2)
						mAdapter = new CustomAdapter(getActivity(), db
								.getTodoList());
					mListView.setAdapter(mAdapter);
					break;
				}
			}

		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}

		});

		builder.create().show();
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(String id);
	}

	public class SwipeDetector implements View.OnTouchListener {

		private static final String logTag = "SwipeDetector";
		private static final int MIN_DISTANCE = 100;
		private float downX, downY, upX, upY;
		private Action mSwipeDetected = Action.None;

		public boolean swipeDetected() {
			return mSwipeDetected != Action.None;
		}

		public Action getAction() {
			return mSwipeDetected;
		}

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				downX = event.getX();
				downY = event.getY();
				mSwipeDetected = Action.None;
				return false; // allow other events like Click to be processed
			}
			case MotionEvent.ACTION_MOVE: {
				upX = event.getX();
				upY = event.getY();

				float deltaX = downX - upX;
				float deltaY = downY - upY;

				// horizontal swipe detection
				if (Math.abs(deltaX) > MIN_DISTANCE) {
					// left or right
					if (deltaX < 0) {
						mSwipeDetected = Action.LR;
						return true;
					}
					if (deltaX > 0) {
						mSwipeDetected = Action.RL;
						return true;
					}
				} else

				// vertical swipe detection
				if (Math.abs(deltaY) > MIN_DISTANCE) {
					// top or down
					if (deltaY < 0) {
						mSwipeDetected = Action.TB;
						return false;
					}
					if (deltaY > 0) {
						mSwipeDetected = Action.BT;
						return false;
					}
				}
				return true;
			}
			}
			return false;
		}
	}

}
