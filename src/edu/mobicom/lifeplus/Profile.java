package edu.mobicom.lifeplus;

import java.util.Date;

public class Profile {
	
	static final String TABLE_NAME = "ProfileOfUser";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_CREDITS = "credits";
	static final String COLUMN_EXP = "experience";

	private int id;
	private int credits;
	private int exp;
	
	public Profile(){
		exp = 0;
		credits = 100;
		id = 1;
	}
	
	public Profile(int id, int credits, int exp){
		this.exp = exp;
		this.credits = credits;
		this.id = id;
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
	public void gainExp(int difficulty) {
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
		
		this.exp = exp + temp;
	}
	public int getLevel(){
		return (int)(0.05 * Math.sqrt(exp));
	}
	
	public int expForNextLevel(){
		return (int) Math.round((Math.pow(getLevel()/0.05,2)));
	}
	


}
