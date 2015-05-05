package com.ukfire.base;

public class PresenterBase<T extends IViewBase> {
	private T view;

	public PresenterBase(T mainView) {
		this.view = mainView;
	}

	public T getView() {
		return view;
	}
}
