package com.example.recentcontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends Activity{
	TextView oj,name,link,access,time,week;
	Button gao;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationactivity);
		Intent intent = getIntent();
		String[] data = intent.getStringArrayExtra("data");
		oj = (TextView) findViewById(R.id.noti_oj);
		name = (TextView) findViewById(R.id.noti_name);
		link = (TextView) findViewById(R.id.noti_link);
		access =(TextView) findViewById(R.id.noti_access);
		time = (TextView) findViewById(R.id.noti_start_time);
		week = (TextView) findViewById(R.id.noti_week);
		gao = (Button) findViewById(R.id.gao);
		oj.setText(data[1]);
		name.setText(data[2]);
		link.setText(data[3]);
		time.setText(data[4]);
		week.setText(data[5]);
		access.setText(data[6]);
	}
}

