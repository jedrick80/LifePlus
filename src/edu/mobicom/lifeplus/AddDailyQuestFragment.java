package edu.mobicom.lifeplus;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link AddDailyQuestFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link AddDailyQuestFragment#newInstance}
 * factory method to create an instance of this fragment.
 *
 */
public class AddDailyQuestFragment extends Fragment {

	private EditText etName;
	private EditText etDesc;
	private EditText etTime;
	private EditText etDur;
	private Spinner spDifficulty;
	private DatabaseManager db;
	private ImageButton ibNew;
	private ImageButton ibExisting;
	private ImageView ivImage;
	private static final int RESULT_OK = -1;
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESULT_CAMERA_REQUEST = 1888;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment AddDailyQuestFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AddDailyQuestFragment newInstance() {
		AddDailyQuestFragment fragment = new AddDailyQuestFragment();
		return fragment;
	}

	public AddDailyQuestFragment() {
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
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_add_daily_quest, container,
				false);

		getActivity().setTitle("Add a Daily Quest");

		etName = (EditText) v.findViewById(R.id.et_add_quest_name);
		etDesc = (EditText) v.findViewById(R.id.et_add_quest_desc);
		etTime = (EditText) v.findViewById(R.id.et_add_quest_time);
		etDur = (EditText) v.findViewById(R.id.et_add_quest_duration);
		spDifficulty = (Spinner) v.findViewById(R.id.sp_add_quest);
		ibNew = (ImageButton) v.findViewById(R.id.ib_add_quest_capture);
		ibExisting = (ImageButton) v.findViewById(R.id.ib_add_quest_browse);
		ivImage = (ImageView) v.findViewById(R.id.iv_add_quest);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spinner_difficulty,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDifficulty.setAdapter(adapter);

		etTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				int mHour = c.get(Calendar.HOUR_OF_DAY);
				int mMinute = c.get(Calendar.MINUTE);

				// Launch Time Picker Dialog
				TimePickerDialog tpd = new TimePickerDialog(getActivity(),
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// Display Selected time in textbox
								etTime.setText(hourOfDay
										+ ":"
										+ String.format("%02d%n", minute)
												.trim());
							}
						}, mHour, mMinute, false);
				tpd.show();
			}
		});

		ibExisting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		ibNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, RESULT_CAMERA_REQUEST);
			}
		});

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		} else if (requestCode == RESULT_CAMERA_REQUEST
				&& resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			ivImage.setImageBitmap(photo);
		}
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
			((MainActivity) activity).onSectionAttached(5);
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
						"Please enter a name for the daily quest.",
						Toast.LENGTH_SHORT).show();
			else if (desc.isEmpty())
				Toast.makeText(getActivity(),
						"Please enter a description for the daily quest.",
						Toast.LENGTH_SHORT).show();
			else {
				Task newQuest = new Task(name, desc,
						spDifficulty.getSelectedItemPosition(), etDur.getText()
								.toString(), etTime.getText().toString(), 1,
						false, false, false);
				
				if (ivImage.getDrawable() != null)
					newQuest.setImage(((BitmapDrawable) ivImage.getDrawable())
							.getBitmap());

				db.addTask(newQuest);

				Fragment daily_quest_fragment = CustomListFragment.newInstance(
						1, db.getDailyQuests());
				daily_quest_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, daily_quest_fragment).commit();
			}
		} else if (item.getItemId() == R.id.cancel) {
			Fragment daily_quest_fragment = CustomListFragment.newInstance(1,
					db.getDailyQuests());
			daily_quest_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, daily_quest_fragment).commit();
		}

		return super.onOptionsItemSelected(item);
	}
}
