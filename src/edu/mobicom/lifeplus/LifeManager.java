package edu.mobicom.lifeplus;

import android.content.Context;

public class LifeManager {

	static DatabaseManager db;
	static Profile p;

	public static boolean setup(Context context) {
		db = new DatabaseManager(context, Task.DATABASE_NAME, null, 1);
		p = db.getActiveProfile();

		if (p == null) {
//			p = new Profile(android.os.Build.MODEL);
			p = new Profile("test");
			db.addProfile(p);
			
			db.addIndulgence(new Indulgence("Name", "Description", 50));
		}
		
		return true;
	}

}
