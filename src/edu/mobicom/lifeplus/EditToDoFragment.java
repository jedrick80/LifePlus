package edu.mobicom.lifeplus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link EditToDoFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link EditToDoFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class EditToDoFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_ITEM_ID = "item_id";

	// TODO: Rename and change types of parameters
	private String mItemID;

	private DatabaseManager db;
	private EditText etName;
	private EditText etDesc;
	private EditText etTime;
	private EditText etDate;
	private ImageButton ibNew;
	private ImageButton ibExisting;
	private ImageView ivImage;
	private Date mDate;
	private static final int RESULT_OK = -1;
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESULT_CAMERA_REQUEST = 1888;
	private int mDifficultyPosition;
	private Task mTask;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment EditToDoFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static EditToDoFragment newInstance(String item_id) {
		EditToDoFragment fragment = new EditToDoFragment();
		Bundle args = new Bundle();
		args.putString(ARG_ITEM_ID, item_id);
		fragment.setArguments(args);
		return fragment;
	}

	public EditToDoFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mItemID = getArguments().getString(ARG_ITEM_ID);
		}
		db = new DatabaseManager(getActivity(), Task.DATABASE_NAME, null, 1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_edit_to_do, container,
				false);
		etName = (EditText) v.findViewById(R.id.et_edit_todo_name);
		etDesc = (EditText) v.findViewById(R.id.et_edit_todo_desc);
		etTime = (EditText) v.findViewById(R.id.et_edit_todo_time);
		etDate = (EditText) v.findViewById(R.id.et_edit_todo_date);
		ibNew = (ImageButton) v.findViewById(R.id.ib_edit_todo_capture);
		ibExisting = (ImageButton) v.findViewById(R.id.ib_edit_todo_browse);
		ivImage = (ImageView) v.findViewById(R.id.iv_edit_todo);
		TextView tvStatus = (TextView) v
				.findViewById(R.id.tv_edit_todo_StatusUpdate);
		TextView tvDifficulty = (TextView) v
				.findViewById(R.id.tv_edit_todo_DifficultyValue);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spinner_difficulty,
				android.R.layout.simple_spinner_item);
		final String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		mTask = null;
		for (Task t : db.getTodoList())
			if (t.getID() == Integer.parseInt(mItemID))
				mTask = t;

		etName.setText(mTask.getName());
		etDesc.setText(mTask.getDesc());
		etTime.setText(mTask.getTime());
		if (mTask.getDate() != null) {
			etDate.setText(new SimpleDateFormat("MM dd yyyy").format(mTask
					.getDate()));
			etDate.setText(monthName[Integer.parseInt(etDate.getText()
					.toString().substring(0, 2)) - 1]
					+ etDate.getText().toString().substring(2, 5)
					+ ","
					+ etDate.getText().toString().substring(5));
		}
		ivImage.setImageBitmap(mTask.getImage());

		if (mTask.getStatus() == true)
			tvStatus.setText("Finished");
		else
			tvStatus.setText("Active");

		mDifficultyPosition = mTask.getDifficulty();
		tvDifficulty.setText(adapter.getItem(mTask.getDifficulty()));

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

		etDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								etDate.setText(monthName[monthOfYear] + " "
										+ dayOfMonth + ", " + year);
								try {
									mDate = new SimpleDateFormat("yyyy-MM-dd")
											.parse(year + "-"
													+ (monthOfYear + 1) + "-"
													+ dayOfMonth);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									mDate = null;
								}

							}
						}, mYear, mMonth, mDay);
				dpd.show();
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
			((MainActivity) activity).onSectionAttached(8);
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
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.add(Calendar.DAY_OF_YEAR, -1);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);

			if (name.isEmpty())
				Toast.makeText(getActivity(),
						"Please enter a name for the task.", Toast.LENGTH_SHORT)
						.show();
			else if (desc.isEmpty())
				Toast.makeText(getActivity(),
						"Please enter a description for the task.",
						Toast.LENGTH_SHORT).show();
			else if (cal.getTime().compareTo(mDate) == 1)
				Toast.makeText(getActivity(),
						"Please enter a valid date for the task.",
						Toast.LENGTH_SHORT).show();
			else {
				mTask.setName(name);
				mTask.setDesc(desc);
				mTask.setDate(mDate);
				mTask.setTime(etTime.getText().toString());
				if (ivImage.getDrawable() != null)
					mTask.setImage(((BitmapDrawable) ivImage.getDrawable())
							.getBitmap());

				db.editTask(mTask.getID(), mTask);

				Fragment todo_fragment = CustomListFragment.newInstance(2,
						db.getTodoList());
				todo_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, todo_fragment).commit();
			}
		} else if (item.getItemId() == R.id.cancel) {
			Fragment todo_fragment = CustomListFragment.newInstance(2,
					db.getTodoList());
			todo_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, todo_fragment).commit();
		}

		return super.onOptionsItemSelected(item);
	}

}
