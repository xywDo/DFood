package com.xyw.dfood.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public abstract class SlideableView extends FrameLayout{
	protected Scroller mScroller;
	public SlideableView(Context context) {
		super(context);
		mScroller = new Scroller(context);
	}

	public void smoothScrollTo(int des){
		int duration = 500;
		int oldScrollX = getScrollX();
		int dx = des - oldScrollX;
		mScroller.startScroll(oldScrollX, getScrollY(), dx, getScrollY(), duration);
		invalidate();
	}
	@Override
	public void computeScroll() {
		if (!mScroller.isFinished()) {
			if (mScroller.computeScrollOffset()) {
				int oldX = getScrollX();
				int oldY = getScrollY();
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();
				if (oldX != x || oldY != y) {
					scrollTo(x, y);
				}
				invalidate();
			} else {
				clearChildrenCache();
			} 
		} else {
			clearChildrenCache();
		} 
	}
	void clearChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
	}
}
