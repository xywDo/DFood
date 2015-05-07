package com.xyw.dfood.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyw.dfood.R;

public class MenuLinearLayout extends LinearLayout {

	private LinearLayout ll;
	private ImageView ivLeft, ivRight;
	private TextView tv;
	private boolean isOut = false;
	private MenuLLOnClick mlloc;

	public MenuLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(
				R.layout.menu_ll, null);
		ivLeft = (ImageView) ll.findViewById(R.id.iv_left);
		ivRight = (ImageView) ll.findViewById(R.id.iv_right);
		tv = (TextView) ll.findViewById(R.id.tv);
		this.addView(ll);
	}

	public void setMlloc(MenuLLOnClick m) {
		mlloc = m;
	}

	public void setRes( String tvRes, int ivRightRes) {
		this.ivRight.setImageResource(ivRightRes);
		this.tv.setText(tvRes);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			ll.setPressed(true);
			ivLeft.setPressed(true);
			ivRight.setPressed(true);
			tv.setPressed(true);
			break;
		case MotionEvent.ACTION_MOVE:
			if (isOut) {
				break;
			}
			if (event.getX() < 0 || event.getX() > this.getWidth()
					|| event.getY() < 0 || event.getY() > this.getHeight()) {
				ll.setPressed(false);
				ivLeft.setPressed(false);
				ivRight.setPressed(false);
				tv.setPressed(false);
				isOut = true;
			}
			break;
		case MotionEvent.ACTION_UP:// ̧��
			ll.setPressed(false);
			ivLeft.setPressed(false);
			ivRight.setPressed(false);
			tv.setPressed(false);
			if (!isOut && mlloc != null) {
				mlloc.menuOnClick(this);
			}
			isOut = false;
			break;
		}
		return true;
	}

	public interface MenuLLOnClick {
		public void menuOnClick(MenuLinearLayout view);
	}

}
