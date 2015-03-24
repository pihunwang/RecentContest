package com.example.recentcontest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends Activity implements OnClickListener {
	TextView oj, name, link, access, time, week;
	Button gao;
	String ddd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationactivity);
		Intent intent = getIntent();
		String[] data = intent.getStringArrayExtra("data");
		oj = (TextView) findViewById(R.id.noti_oj);
		name = (TextView) findViewById(R.id.noti_name);
		link = (TextView) findViewById(R.id.noti_link);
		access = (TextView) findViewById(R.id.noti_access);
		time = (TextView) findViewById(R.id.noti_start_time);
		week = (TextView) findViewById(R.id.noti_week);
		gao = (Button) findViewById(R.id.gao);
		ddd = data[4];
		oj.setText(data[1]);
		name.setText(data[2]);
		link.setText(data[3]);
		time.setText(data[4]);
		week.setText(data[5]);
		access.setText(data[6]);
		gao.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Date date2 = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Log.i("cat",df.format(new Date())+"");
		Date date1 = new Date();
		long d1 = date1.getTime();
		try {
			date2 = df.parse(ddd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d2 = date2.getTime();
		//比赛开始前半个小时到系统当前时间的相对时间
		long diff = d2 - d1 - 1800000;
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this,AlarmBroadcastReceiver.class);
		intent.setAction("Action.Alarm");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ diff, pi);
		
		
		
	}
}
