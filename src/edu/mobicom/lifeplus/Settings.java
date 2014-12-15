package edu.mobicom.lifeplus;

import edu.mobicom.lifeplus.ProfileFragment.OnFragmentInteractionListener;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link Settings.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the {@link Settings#newInstance} factory
 * method to create an instance of this fragment.
 *
 */
public class Settings extends Fragment {
	private DatabaseManager db;
	private EditText etName;
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
	 * @return A new instance of fragment Settings.
	 */
	// TODO: Rename and change types and number of parameters
	public static Settings newInstance(int sectionNumber) {
		Settings fragment = new Settings();
		return fragment;
	}

	public Settings() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new DatabaseManager(getActivity(), Task.DATABASE_NAME, null, 1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_settings, container, false);

		etName = (EditText) v.findViewById(R.id.et_settings_name);
		ibNew = (ImageButton) v.findViewById(R.id.ib_settings_capture);
		ibExisting = (ImageButton) v.findViewById(R.id.ib_settings_browse);
		ivImage = (ImageView) v.findViewById(R.id.iv_settings);

		Profile p = db.getActiveProfile();

		etName.setText(p.getName());
		ivImage.setImageBitmap(p.getImage());

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

		Button btDelete = (Button) v.findViewById(R.id.bt_settings_delete);
		btDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder builder = new Builder(getActivity());
				builder.setTitle("Delete database");
				builder.setMessage("WARNING! All your data will be erased. The application will close if you continue.");

				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								getActivity()
										.deleteDatabase(Task.DATABASE_NAME);
								getActivity().finish();
							}

						});

				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}

						});

				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.create().show();
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
			((MainActivity) activity).onSectionAttached(10);
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
		if (item.getItemId() == R.id.done) {
			Profile p = db.getActiveProfile();
			String new_name = etName.getText().toString();

			if (new_name.isEmpty())
				Toast.makeText(getActivity(), "Please enter a valid username.",
						Toast.LENGTH_SHORT).show();
			else {
				p.setName(new_name);

				if (ivImage.getDrawable() != null)
					p.setImage(((BitmapDrawable) ivImage.getDrawable())
							.getBitmap());

				db.updateProfile(p);

				Fragment profile_fragment = ProfileFragment.newInstance(4);
				profile_fragment.setHasOptionsMenu(true);

				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.container, profile_fragment).commit();
			}
		} else if (item.getItemId() == R.id.cancel) {
			Fragment profile_fragment = ProfileFragment.newInstance(4);
			profile_fragment.setHasOptionsMenu(true);

			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.container, profile_fragment).commit();
		}

		return super.onOptionsItemSelected(item);
	}

}
