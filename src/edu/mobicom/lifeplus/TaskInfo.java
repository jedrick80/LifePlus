package edu.mobicom.lifeplus;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TaskInfo.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the {@link TaskInfo#newInstance} factory
 * method to create an instance of this fragment.
 *
 */
public class TaskInfo extends Fragment {
	private DatabaseManager db;
	private static int mItemID;
	private Task mTask;

	private OnFragmentInteractionListener mListener;
	
	public static TaskInfo newInstance(int itemID) {
		TaskInfo fragment = new TaskInfo();
		mItemID = itemID;
		return fragment;
	}

	public TaskInfo() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new DatabaseManager(getActivity(), Task.DATABASE_NAME, null, 1);
		
		for (Task t : db.getDailyQuests())
			if (t.getID() == mItemID)
				mTask = t;
		for (Task t : db.getTodoList())
			if (t.getID() == mItemID)
				mTask = t;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_task_info, container, false);
		
		if(mTask != null) {
			TextView tvName = (TextView) v.findViewById(R.id.tv_view_name);
			TextView tvDesc = (TextView) v.findViewById(R.id.tv_view_desc);
			TextView tvDiff = (TextView) v.findViewById(R.id.tv_view_diff);
			TextView tvDateDurr = (TextView) v.findViewById(R.id.tv_view_date_durr);
			TextView tvDateDurrLabel = (TextView) v.findViewById(R.id.tv_view_date_durr_label);
			TextView tvTime = (TextView) v.findViewById(R.id.tv_view_time);
			TextView tvStat = (TextView) v.findViewById(R.id.tv_view_stat);
			ImageView ivImage = (ImageView) v.findViewById(R.id.iv_view);
			
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					getActivity(), R.array.spinner_difficulty,
					android.R.layout.simple_spinner_item);
			
			tvName.setText(mTask.getName());
			tvDesc.setText(mTask.getDesc());
			tvDiff.setText(adapter.getItem(mTask.getDifficulty()));
			
			if(mTask.getType() == 1) {
				tvDateDurrLabel.setText("Duration: ");
				tvDateDurr.setText(mTask.getDuration());
			} else if (mTask.getType() == 2) {
				tvDateDurrLabel.setText("Date: ");
				tvDateDurr.setText(new SimpleDateFormat("Mmm dd yyyy").format(mTask.getDate()));
			}
			
			if(mTask.getTime().equals("00:00"))
				tvTime.setText("");
			else
				tvTime.setText(mTask.getTime());
			
			if (mTask.getStatus() == true)
				tvStat.setText("Finished");
			else
				tvStat.setText("Active");
			
			if(mTask.getImage() != null)
				ivImage.setImageBitmap(mTask.getImage());
		}
		
		return v;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = new OnFragmentInteractionListener() {

				@Override
				public void onFragmentInteraction(Uri uri) {
					// TODO Auto-generated method stub

				}
			};
			((MainActivity) activity).onSectionAttached(12);
			((MainActivity) activity).restoreActionBar();
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
		public void onFragmentInteraction(Uri uri);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.view_task, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.discard) {
			if(mTask.getType() == 1) {
				Fragment daily_quest_fragment = CustomListFragment.newInstance(1,
						db.getDailyQuests());
				daily_quest_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, daily_quest_fragment).commit();
			} else if (mTask.getType() == 2) {
				Fragment todo_fragment = CustomListFragment.newInstance(2,
						db.getTodoList());
				todo_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, todo_fragment).commit();
			}
		}
		
		return super.onOptionsItemSelected(item);
	}

}
