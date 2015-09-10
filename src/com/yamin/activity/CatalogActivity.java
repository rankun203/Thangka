package com.yamin.activity;

import com.yamin.tangka.R;
import com.yamin.bean.TangCard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CatalogActivity extends Activity implements OnClickListener {

	private View mTianwangView;
	private View mPusaView;
	private View mGuanyinView;
	private View mFoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog);
		initView();
	}
	
	private void initView() {
		mTianwangView = findViewById(R.id.tianwang);
		mTianwangView.setOnClickListener(this);
		mPusaView = findViewById(R.id.pusa);
		mPusaView.setOnClickListener(this);
		mGuanyinView = findViewById(R.id.guanyin);
		mGuanyinView.setOnClickListener(this);
		mFoView = findViewById(R.id.fo);
		mFoView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(); 
		switch (v.getId()) {
		case R.id.tianwang:
			intent.putExtra("type", TangCard.CARD_TYPE_TIANWANG);
			break;
		case R.id.guanyin:
			intent.putExtra("type", TangCard.CARD_TYPE_GUANYIN);
			break;
		case R.id.pusa:
			intent.putExtra("type", TangCard.CARD_TYPE_PUSA);
			break;
		case R.id.fo:
			intent.putExtra("type", TangCard.CARD_TYPE_FO);
			break;
		}
		setResult(1, intent);
		finish();
	}
	
}
