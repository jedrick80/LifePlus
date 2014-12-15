package edu.mobicom.lifeplus;

import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;

public class LifeManager {

	static DatabaseManager db;
	static Profile p;

	public static boolean setup(Context context) {
		db = new DatabaseManager(context, Task.DATABASE_NAME, null, 1);
		p = db.getActiveProfile();

		if (p == null) {
			p = new Profile(android.os.Build.MODEL);
//			p = new Profile("test");
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
			
			db.addTask(new Task("Finish a task", "Mark tasks, select check icon",
					0, "", "00:00", 1, true, false, false));
			db.addTask(new Task("Color-coded Names", "Based on the difficulty",
					1, "", "00:00", 1, true, false, false));
			db.addTask(new Task("Edit a task", "Long press the quest to edit",
					2, "", "00:00", 1, true, false, false));
			db.addTask(new Task("Delete a task", "Swipe from right to left",
					3, "", "00:00", 1, true, false, false));
			db.addTask(new Task("View the task's info", "Swipe from left to right",
					4, "", "00:00", 1, true, false, false));
			db.addTask(new Task("Delete all tasks", "Mark all, select trash icon",
					5, "", "00:00", 1, true, false, false));
			
			addTaskOfTheDay(context);
			db.updateLastGenerated(p);
		}
		
		boolean flag = p.generateQuest();
		if (flag == true) {
			DatabaseManager db = new DatabaseManager(context,
					Task.DATABASE_NAME, null, 1);
			//Removing generated quest
			db.deleteGenerated();
			//Changing to Undone all Daily Quest
			db.resetFinishedDailyQuest();
			//Adding Generated Task
			addTaskOfTheDay(context);
		}
		db.updateLastGenerated(p);
		
		return true;
	}
	
	public static void addTaskOfTheDay(Context context) {
		Random randomGenerator = new Random();
		int cases = randomGenerator.nextInt(14) + 1;
		Task generatedTask = null;
		
		switch (cases) {
		case 1:
			generatedTask = new Task("I'm not stinky!", "Take a bath",
					0, "", "00:00", 1, true, false, false);
			break;
		case 2:
			generatedTask = new Task("My divine breath",
					"Brush your teeth", 0, "", "00:00", 1, true, false,
					false);
			break;
		case 3:
			generatedTask = new Task("My mind is overwhelming",
					"Study", 1, "", "00:00", 1, true, false, false);
			break;
		case 4:
			generatedTask = new Task("Them muscles", "Exercise", 1,
					"", "00:00", 1, true, false, false);
			break;
		case 5:
			generatedTask = new Task("Is that a book I see?",
					"Finish 1 Chapter", 0, "", "00:00", 1, true, false,
					false);
			break;
		case 6:
			generatedTask = new Task("Mission:Socialize",
					"Talk with a friend", 0, "", "00:00", 1, true,
					false, false);
			break;
		case 7:
			generatedTask = new Task("Your on cleaning duty",
					"Clean your room", 2, "", "00:00", 1, true, false,
					false);
			break;
		case 8:
			generatedTask = new Task("Dishes don't wash themselves",
					"Wash the dishes", 1, "", "00:00", 1, true, false,
					false);
			break;
		case 9:
			generatedTask = new Task("Time for... nailcleaning?",
					"Trim your nails", 0, "", "00:00", 1, true, false,
					false);
			break;
		case 10:
			generatedTask = new Task("I like greens", "Eat veggies", 0,
					"", "00:00", 1, true, false, false);
			break;
		case 11:
			generatedTask = new Task("Planning is everything!",
					"Plan your day", 1, "", "00:00", 1, true, false,
					false);
			break;
		case 12:
			generatedTask = new Task("Mission:Stinky",
					"Take out the trash", 1, "", "00:00", 1, true,
					false, false);
			break;
		case 13:
			generatedTask = new Task("Around the Neighbourhood",
					"Jog around", 1, "", "00:00", 1, true, false, false);
			break;
		case 14:
			generatedTask = new Task("Mirror mirror on the wall",
					"Reflect on things you did", 1, "", "00:00", 1,
					true, false, false);
			break;
		}
		
		db.addTask(generatedTask);
	}

}
