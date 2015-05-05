package com.ukfire.main;

import java.util.List;

import com.example.recentcontest.Oj;
import com.ukfire.base.IViewBase;
import com.ukfire.main.MainAct.MyAdapter;

public interface IView extends IViewBase {

	void setList(List<Oj> list);

	void setAdapter();
	
	void shared(String string);
	
	void clear();

}
