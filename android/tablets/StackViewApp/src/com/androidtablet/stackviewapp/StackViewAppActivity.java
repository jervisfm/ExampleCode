package com.androidtablet.stackviewapp;

import android.os.Bundle; 
import android.app.Activity; 
import android.content.Context; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.ImageView; 
import android.widget.StackView; 
import android.widget.BaseAdapter;

public class StackViewAppActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view_app);
        StackView stackView = (StackView)this.findViewById(R.id.stackview); 
        stackView.setAdapter(new ImageAdapter(this)); 
    }

    public class ImageAdapter extends BaseAdapter {
        private Context contxt;
        Integer[] images = {
            R.drawable.prod1,
            R.drawable.prod2,
            R.drawable.prod3,
            R.drawable.prod4,
            R.drawable.prod5
        };

        public ImageAdapter(Context c) {
            contxt = c;
        }

        public int getCount() {
            return images.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup parent) {  
            if (view == null) {   
                LayoutInflater vi = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = vi.inflate(R.layout.item, null, false); 
            }  
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
            imageView.setImageResource(images[position]); 
            return view; 
        }
    }   
}
