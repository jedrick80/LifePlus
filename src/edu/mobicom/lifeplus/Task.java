package edu.mobicom.lifeplus;

import java.util.Date;

import android.graphics.Bitmap;

public class Task {

	static final String TABLE_NAME = "TaskList";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_DESC = "desc";
	static final String COLUMN_DIFFICULTY = "difficulty";
	static final String COLUMN_DURATION = "duration";
	static final String COLUMN_DATE = "date";
	static final String COLUMN_TIME = "time";
	static final String COLUMN_TYPE = "type";
	static final String COLUMN_GENERATED = "generated";
	static final String COLUMN_CHECKED = "checked";
	static final String COLUMN_STATUS = "status";
	static final String COLUMN_IMAGE = "image";
	static final String DATABASE_NAME = "LifePlus";

	private int id;
	private String name;
	private String desc;
	private Date date;
	private int difficulty;
	private String duration;
	private String time;
	private Bitmap image;
	private int type; // 1 daily, 2 normal task
	private boolean generated;
	private boolean checked;
	private boolean status;

	public Task() {
		id = -1;
		name = "";
		desc = "";
		status = false;
		duration = "";
		generated = false;
	}

	public Task(String name, String desc, int difficulty,
			Date date, String time, int type, boolean generated,
			boolean checked, boolean status) {
		id = -1;
		this.name = name;
		this.desc = desc;
		this.difficulty = difficulty;
		this.date = date;
		this.time = time;
		this.type = type;
		this.generated = generated;
		this.checked = checked;
		this.status = status;
	}

	public Task(String name, String desc, int difficulty, String duration,
			String time, int type, boolean generated, boolean checked,
			boolean status) {
		id = -1;
		this.name = name;
		this.desc = desc;
		this.difficulty = difficulty;
		this.duration = duration;
		this.date = null;
		this.time = time;
		this.type = type;
		this.generated = generated;
		this.checked = checked;
		this.status = status;
	}

	public Task(int id, String name, String desc, int difficulty, Date date, String time, int type,
			boolean generated, boolean checked, boolean status) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.difficulty = difficulty;
		this.date = date;
		this.time = time;
		this.type = type;
		this.generated = generated;
		this.checked = checked;
		this.status = status;
	}

	public Task(int id, String name, String desc, int difficulty,
			String duration, String time, int type, boolean generated,
			boolean checked, boolean status) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.difficulty = difficulty;
		this.duration = duration;
		this.date = null;
		this.time = time;
		this.type = type;
		this.generated = generated;
		this.checked = checked;
		this.status = status;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getTime() {
		return time;
	}

	public Date getDate() {
		return date;
	}

	public Bitmap getImage() {
		return image;
	}

	public int getType() {
		return type;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getDuration() {
		return duration;
	}

	public boolean getGenerated() {
		return generated;
	}

	public boolean getChecked() {
		return checked;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
