package com.example.recentcontest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static ListView listView;
	private List<Oj> list = new ArrayList<Oj>();
	private ImageView shuaxin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		shuaxin = (ImageView) findViewById(R.id.shuaxin);
		listView = (ListView) findViewById(R.id.listView);
		//调用首先调用存储的数据
		SharedPreferences pref = getSharedPreferences("data", 0);
		String jsondata = pref.getString("Jsondata", null);
		if (jsondata != null) {
			list.clear();
			JsonHelper(jsondata);
			myAdapter adapter = new myAdapter(MainActivity.this,
					R.layout.list_item, list);
			listView.setAdapter(adapter);
		}

		shuaxin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MyAsync async = new MyAsync();
				async.execute("http://contests.acmicpc.info/contests.json");
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Oj oj = list.get(position);
			}

		});

	}

	/**
	 * 异步操作类
	 * 
	 * @author ukfire
	 * 
	 */
	class MyAsync extends AsyncTask<String, Integer, String> {

		BufferedReader in = null;
		ProgressDialog progressDialog;

		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("正在获取网络数据");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			list.clear();
			HttpGet get = new HttpGet(params[0]);
			HttpClient client = new DefaultHttpClient();
			try {
				HttpResponse response = client.execute(get);
				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				while ((line = in.readLine()) != null)
					sb.append(line);

				Log.i("cat", sb.toString());
				JsonHelper(sb.toString());
				//持久化存储
				SharedPreferences.Editor editor = getSharedPreferences("data",
						0).edit();
				editor.putString("Jsondata", sb.toString());
				editor.commit();
				in.close();
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			myAdapter adapter = new myAdapter(MainActivity.this,
					R.layout.list_item, list);
			listView.setAdapter(adapter);
		}

	}

	/**
	 * Json解析类
	 * 
	 * @param Jsondata
	 */
	public void JsonHelper(String Jsondata) {
		try {
			JSONArray jsonArray = new JSONArray(Jsondata);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Oj oj = new Oj();
				oj.setId(jsonObject.getString("id"));
				oj.setOj(jsonObject.getString("oj"));
				oj.setLink(jsonObject.getString("link"));
				oj.setName(jsonObject.getString("name"));
				oj.setStart_time(jsonObject.getString("start_time"));
				oj.setWeek(jsonObject.getString("week"));
				oj.setAccess(jsonObject.getString("access"));
				list.add(oj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ListView适配器类
	 * 
	 * @author Vana
	 * 
	 */
	class myAdapter extends ArrayAdapter<Oj> {

		private int resourceId;

		public myAdapter(Context context, int resource, List<Oj> list) {
			super(context, resource, list);
			resourceId = resource;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			class ViewHolder {
				TextView oj;
				TextView name;
				TextView start_time;
				TextView week;
				TextView access;
			}
			Oj oj = getItem(position);
			ViewHolder viewHolder;
			View view;

			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) view.findViewById(R.id.name);
				viewHolder.oj = (TextView) view.findViewById(R.id.oj);
				viewHolder.start_time = (TextView) view
						.findViewById(R.id.start_time);
				viewHolder.week = (TextView) view.findViewById(R.id.week);
				viewHolder.access = (TextView) view.findViewById(R.id.access);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.name.setText(oj.getName());
			viewHolder.oj.setText(oj.getOj());
			viewHolder.access.setText(oj.getAccess());
			viewHolder.week.setText(oj.getWeek());
			viewHolder.start_time.setText(oj.getStart_time());
			return view;
		}

	}

}
