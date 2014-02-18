
package com.cookbook.advance.ndk;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;


public class NDK extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TextView  tv = new TextView(this);
        tv.setText(" native calculation on factorial :"+factorial(30));
        setContentView(tv);
    }

    public static native int factorial(int n);

    static {
        System.loadLibrary("ndkcookbook");
    }
}
