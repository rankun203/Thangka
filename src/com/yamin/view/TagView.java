package com.yamin.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yamin.bean.TangkaTag;
import com.yamin.tangka.R;

public class TagView extends LinearLayout implements View.OnClickListener {

	private ImageView mPoint;
	private TextView mContent;

	private TangkaTag.ThangkaTags tangKaTag;

	public TangkaTag.ThangkaTags getTangKaTag() {
		return tangKaTag;
	}

	public void setTangKaTag(TangkaTag.ThangkaTags tangKaTag) {
		this.tangKaTag = tangKaTag;
		updateInfo();
	}

	public TagView(Context context) {
		super(context);
		initView();
	}

	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public TagView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.item_tag, this);
		mPoint = (ImageView) findViewById(R.id.iv_point);
		mContent = (TextView) findViewById(R.id.tv_content);
		mContent.setOnClickListener(this);
		mPoint.setOnClickListener(this);
		findViewById(R.id.ll_tag).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == mPoint) {
			showContent(mContent.getVisibility() == View.VISIBLE ? false : true);
		} else if (v == mContent) {
			
		}
	}

	public void showContent(boolean isShow) {
		if (isShow)
			mContent.setVisibility(View.VISIBLE);
		else
			mContent.setVisibility(View.INVISIBLE);
	}

	private void updateInfo() {
		if (mContent != null) {
			if (tangKaTag != null && !TextUtils.isEmpty(tangKaTag.getDesc())) {
				mContent.setText(tangKaTag.getDesc());
			}
		}
	}
}
