package com.androidtablet.draganddropimage;

import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.view.DragEvent;
import android.view.View;
import android.content.ClipDescription;
import android.content.ClipData;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.graphics.Point;
import android.graphics.Canvas;
import android.util.Log;

public class DragAndDropImageActivity extends Activity {
    GridView sourceGridView;
    GridView targetGridView;
    private ArrayList <Integer> drawables;
    private ArrayList <Integer>  targetdrawables = new ArrayList <Integer>();
    DragEventListener dragEventListener = new DragEventListener();
    TargetAdapter targetAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop_image);
        drawables = new ArrayList<Integer>();
        drawables.add(R.drawable.image1);
        drawables.add(R.drawable.image2);
        drawables.add(R.drawable.image3);
        drawables.add(R.drawable.image4);
        drawables.add(R.drawable.image5);
        drawables.add(R.drawable.image6); 
        drawables.add(R.drawable.image7);
        sourceGridView = (GridView) findViewById(R.id.sourcegrid_view);
        targetGridView = (GridView) findViewById(R.id.targetgrid_view);
        sourceGridView.setAdapter(new ImageAdapter()); 
        sourceGridView.setOnItemLongClickListener(sourceGridLongClickListener);
        sourceGridView.setOnDragListener(dragEventListener);
        targetGridView.setOnDragListener(dragEventListener);
        targetAdapter=new TargetAdapter();
        targetGridView.setAdapter(targetAdapter); 
        sourceGridView.setTag("Source GridView");
        targetGridView.setTag("Target GridView");
    } 

    OnItemLongClickListener sourceGridLongClickListener    = new  OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
            ClipData.Item item = new ClipData.Item(drawables.get(position).toString()); 
            String[] clipDescription = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData dragData = new ClipData((CharSequence) v.getTag(), clipDescription,item);
            DragShadowBuilder itemShadow = new ShadowBuilder(v);
            v.startDrag(dragData, itemShadow, drawables.get(position), 0); 
            return true;
        }
    };

    private static class ShadowBuilder extends View.DragShadowBuilder {
        private static View view;
        public ShadowBuilder(View v) {
            super(v);
            view=v;
        }

        @Override
        public void onProvideShadowMetrics (Point size, Point  touch){
            int width = getView().getWidth();
            int height = getView().getHeight();
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            view.draw(canvas);
        }
    }

    protected class ImageAdapter extends BaseAdapter{ 
        @Override
        public View getView(int position, View convertView, ViewGroup gridView) {    
            ImageView imageView = new ImageView(DragAndDropImageActivity.this); 
            imageView.setImageResource((Integer) drawables.get(position));
            int layout_width = (int) getResources().getDimension(R.dimen.layout_width);
            int layout_height = (int) getResources().getDimension(R.dimen.layout_height);           
            imageView.setLayoutParams(new GridView.LayoutParams(layout_width, layout_height)); 
            imageView.setLongClickable(true);   
            imageView.setTag(String.valueOf(position));
            return imageView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return drawables.get(position);
        }

        @Override
        public int getCount() {
            return drawables.size();
        }
    }

    protected class TargetAdapter extends BaseAdapter{ 
        @Override
        public View getView(int position, View convertView, ViewGroup gridView) {    
            ImageView imageView = new ImageView(DragAndDropImageActivity.this); 
            imageView.setImageResource((Integer) targetdrawables.get(position));
            int layout_width = (int) getResources().getDimension(R.dimen.layout_width);
            int layout_height = (int) getResources().getDimension(R.dimen.layout_height);  
            imageView.setLayoutParams(new GridView.LayoutParams(layout_width, layout_height)); 
            imageView.setLongClickable(true);   
            imageView.setTag(String.valueOf(position));
            return imageView;
        }
    
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return targetdrawables.get(position);
        }

        @Override
        public int getCount() {
            return targetdrawables.size();
        }
    }

    protected class DragEventListener implements View. 
        OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        Log.d((String) v.getTag(), "ACTION_DRAG_STARTED accepted");
                        return true; 
                    }else{
                        Log.d((String) v.getTag(), "ACTION_DRAG_STARTED rejected");
                        return false; 
                    }
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d((String) v.getTag(), "ACTION_DRAG_ENTERED");
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d((String) v.getTag(), "ACTION_DRAG_EXITED");
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DROP:
                    if(v == targetGridView){
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        Log.d((String) v.getTag(), "ACTION_DROP");        
                        String droppedItem = item.getText().toString();
                        targetdrawables.add(Integer.parseInt(droppedItem));
                        targetAdapter.notifyDataSetChanged();
                        return true;
                    }
                    else
                        return false;                
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult())
                        Log.d((String) v.getTag(), "ACTION_DRAG_ENDED successfully");
                    else 
                        Log.d((String) v.getTag(), "Failure:  ACTION_DRAG_ENDED");
                    return true;        
                default: 
                    Log.d((String) v.getTag(), "Not Known");
                    return false;
            }
        }
    }
}
