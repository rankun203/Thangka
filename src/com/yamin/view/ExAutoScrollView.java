package com.yamin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ExAutoScrollView extends ViewGroup {
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 600;

	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private int duration = DEFAULT_INTERVAL;

	public static final int DEFAULT_INTERVAL = 1500;
	public static final int SCROLL_WHAT = 0;
	private long interval = DEFAULT_INTERVAL;
	private boolean isAutoScroll = false;
	private boolean stopScrollWhenTouch = true;
	private boolean isStopByTouch = false;
	private MyHandler handler;

	public ExAutoScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ExAutoScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		handler = new MyHandler();
	}

	public void startAutoScroll() {
		isAutoScroll = true;
		sendScrollMessage(interval);
	}

	public void startAutoScroll(int delayTimeInMills) {
		isAutoScroll = true;
		sendScrollMessage(delayTimeInMills);
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void stopAutoScroll() {
		isAutoScroll = false;
		handler.removeMessages(SCROLL_WHAT);
	}

	private void sendScrollMessage(long delayTimeInMills) {
		/** remove messages before, keeps one message is running at most **/
		handler.removeMessages(SCROLL_WHAT);
		handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
	}

	private void scrollOnce() {
		if (mCurScreen == getChildCount() - 1) {
			snapToScreen(0);
		} else {
			snapToScreen(mCurScreen + 1);
		}
	}

	@SuppressLint("HandlerLeak") 
	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case SCROLL_WHAT:
				scrollOnce();
				sendScrollMessage(interval + duration);
			default:
				break;
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		System.out.println("onLayout : " + changed + " " + getChildCount());
		int childLeft = 0;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}

		final int height = width * 9 / 16;
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		// Log.e(TAG, "moving to screen "+mCurScreen);
		scrollTo(mCurScreen * width, 0);
		setMeasuredDimension(width, height);
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	public void snapToScreen(int whichScreen) {
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {

			final int delta = whichScreen * getWidth() - getScrollX();
			if (getScrollX() % getWidth() == 0) {
				mScroller.startScroll(getScrollX(), 0, delta, 0, duration);
			} else {
				mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
			}
			
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout
		}
	}

	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (stopScrollWhenTouch) {
			if (event.getAction() == MotionEvent.ACTION_DOWN && isAutoScroll) {
				isStopByTouch = true;
				stopAutoScroll();
			} else if (event.getAction() == MotionEvent.ACTION_UP
					&& isStopByTouch) {
				startAutoScroll();
			}
		}

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// Log.e(TAG, "event down!");
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;

		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;

			scrollBy(deltaX, 0);
			break;

		case MotionEvent.ACTION_UP:
			// Log.e(TAG, "event : up");
			// if (mTouchState == TOUCH_STATE_SCROLLING) {
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			// Log.e(TAG, "velocityX:"+velocityX);

			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move left
				// Log.e(TAG, "snap left");
				snapToScreen(mCurScreen - 1);
			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				// Log.e(TAG, "snap right");
				snapToScreen(mCurScreen + 1);
			} else {
				snapToDestination();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// }
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		// Log.e(TAG, "onInterceptTouchEvent-slop:"+mTouchSlop);

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;

			}
			break;

		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return mTouchState != TOUCH_STATE_REST;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		handler.removeMessages(SCROLL_WHAT);
	}

}
