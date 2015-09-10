package com.yamin.activity;

import com.yamin.application.MyApplication;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected MyApplication mApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication = (MyApplication) getApplication();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		mApplication.playMusic();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
