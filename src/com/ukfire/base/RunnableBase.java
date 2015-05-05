package com.ukfire.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class RunnableBase implements Runnable {
	private Handler handler;

	public RunnableBase(Handler handler) {
		this.handler = handler;
	}

	protected Handler getHandler() {
		return handler;
	}

	protected void sendMessage(int what, String response) {
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putString("response", response);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	protected void sendMessage(String response) {
		sendMessage(0, response);
	}
}
