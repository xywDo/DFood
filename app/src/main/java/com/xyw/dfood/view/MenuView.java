package com.xyw.dfood.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.xyw.dfood.R;
import com.xyw.dfood.base.BaseFragmentActivity;

public class MenuView extends SlideableView {
	private ViewGroup mContainer;

	public MenuView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundColor(0XFF1A1A1A);
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(
				R.layout.layout_menu, null);
		mContainer.setBackgroundColor(0xffffffff);
		scrollTo(BaseFragmentActivity.mMenuGapWidth, getScrollY());
		addView(mContainer);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}

		return super.onTouchEvent(event);
	}

}
