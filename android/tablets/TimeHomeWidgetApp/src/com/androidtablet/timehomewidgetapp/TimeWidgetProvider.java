package com.androidtablet.timehomewidgetapp;

import java.util.Date;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.Service;
import android.os.IBinder;
import android.app.AlarmManager;
import android.os.SystemClock;

public class TimeWidgetProvider extends AppWidgetProvider {
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
	super.onDeleted(context, appWidgetIds);
	Toast.makeText(context, "onDeleted()", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDisabled(Context context) {
	super.onDisabled(context);
	Toast.makeText(context, "onDisabled()", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onEnabled(Context context) {
	super.onEnabled(context);
	Toast.makeText(context, "onEnabled()", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
	for (int i=0; i<N; i++) {
			int widgetId = appWidgetIds[i];
			Intent intent = new Intent(context, UpdateService.class);
		  intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		   intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);	
PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000, pendingIntent);
/*			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_time_home_widget_app);
			remoteViews.setOnClickPendingIntent(R.id.update_button, pendingIntent);
		appWidgetManager.updateAppWidget(widgetId, remoteViews);*/
   }
}
	
	public static class UpdateService extends Service {
		String currentTime = formatter.format(new Date());	
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			   super.onStartCommand(intent, flags, startId);
		    	RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_time_home_widget_app);
						remoteViews.setTextViewText(R.id.time_textview, currentTime);		
						AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
						  int appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
						appWidgetManager.updateAppWidget(appWidgetId, remoteViews);   
		      		stopSelf(startId);
				        return 0;
		}

		@Override
		public IBinder onBind(Intent intent) {
		return null;
		}
		}
}
