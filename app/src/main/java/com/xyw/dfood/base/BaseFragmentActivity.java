package com.xyw.dfood.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.xyw.dfood.view.LoadDialog;
import com.xyw.dfood.view.RootView;

public class BaseFragmentActivity extends FragmentActivity {
	private RootView mRootView = null;
	public LoadDialog loadDialog;
	public boolean open = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setFullScreen();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		loadDialog = new LoadDialog(this);
		initScreenWH();
		init(0.8f);
		mRootView = new RootView(this, open);
		setContentView(mRootView);
	}

	private void setFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void activityClose() {
		this.onDestroy();
	}

	public void showLoad() {
		if (loadDialog != null && !loadDialog.dialogShowing()) {
			loadDialog.loadDialog();
		}
	}

	public void removeLoad() {
		if (loadDialog != null && loadDialog.dialogShowing()) {
			loadDialog.removeDialog();
		}
	}

	protected int mScreenWidth;
	protected float mMenuRatio = 0.8f;
	public static int mMenuWidth;

	public static int mMenuGapWidth;

	private void initScreenWH() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
	}

	protected void init(float menuRatio) {
		this.mMenuRatio = menuRatio;
		mMenuWidth = (int) (mScreenWidth * mMenuRatio);
		mMenuGapWidth = mScreenWidth - mMenuWidth;
	}

	public void toggleView() {
		mRootView.getmSlidingView().toggleView();
	}

}
