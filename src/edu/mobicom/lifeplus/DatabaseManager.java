package edu.mobicom.lifeplus;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {

	public DatabaseManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Task Table
		Log.i("DATABASE", "onCreate()");
		db.execSQL("CREATE TABLE " + Task.TABLE_NAME + " (" 
				+ Task.COLUMN_ID + " integer PRIMARY KEY autoincrement," 
				+ Task.COLUMN_NAME + " text," 
				+ Task.COLUMN_DESC + " text,"
				+ Task.COLUMN_DIFFICULTY + " integer," 
				+ Task.COLUMN_DURATION + " text," 
				+ Task.COLUMN_TIME + " text," 
				+ Task.COLUMN_DATE + " date," 
				+ Task.COLUMN_IMAGE + " blob,"
				+ Task.COLUMN_TYPE + " text,"
				+ Task.COLUMN_GENERATED + " boolean," 
				+ Task.COLUMN_CHECKED + " boolean," 
				+ Task.COLUMN_STATUS + " boolean)");

		db.execSQL("CREATE TABLE "+ Indulgence.TABLE_NAME + " ("
				+ Indulgence.COLUMN_ID + " integer PRIMARY KEY autoincrement,"
				+ Indulgence.COLUMN_NAME + " text,"
				+ Indulgence.COLUMN_DESC + " text,"
				+ Indulgence.COLUMN_PRICE + " integer)");
		
		db.execSQL("CREATE TABLE "+ Profile.TABLE_NAME + " ("
				+ Profile.COLUMN_ID + " integer PRIMARY KEY autoincrement,"
				+ Profile.COLUMN_EXP + " integer,"
				+ Profile.COLUMN_CREDITS + " integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void editTask(int id, Task t) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Bitmap temp = null;

		values.put(Task.COLUMN_NAME, t.getName());
		values.put(Task.COLUMN_DESC, t.getDesc());
		values.put(Task.COLUMN_DURATION, t.getDuration());
		values.put(Task.COLUMN_TIME, t.getTime());
		values.put(Task.COLUMN_DIFFICULTY, t.getDifficulty());

		temp = t.getImage();
		if (temp != null) {
			temp.compress(CompressFormat.PNG, 0, outputStream);
			values.put(Task.COLUMN_IMAGE, outputStream.toByteArray());
		}

		if (t.getGenerated() == true)
			values.put(Task.COLUMN_GENERATED, 1);
		else if (t.getGenerated() == false)
			values.put(Task.COLUMN_GENERATED, 0);

		if (t.getChecked() == true)
			values.put(Task.COLUMN_CHECKED, 1);
		else if (t.getChecked() == false)
			values.put(Task.COLUMN_CHECKED, 0);

		values.put(Task.COLUMN_TYPE, t.getType());

		if (t.getStatus() == true)
			values.put(Task.COLUMN_STATUS, 1);
		else if (t.getStatus() == false)
			values.put(Task.COLUMN_STATUS, 0);

		if (t.getType() == 2)
			if(t.getDate() != null)
				values.put(Task.COLUMN_DATE, t.getDate().toString());

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void setDone(int id, Boolean Done) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Task.COLUMN_STATUS, Done);

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void addTask(Task t) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Bitmap temp = null;

		values.put(Task.COLUMN_NAME, t.getName());
		values.put(Task.COLUMN_DESC, t.getDesc());
		values.put(Task.COLUMN_DURATION, t.getDuration());
		values.put(Task.COLUMN_TIME, t.getTime());
		values.put(Task.COLUMN_DIFFICULTY, t.getDifficulty());

		temp = t.getImage();
		if (temp != null) {
			temp.compress(CompressFormat.PNG, 0, outputStream);
			values.put(Task.COLUMN_IMAGE, outputStream.toByteArray());
		}

		if (t.getGenerated() == true)
			values.put(Task.COLUMN_GENERATED, 1);
		else if (t.getGenerated() == false)
			values.put(Task.COLUMN_GENERATED, 0);

		if (t.getChecked() == true)
			values.put(Task.COLUMN_CHECKED, 1);
		else if (t.getChecked() == false)
			values.put(Task.COLUMN_CHECKED, 0);

		values.put(Task.COLUMN_TYPE, t.getType());

		if (t.getStatus() == true)
			values.put(Task.COLUMN_STATUS, 1);
		else if (t.getStatus() == false)
			values.put(Task.COLUMN_STATUS, 0);

		if (t.getType() == 2)
			if (t.getDate() != null)
				values.put(Task.COLUMN_DATE, t.getDate().toString());
			else
				values.put(Task.COLUMN_DATE, "");

		db.insert(Task.TABLE_NAME, null, values);
		db.close();
	}

	public void deleteTask(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Task.TABLE_NAME, Task.COLUMN_ID + " =? ",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void setAsDone(int id) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Task.COLUMN_STATUS, 1);

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public ArrayList<Task> getDailyQuests() {

		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(Task.TABLE_NAME, null, Task.COLUMN_TYPE + " = ?",
				new String[] { "1" }, null, null, null);

		ArrayList<Task> daily = new ArrayList<Task>();

		if (c.moveToFirst()) {
			do {
				int id = c.getInt(c.getColumnIndex(Task.COLUMN_ID));
				String name = c.getString(c.getColumnIndex(Task.COLUMN_NAME));
				String desc = c.getString(c.getColumnIndex(Task.COLUMN_DESC));
				int difficulty = c.getInt(c
						.getColumnIndex(Task.COLUMN_DIFFICULTY));
				String duration = c.getString(c
						.getColumnIndex(Task.COLUMN_DURATION));
				String time = c.getString(c.getColumnIndex(Task.COLUMN_TIME));
				byte[] imgByte = c.getBlob(c.getColumnIndex(Task.COLUMN_IMAGE));

				int generated = c.getInt(c
						.getColumnIndex(Task.COLUMN_GENERATED));
				boolean isGenerated;
				if (generated == 1) {
					isGenerated = true;
				} else {
					isGenerated = false;
				}

				int checked = c.getInt(c.getColumnIndex(Task.COLUMN_CHECKED));
				boolean isChecked;
				if (checked == 1) {
					isChecked = true;
				} else {
					isChecked = false;
				}

				int status = c.getInt(c.getColumnIndex(Task.COLUMN_STATUS));
				boolean isFinished;
				if (status == 1) {
					isFinished = true;
				} else {
					isFinished = false;
				}

				Task t = new Task(id, name, desc, difficulty, duration, time,
						1, isGenerated, isChecked, isFinished);
				if (imgByte != null)
					t.setImage(BitmapFactory.decodeByteArray(imgByte, 0,
							imgByte.length));

				daily.add(t);
			} while (c.moveToNext());
		}

		c.close();
		db.close();

		return daily;
	}

	public ArrayList<Task> getTodoList() {

		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(Task.TABLE_NAME, null, Task.COLUMN_TYPE + " = ?",
				new String[] { "2" }, null, null, null);

		ArrayList<Task> todo = new ArrayList<Task>();

		if (c.moveToFirst()) {
			do {

				int id = c.getInt(c.getColumnIndex(Task.COLUMN_ID));
				String name = c.getString(c.getColumnIndex(Task.COLUMN_NAME));
				String desc = c.getString(c.getColumnIndex(Task.COLUMN_DESC));
				int difficulty = c.getInt(c
						.getColumnIndex(Task.COLUMN_DIFFICULTY));
				String duration = c.getString(c
						.getColumnIndex(Task.COLUMN_DURATION));
				String time = c.getString(c.getColumnIndex(Task.COLUMN_TIME));
				byte[] imgByte = c.getBlob(c.getColumnIndex(Task.COLUMN_IMAGE));

				int checked = c.getInt(c.getColumnIndex(Task.COLUMN_CHECKED));
				boolean isChecked;
				if (checked == 1) {
					isChecked = true;
				} else {
					isChecked = false;
				}

				int status = c.getInt(c.getColumnIndex(Task.COLUMN_STATUS));
				boolean isFinished;
				if (status == 1) {
					isFinished = true;
				} else {
					isFinished = false;
				}

				Date date = null;

				try {
					String tDate = c.getString(c
							.getColumnIndex(Task.COLUMN_DATE));
					if (!tDate.isEmpty())
						date = new SimpleDateFormat(
								"EEE MMM dd HH:mm:ss z yyyy").parse(tDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Task t = new Task(id, name, desc, difficulty, date, time, 2,
						false, isChecked, isFinished);
				if (imgByte != null)
					t.setImage(BitmapFactory.decodeByteArray(imgByte, 0,
							imgByte.length));
				todo.add(t);
			} while (c.moveToNext());
		}

		c.close();
		db.close();

		return todo;
	}

	public void setChecked(int id, boolean isChecked) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Task.COLUMN_CHECKED, isChecked);

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void gainEXP(Profile p, int difficulty) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		p.gainExp(difficulty);

		values.put(Profile.COLUMN_EXP, p.getExp());

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(p.getId()) });
		db.close();
	}

	public void updateCredits(Profile p) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Profile.COLUMN_CREDITS, p.getCredits());

		db.update(Task.TABLE_NAME, values, Task.COLUMN_ID + "= ?",
				new String[] { String.valueOf(p.getId()) });
		db.close();
	}

	public void addIndulgence(Indulgence i) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(Indulgence.COLUMN_NAME, i.getName());
		values.put(Indulgence.COLUMN_DESC, i.getDesc());
		values.put(Indulgence.COLUMN_PRICE, i.getPrice());

		db.insert(Task.TABLE_NAME, null, values);
		db.close();
	}

	public void deleteIndulgence(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Indulgence.TABLE_NAME, Indulgence.COLUMN_ID + " =? ",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public ArrayList<Indulgence> getIndulgenceList() {

		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(Indulgence.TABLE_NAME, null, null, null, null,
				null, null);

		ArrayList<Indulgence> indulge = new ArrayList<Indulgence>();

		if (c.moveToFirst()) {
			do {

				int id = c.getInt(c.getColumnIndex(Task.COLUMN_ID));
				String name = c.getString(c
						.getColumnIndex(Indulgence.COLUMN_NAME));
				String desc = c.getString(c
						.getColumnIndex(Indulgence.COLUMN_DESC));
				int price = c.getInt(c.getColumnIndex(Indulgence.COLUMN_PRICE));

				indulge.add(new Indulgence(id, name, desc, price));

			} while (c.moveToNext());
		}

		c.close();
		db.close();

		return indulge;
	}

}
