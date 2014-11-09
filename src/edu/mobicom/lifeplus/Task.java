package edu.mobicom.lifeplus;

public class Task {

	private int id;
	private String name;
	private String desc;
	private Boolean isChecked;
	
	public Task() {
		id = -1;
		name = "";
		desc = "";
		isChecked = false;
	}
	
	public Task(String name, String desc, Boolean isChecked) {
		id = -1;
		this.name = name;
		this.desc = desc;
		this.isChecked = isChecked;
	}
	
	public Task(int id, String name, String desc, Boolean isChecked) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.isChecked = isChecked;
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
	
	public Boolean isChecked() {
		return isChecked;
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
	
	public void setChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
