package edu.mobicom.lifeplus;

import android.content.Context;
import android.graphics.BitmapFactory;

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
			p.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
			
			db.addIndulgence(new Indulgence("Playtime isn't over!", "Play for an hour", 50));
			db.addIndulgence(new Indulgence("Me Sleepy", "Take a nap", 30));
			db.addIndulgence(new Indulgence("Sweet Dreams", "A longer nap", 90));
			db.addIndulgence(new Indulgence("I wanna play even more!", "Play for three hours", 120));
			db.addIndulgence(new Indulgence("I have a life too!", "Go out and play!", 500));
			db.addIndulgence(new Indulgence("I need to trip myself!", "Go on a trip!", 5000));
			db.addIndulgence(new Indulgence("Merry Xmas to me!", "Gift yourself", 500));
			db.addIndulgence(new Indulgence("Phew! That's quite a sweat", "Play some sports", 200));
			db.addIndulgence(new Indulgence("I crave for you!", "Eat one of your desired food", 80));
			db.addIndulgence(new Indulgence("I am craving for even more", "I need..... moar", 160));
			db.addIndulgence(new Indulgence("Oh the relaxation!", "Go to a spa", 1500));
			db.addIndulgence(new Indulgence("Its time to swim!", "Go to a pool", 2000));
			db.addIndulgence(new Indulgence("I see ocean, I see sand!", "Go to a beach", 5000));
			db.addIndulgence(new Indulgence("I dont wanna cook :(", "Eat outside", 300));
			db.addIndulgence(new Indulgence("Its time for the big screen", "Watch a movie", 350));
			db.addIndulgence(new Indulgence("I need a break", "Well take a break", 80));
			
			p.generateQuest();
			db.updateLastGenerated(p);
		}
		
		return true;
	}

}
