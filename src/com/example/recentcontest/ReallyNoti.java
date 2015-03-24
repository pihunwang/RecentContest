package com.example.recentcontest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;

public class ReallyNoti extends Activity {
	public static final int NOTIFICATION_ID = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.ic_launcher,"您半小时后有一场比赛",System.currentTimeMillis());
		n.setLatestEventInfo(this, "您半小时后有一场比赛", "final瓜", null);
		n.defaults = Notification.DEFAULT_ALL;
		nm.notify(NOTIFICATION_ID, n);
		finish();
	}

}
