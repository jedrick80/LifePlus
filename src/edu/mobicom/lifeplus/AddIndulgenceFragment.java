package edu.mobicom.lifeplus;

import edu.mobicom.lifeplus.AddDailyQuestFragment.OnFragmentInteractionListener;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class AddIndulgenceFragment extends Fragment {

	private EditText etName;
	private EditText etDesc;
	private EditText etPrice;
	
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
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_add_indulgence,
				container, false);
		
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
			((MainActivity) activity).onSectionAttached(5);
			((MainActivity) activity).restoreActionBar();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	/* new Indulgence(etName.getText.toString(), etDesc.getText().toString(), Integer.valueof(etPrice.getText().toString()))*/
	
}
