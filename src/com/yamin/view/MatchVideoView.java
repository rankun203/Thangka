package com.yamin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MatchVideoView extends VideoView {

	private int mWidth;
	private int mHeight;

	public MatchVideoView(Context context) {
		super(context);
	}

	public MatchVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MatchVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = getDefaultSize(mWidth, widthMeasureSpec);

		int height = getDefaultSize(mHeight, heightMeasureSpec);

		setMeasuredDimension(width, height);
	}
}
