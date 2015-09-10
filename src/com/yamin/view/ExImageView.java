package com.yamin.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.yamin.tangka.R;

public class ExImageView extends FrameLayout implements View.OnClickListener {

	private ImageView mSourceImageView;
	private FrameLayout mContainFrameLayout;

	private List<TagView> tagViews = new ArrayList<TagView>();
	private LayoutInflater inflater;

	public ExImageView(Context context) {
		super(context);
		initView();
	}

	public ExImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ExImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.layout_image, this);
		mSourceImageView = (ImageView) findViewById(R.id.iv_photo);
		mContainFrameLayout = (FrameLayout) findViewById(R.id.contain);
		mSourceImageView.setScaleType(ScaleType.CENTER_CROP);
		mContainFrameLayout.setOnClickListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int height = MeasureSpec.getSize(heightMeasureSpec);

		setMeasuredDimension(
				MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

		measureChild(mContainFrameLayout, MeasureSpec.makeMeasureSpec(
				getMeasuredWidth(), MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
						MeasureSpec.EXACTLY));

		for (int i = 0, size = tagViews.size(); i < size; i++) {
			TagView tagView = tagViews.get(i);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.leftMargin = (int) (width * tagView.getTangKaTag().getX() - tagView
					.getMeasuredWidth() / 2);
			params.topMargin = (int) (height * tagView.getTangKaTag().getY() - tagView
					.getMeasuredHeight() / 2);
			tagView.setLayoutParams(params);
		}
	}

	public void setTangkaTags(List<TagView> tagViews) {
		this.tagViews = tagViews;
		showTag();
	}

	int width;
	int height;

	private void showTag() {
		System.out.println("width1 : " + width);
		System.out.println("height1 : " + height);
		for (int i = 0, size = tagViews.size(); i < size; i++) {
			TagView tagView = tagViews.get(i);
			mContainFrameLayout.addView(tagView);
		}
		requestLayout();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contain:
			for (int i = 0, size = tagViews.size(); i < size; i++) {
				tagViews.get(i).showContent(false);
			}
			break;
		}
	}

	public void setImageUrl(InputStream is) {
		if (mSourceImageView != null) {
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			mSourceImageView.setImageBitmap(bitmap);
		}
	}
}
