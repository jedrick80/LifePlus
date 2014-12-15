package edu.mobicom.lifeplus;

import java.util.Date;

public class Profile {
	
	static final String TABLE_NAME = "userprofiles";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_CREDITS = "credits";
	static final String COLUMN_EXP = "experience";
	static final String COLUMN_ACTIVE = "active";

	private int id;
	private String name;
	private int credits;
	private int exp;
	private boolean active;
	
	public Profile(String name) {
		this.name = name;
		if (name.equals("test"))
			credits = 999999;
		else
			credits = 100;
		exp = 0;
		active = true;
	}
	
	public Profile(int id, String name, int credits, int exp, Boolean active){
		this.exp = exp;
		this.credits = credits;
		this.id = id;
		this.name = name;
		this.active = active;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public int getExp() {
		return exp;
	}
	
	public void gainExp(int difficulty, int type) {
		int temp = 0;
		
		switch(difficulty) {
		case 0:
			temp = 10;
			break;
		case 1:
			temp = 25;
			break;
		case 2:
			temp = 40;
			break;
		case 3:
			temp = 70;
			break;
		case 4:
			temp = 110;
			break;
		case 5:
			temp = 150;
			break;
		}
		
		if(type == 1)
			temp = (int)temp/3;
		
		this.exp = exp + temp;
	}
	
	public void gainCredits(int difficulty) {
		int temp = 0;
		
		switch(difficulty) {
		case 0:
			temp = 5;
			break;
		case 1:
			temp = 10;
			break;
		case 2:
			temp = 20;
			break;
		case 3:
			temp = 30;
			break;
		case 4:
			temp = 45;
			break;
		case 5:
			temp = 60;
			break;
		}
		
		this.credits += temp;
	}
	
	public int getLevel(){
		return (int)(0.05 * Math.sqrt(exp));
	}
	
	public int expForNextLevel(){
		if(getLevel() == 0)
			return 400;
		return (int) Math.round((Math.pow(getLevel()/0.05,2)));
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getActive() {
		return active;
	}

}
