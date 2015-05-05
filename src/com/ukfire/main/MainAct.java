package com.ukfire.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recentcontest.NotificationActivity;
import com.example.recentcontest.Oj;
import com.example.recentcontest.R;

public class MainAct extends Activity implements IView, OnClickListener {

	public static ListView listView;
	private List<Oj> list = new ArrayList<Oj>();
	private ImageView refresh;
	private ProgressDialog progressDialog;

	Presenter presenter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		listView = (ListView) findViewById(R.id.listView);
		refresh = (ImageView) findViewById(R.id.shuaxin);
		refresh.setOnClickListener(this);
		presenter = new Presenter(this);
		SharedPreferences pref = getSharedPreferences("data", 0);
		String jsondata = pref.getString("Jsondata", null);
		if (jsondata != null) {
			Log.v("cat", "shared.......");
			clear();
			JsonHelper(jsondata);
			setAdapter();
		} else {
			presenter.onRenovateClick();
			Log.v("cat", "net..........");
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Oj oj = list.get(position);
				String[] ss = new String[7];
				ss[0] = oj.getId();
				ss[1] = oj.getOj();
				ss[2] = oj.getName();
				ss[3] = oj.getLink();
				ss[4] = oj.getStart_time();
				ss[5] = oj.getWeek();
				ss[6] = oj.getAccess();
				Intent intent = new Intent(MainAct.this,
						NotificationActivity.class);
				intent.putExtra("data", ss);
				startActivity(intent);
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shuaxin:
			presenter.onRenovateClick();
			break;
		}
	}

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

	@Override
	public void shared(String string) {
		SharedPreferences.Editor editor = getSharedPreferences("data", 0)
				.edit();
		editor.putString("Jsondata", string);
		editor.commit();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public void setList(List<Oj> list) {
		this.list = list;
	}

	@Override
	public void setAdapter() {
		MyAdapter adapter = new MyAdapter();
		listView.setAdapter(adapter);
	}

	@Override
	public void showToast(String string) {
		Toast.makeText(MainAct.this, string, 2000).show();
	}

	@Override
	public void stopLoading() {
		progressDialog.cancel();
	}

	@Override
	public void startLoading(String string) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在获取网络数据");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(MainAct.this).inflate(
					R.layout.list_item, null);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView oj = (TextView) view.findViewById(R.id.oj);
			TextView start_time = (TextView) view.findViewById(R.id.start_time);
			TextView week = (TextView) view.findViewById(R.id.week);
			TextView access = (TextView) view.findViewById(R.id.access);
			name.setText(list.get(position).getName());
			oj.setText(list.get(position).getOj());
			start_time.setText(list.get(position).getStart_time());
			week.setText(list.get(position).getWeek());
			access.setText(list.get(position).getAccess());
			return view;
		}
	}

}
