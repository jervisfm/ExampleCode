package com.androidtablet.actionbartabapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.util.Log;
import android.app.FragmentManager;
import android.app.Fragment;

public class ActionBarTabAppActivity extends Activity  {   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_tab_app);
        Fragment createFragment = new CreateActivity();
        Fragment updateFragment = new UpdateActivity();
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);   
        ActionBar.Tab CreateTab = actionBar.newTab().setText("Create");        
        ActionBar.Tab UpdateTab = actionBar.newTab().setText("Update"); 
        CreateTab.setTabListener(new MyTabsListener(createFragment));        
        UpdateTab.setTabListener(new MyTabsListener(updateFragment));
        actionBar.addTab(CreateTab);        
        actionBar.addTab(UpdateTab);
    }
    protected class MyTabsListener implements ActionBar.TabListener {
        Fragment fragment;  
        public MyTabsListener(Fragment fragment){
            this.fragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {           
            ft.replace(R.id.fragment_container, fragment, null);    
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        Log.d("Tab", String.valueOf(tab.getPosition()) + " re-selected");
        }
    }
}
