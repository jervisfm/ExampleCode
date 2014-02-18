package com.androidtablet.dragdroplistapp;

import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.util.Log;

public class DragDropListAppActivity extends Activity {
    LinearLayout targetLayout;
    ListView sourceListView, targetListView;
    DragEventListener dragEventListener = new DragEventListener();
    String[] food ={"Pizza","Hot Dog","Chowmein","Burger", "Sandwich","Finger Fries","Cold Drink","Ice Cream" };
    List<String> targetArrayList;
    ArrayAdapter<String> targetAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop_list_app);
        sourceListView = (ListView)findViewById(R.id.sourcelist);                                 
        targetListView = (ListView)findViewById(R.id.targetlist);                                 
        targetLayout = (LinearLayout)findViewById(R.id.targetlayout);                              
        sourceListView.setTag("Source ListView");            
        targetListView.setTag("Target ListView");            
        targetLayout.setTag("Target Layout");                
        sourceListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, food)); 
        sourceListView.setOnItemLongClickListener(sourceListItemLongClickListener);
        targetArrayList = new ArrayList<String>();           
        targetAdapter = new ArrayAdapter<String>(this, R.layout.list_item, targetArrayList);
        targetListView.setAdapter(targetAdapter);
        sourceListView.setOnDragListener(dragEventListener);  
        targetLayout.setOnDragListener(dragEventListener);   
    }

    OnItemLongClickListener sourceListItemLongClickListener    =   new OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> l, View v,  int position, long id) {
            ClipData.Item foodItem = new ClipData.Item(food[position]);   
            String[] clipDescription = {ClipDescription.MIMETYPE_TEXT_PLAIN}; 
            ClipData dragData = new ClipData((CharSequence) v.getTag(), clipDescription, foodItem);      
            DragShadowBuilder foodItemShadow = new ShadowBuilder(v);                            
            v.startDrag(dragData, foodItemShadow, food[position], 0); 
            return true;
        }
    };

    private static class ShadowBuilder extends View.DragShadowBuilder {                         
        private static Drawable shadow;
        public ShadowBuilder(View v) {                   
            super(v);
            shadow = new ColorDrawable(Color.CYAN);
        }

        @Override
        public void onProvideShadowMetrics (Point size, Point  touch){                                    
            int width = getView().getWidth();
            int height = getView().getHeight();
            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {      
            shadow.draw(canvas);
        }
    }

    protected class DragEventListener implements  View.OnDragListener {                          
        @Override
        public boolean onDrag(View v, DragEvent event) { 
            switch(event.getAction()) {               
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription. MIMETYPE_TEXT_PLAIN)) {
                        Log.d((String) v.getTag(),  "ACTION_DRAG_STARTED accepted");
                        return true; 
                    }
                    else{
                        Log.d((String) v.getTag(), "ACTION_DRAG_STARTED rejected");
                        return false; 
                    }
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d((String) v.getTag(), "ACTION_DRAG_ENTERED");
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d((String) v.getTag(), "ACTION_DRAG_LOCATION " + event.getX() +  " : " + event.getY());
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d((String) v.getTag(), "ACTION_DRAG_EXITED");
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item foodItem =  event.getClipData().getItemAt(0);
                    Log.d((String) v.getTag(), "ACTION_DROP");
                    if(v == targetLayout){
                        String droppedItem = foodItem.getText(). 
                            toString();
                        Log.d("Item dropped is ", droppedItem);
                        targetArrayList.add(droppedItem);
                        targetAdapter.notifyDataSetChanged();
                        return true;
                    }
                    else
                        return false;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult())
                        Log.d((String) v.getTag(),  "ACTION_DRAG_ENDED successfully");
                    else 
                        Log.d((String) v.getTag(), "Failure: ACTION_DRAG_ENDED");
                        return true;
                default: 
                    Log.d((String) v.getTag(), "Not Known");
                    return false;
            }
        } 
    }
}
