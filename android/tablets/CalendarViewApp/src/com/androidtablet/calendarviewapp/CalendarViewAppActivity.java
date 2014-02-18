package com.androidtablet.calendarviewapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class CalendarViewAppActivity extends Activity {
    private CalendarView calendarView;
    private int yr, mon, dy;
    private Calendar selectedDate;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view_app);
        Calendar c = Calendar.getInstance();
        yr = c.get(Calendar.YEAR);
        mon = c.get(Calendar.MONTH);
        dy = c.get(Calendar.DAY_OF_MONTH);
        Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        datePickerButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                  new DatePickerDialog(CalendarViewAppActivity.this, dateListener, yr, mon, dy).show();
            }
        });   
        calendarView.setOnDateChangeListener(new OnDateChangeListener() { 
            @Override 
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) { 
                Toast.makeText(getApplicationContext(),"Selected date is "+(month+1)+"-"+dayOfMonth+"-"+ year, Toast.LENGTH_SHORT). show();
            } 
        }); 
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){     
            selectedDate=Calendar.getInstance();
            yr=year;
            mon=monthOfYear;
            dy=dayOfMonth;
            selectedDate.set(yr, mon, dy);
           calendarView.setDate(selectedDate.getTimeInMillis());
        }
    };
}
