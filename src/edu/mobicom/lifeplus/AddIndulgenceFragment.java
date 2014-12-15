package edu.mobicom.lifeplus;

import edu.mobicom.lifeplus.AddDailyQuestFragment.OnFragmentInteractionListener;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AddIndulgenceFragment extends Fragment {

	private EditText etName;
	private EditText etDesc;
	private EditText etPrice;
	private DatabaseManager db;

	private OnFragmentInteractionListener mListener;

	public static AddIndulgenceFragment newInstance() {
		AddIndulgenceFragment fragment = new AddIndulgenceFragment();
		return fragment;
	}

	public AddIndulgenceFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DatabaseManager(getActivity(), Task.DATABASE_NAME, null, 1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_add_indulgence, container,
				false);

		getActivity().setTitle("Add an Indulgence");

		etName = (EditText) v.findViewById(R.id.et_add_indulgence_name);
		etDesc = (EditText) v.findViewById(R.id.et_add_indulgence_desc);
		etPrice = (EditText) v.findViewById(R.id.et_add_indulgence_price);
		return v;
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
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
			((MainActivity) activity).onSectionAttached(9);
			((MainActivity) activity).restoreActionBar();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.list_done, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.done) {
			String name = etName.getText().toString();
			String desc = etDesc.getText().toString();

			if (name.isEmpty())
				Toast.makeText(getActivity(),
						"Please enter a name for the indulgence.",
						Toast.LENGTH_SHORT).show();
			else if (desc.isEmpty())
				Toast.makeText(getActivity(),
						"Please enter a description for the indulgence.",
						Toast.LENGTH_SHORT).show();
			else {
				Indulgence i = new Indulgence(etName.getText().toString(),
						etDesc.getText().toString(), Integer.valueOf(etPrice
								.getText().toString()));

				db.addIndulgence(i);

				Fragment indulgences_fragment = IndulgencesFragment
						.newInstance(3, db.getIndulgenceList());
				indulgences_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, indulgences_fragment).commit();
			}
		} else if (item.getItemId() == R.id.cancel) {
			Fragment indulgences_fragment = IndulgencesFragment.newInstance(3,
					db.getIndulgenceList());
			indulgences_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, indulgences_fragment).commit();
		}

		return super.onOptionsItemSelected(item);
	}
}
