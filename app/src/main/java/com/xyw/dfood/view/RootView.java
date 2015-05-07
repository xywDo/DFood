package com.xyw.dfood.view;


import android.content.Context;
import android.widget.FrameLayout;

import com.xyw.dfood.base.BaseFragmentActivity;

public class RootView extends FrameLayout {
	private SlidingView mSlidingView;
	private MenuView mMenuView;
	private boolean open;

	public RootView(Context context, boolean open) {
		super(context);
		this.open = open;
		init();
	}

	private void init() {
		addMenu();
		addSliding();
	}

	private void addMenu() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mMenuView = new MenuView(getContext());
		params.width = BaseFragmentActivity.mMenuWidth;
		addView(mMenuView, params);
	}

	private void addSliding() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mSlidingView = new SlidingView(getContext(), open);
		addView(mSlidingView, params);
		mSlidingView.setMenuView(mMenuView);
	}

	public SlidingView getmSlidingView() {
		return mSlidingView;
	}

}
