package com.ukfire.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Handler;

import com.ukfire.base.RunnableBase;

public class RenovateRunnable extends RunnableBase {

	private static final String url = "http://contests.acmicpc.info/contests.json";
	private BufferedReader in = null;

	public RenovateRunnable(Handler handler) {
		super(handler);
	}

	@Override
	public void run() {
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			while((line = in.readLine()) != null)
				sb.append(line);
			sendMessage(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
