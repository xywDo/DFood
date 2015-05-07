package com.xyw.dfood.view;


import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.xyw.dfood.R;
import com.xyw.dfood.base.BaseFragmentActivity;

public class SlidingView extends SlideableView {

	private MenuView mMenuView;
	private ViewGroup mContainer;
	private int mTouchSlop;
	private int menuWidth = BaseFragmentActivity.mMenuWidth;
	private boolean open;

	public static final int STATE_RIGHT = 1;
	public static final int STATE_CENTER = 2;
	private int state = STATE_CENTER;

	public SlidingView(Context context, boolean open) {
		super(context);
		this.open = open;
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(
				R.layout.layout_sliding, null);
		mContainer.setClickable(true);
		mContainer.setBackgroundColor(0xffffffff);
		mScroller = new Scroller(getContext());
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		super.addView(mContainer);
	}

	public void setMenuView(MenuView mMenuView) {
		this.mMenuView = mMenuView;
	}

	private float mLastMotionY;
	private boolean dragable;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (open) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = event.getX();
				mLastMotionY = event.getY();
				if (getScrollX() == 0) {
					dragable = false;
				} else if (getScrollX() == -menuWidth) {
					if (event.getX() >= menuWidth) {
						dragable = true;
					} else {
						dragable = false;
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = event.getX() - mLastMotionX;
				float xDiff = Math.abs(dx);
				float yDiff = Math.abs(event.getY() - mLastMotionY);
				if (getScrollX() == 0) {
					if (xDiff > mTouchSlop && xDiff > yDiff) {
						dragable = true;
						mLastMotionX = event.getX();
					} else {
						dragable = false;
					}

				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return dragable;
		} else {
			return super.onInterceptTouchEvent(event);
		}
	}

	private float offsetX;
	private float mLastMotionX;
	private long downTime;
	private long upTime;
	private float downMotionX;
	private float upMotionX;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!dragable || !open) {
			return super.onTouchEvent(event);
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = event.getX();
			downTime = System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_MOVE:
			offsetX = event.getX() - mLastMotionX;
			float scrollX = getScrollX() - offsetX;
			// ����view�������ұ߽�
			if (scrollX < -menuWidth) {
				scrollX = -menuWidth;
			}
			// ����view��������߽�
			if (scrollX > 0) {
				scrollX = 0;
			}
			scrollTo((int) (scrollX), getScrollY());
			mMenuView
					.scrollTo(
							(int) (scrollX * menuWidth / 2 / menuWidth + menuWidth / 2),
							getScrollY());
			mLastMotionX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			upTime = System.currentTimeMillis();
			// ���ʱ���С��50������x����λ��С��10�� ����Ϊ�ǵ��
			if (upTime - downTime < 200
					&& Math.abs(upMotionX - downMotionX) < 10) {
				toggleView();
				break;
			}
			int currentScrollX = getScrollX();
			if (state == STATE_CENTER) {
				// ���һ�������menu��1/5
				if (currentScrollX <= -menuWidth / 5) {
					// �����Ҳ�
					showMenu();
				} else {
					// �ص��м�
					hideMenu();
				}
			} else {// view���Ҳ࣬view���󻬶�����menu��1/5����view�Զ������м䣬����ص��ұ�
					// ���󻬶�����menu��1/5
				if (currentScrollX >= -menuWidth * 4 / 5) {
					// �����м�
					hideMenu();
				} else {
					// �ص��Ҳ�
					showMenu();
				}
			}
			break;
		}
		return true;
	}

	/**
	 * ���menu��ǰ��Ӱ�أ���ʾmenu;��������menu
	 */
	public void toggleView() {
		switch (state) {
		case STATE_RIGHT:
			hideMenu();
			break;
		case STATE_CENTER:
			showMenu();
			break;
		}

	}

	/**
	 * ��ʾ�˵�
	 */
	private void showMenu() {
		smoothScrollTo(-menuWidth);
		mMenuView.smoothScrollTo(0);
		state = STATE_RIGHT;
	}

	/**
	 * �رղ˵�
	 */
	private void hideMenu() {
		smoothScrollTo(0);
		mMenuView.smoothScrollTo(BaseFragmentActivity.mMenuWidth);
		state = STATE_CENTER;
	}

}
