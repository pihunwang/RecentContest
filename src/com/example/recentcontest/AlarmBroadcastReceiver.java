package com.example.recentcontest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 这里可以写具体的提醒代码,可以加上notification和声音提醒
 * 
 * @author Vana
 * 
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("Action.Alarm")) {
			Log.i("cat","naozhong xiang le");
			Toast.makeText(context, "比赛还有半个小时就开始啦", 5000).show();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, ReallyNoti.class);
			context.startActivity(intent);
		}
	}

}
