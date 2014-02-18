package cc.dividebyzero.android.cookbook.chapter8;

import java.util.ArrayList;



import cc.dividebyzero.android.cookbook.chapter8.audio.AudioPlayback;
import cc.dividebyzero.android.cookbook.chapter8.audio.AudioRecording;
import cc.dividebyzero.android.cookbook.chapter8.audio.AudioSoundPool;
import cc.dividebyzero.android.cookbook.chapter8.image.ImageManipulation;
import cc.dividebyzero.android.cookbook.chapter8.video.VideoPlayback;
import cc.dividebyzero.android.cookbook.chapter8.video.VideoViewActivity;
import cc.dividebyzero.android.cookbook.commons.ChapterDashboardActivity;

/**
 * Chapter 8 Launcher.
 * All it needs to do is add the recipes to a list, the parent class does the rest.
 * @author Ronan 'zero' Schwarz
 *
 */
public class Chapter8 extends ChapterDashboardActivity{

	@Override
	protected ArrayList<CookbookRecipe> getRecipeList() {
		ArrayList<CookbookRecipe> list = new  ArrayList<ChapterDashboardActivity.CookbookRecipe>();
		
		CookbookRecipe recipe= new CookbookRecipe("AudioPlayback",AudioPlayback.class);
		list.add(recipe);
		recipe= new CookbookRecipe("AudioRecording",AudioRecording.class);
		list.add(recipe);
		recipe= new CookbookRecipe("AudioSoundPool",AudioSoundPool.class);
		list.add(recipe);
		recipe= new CookbookRecipe("ImageManipulation",ImageManipulation.class);
		list.add(recipe);
		recipe= new CookbookRecipe("VideoView",VideoViewActivity.class);
		list.add(recipe);
		recipe= new CookbookRecipe("VideoPlayback",VideoPlayback.class);
		list.add(recipe);		
		return list;
	}

	
}