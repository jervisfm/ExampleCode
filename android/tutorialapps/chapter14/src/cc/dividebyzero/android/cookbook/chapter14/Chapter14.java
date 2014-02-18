package cc.dividebyzero.android.cookbook.chapter14;

import java.util.ArrayList;

import cc.dividebyzero.android.cookbook.commons.ChapterDashboardActivity;

public class Chapter14 extends ChapterDashboardActivity {

	@Override
	protected ArrayList<CookbookRecipe> getRecipeList() {
		ArrayList<CookbookRecipe> list = new ArrayList<ChapterDashboardActivity.CookbookRecipe>();
		
		list.add(new CookbookRecipe("GCM Push Receiver",GCMPushReceiver.class));
		
		return list;
	}

}
