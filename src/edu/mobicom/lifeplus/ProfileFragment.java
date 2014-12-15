package edu.mobicom.lifeplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ProfileFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ProfileFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class ProfileFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";

	private int mSectionNumber;
	private DatabaseManager db;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ProfileFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ProfileFragment newInstance(int sectionNumber) {
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ProfileFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
		}
		db = new DatabaseManager(getActivity(), Task.DATABASE_NAME, null, 1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_profile, container, false);

		TextView name = (TextView) v.findViewById(R.id.tv_name);
		TextView level = (TextView) v.findViewById(R.id.tv_level);
		TextView EXP = (TextView) v.findViewById(R.id.tv_EXP);
		TextView credits = (TextView) v.findViewById(R.id.tv_credits_value);
		ImageView image = (ImageView) v.findViewById(R.id.iv_profile);
		Profile p = db.getActiveProfile();

		if (p != null) {
			name.setText(p.getName());
			level.setText("Level: " + String.format("%02d", p.getLevel()));
			EXP.setText(p.getExp() * 100.0f / p.expForNextLevel() + "%");
			credits.setText(p.getCredits() + "");
			image.setImageBitmap(p.getImage());
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
		inflater.inflate(R.menu.profile, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			Fragment settings_fragment = Settings.newInstance(10);
			settings_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, settings_fragment).commit();
		} else if (item.getItemId() == R.id.help) {
			Fragment help_fragment = HelpFragment.newInstance();
			help_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, help_fragment).commit();
		} else if (item.getItemId() == R.id.about) {
			new AlertDialog.Builder(getActivity())
					.setTitle("About")
					.setMessage(
							"Life+ is an application that will remind you of the things you have to accomplish. Life+ hopes that you enjoy doing chores and the things you have to do as you earn credits and reward yourself with prizes you could purchase.\n\n-Chua, Ver, & Yao")
					.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// continue with delete
								}
							}).setIcon(R.drawable.ic_action_about_dark).show();
		}

		return super.onOptionsItemSelected(item);
	}

	public static class HelpFragment extends Fragment {

		public static HelpFragment newInstance() {
			HelpFragment fragment = new HelpFragment();
			return fragment;
		}

		public HelpFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.help, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(11);
			((MainActivity) activity).restoreActionBar();
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// TODO Auto-generated method stub
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.view_task, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.discard) {
				Fragment profile_fragment = ProfileFragment.newInstance(4);
				profile_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, profile_fragment).commit();
			}

			return super.onOptionsItemSelected(item);
		}

	}

}
