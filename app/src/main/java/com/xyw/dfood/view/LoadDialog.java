package com.xyw.dfood.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.xyw.dfood.R;

public class LoadDialog {
	private Context mContext;
	private Dialog mDialog;

	public LoadDialog(Context context) {
		mContext = context;
	}

	public Dialog loadDialog() {
		mDialog = new Dialog(mContext) {
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return super.onKeyDown(keyCode, event);
			}
		};
		LayoutInflater in = LayoutInflater.from(mContext);
		View viewDialog = in.inflate(R.layout.load_dialog, null);
		ImageView iv = (ImageView) viewDialog.findViewById(R.id.loading_iv);
		Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.loading);
		animation.setInterpolator(new LinearInterpolator());
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(viewDialog);
		mDialog.setCanceledOnTouchOutside(false);
		iv.startAnimation(animation);
		mDialog.show();
		return mDialog;
	}

	public boolean dialogShowing() {
		if (mDialog == null) {
			return false;
		}
		if (mDialog.isShowing()) {
			return true;
		} else {
			return false;
		}
	}

	public void removeDialog() {
		mDialog.dismiss();
	}
}