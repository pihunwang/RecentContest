package com.ukfire.main;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ukfire.base.IViewBase;
import com.ukfire.base.PresenterBase;

public abstract class BaseHandler<P extends PresenterBase> extends Handler {
	P presenter;

	public BaseHandler(P presenter) {
		super(Looper.myLooper());
		this.presenter = presenter;
	}

	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		String jsonString = msg.getData().getString("response");
		handleCommon(msg);
		if ("".equals(jsonString))
			handlerError(msg);
		else
			handleSucc(msg);
	}

	abstract protected void handleCommon(Message msg);

	protected void handlerError(Message msg) {
		getPresenter().getView().showToast("拉取数据失败");
	}

	abstract protected void handleSucc(Message msg);

	public P getPresenter() {
		return presenter;
	}

	public IViewBase getView() {
		return presenter.getView();
	}
}
