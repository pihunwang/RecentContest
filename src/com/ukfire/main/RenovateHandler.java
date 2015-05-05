package com.ukfire.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Message;

import com.example.recentcontest.Oj;

public class RenovateHandler extends BaseHandler<Presenter> {

	private List<Oj> list = new ArrayList<Oj>();

	public RenovateHandler(Presenter presenter) {
		super(presenter);
	}

	@Override
	protected void handleCommon(Message msg) {
		getPresenter().getView().stopLoading();
	}

	@Override
	protected void handleSucc(Message msg) {
		String string = msg.getData().getString("response");
		((IView) getView()).shared(string);
		((IView) getView()).clear();
		try {
			JSONArray jsonArray = new JSONArray(string);
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
		((IView) getView()).setList(list);
		((IView) getView()).setAdapter();
	}

}
