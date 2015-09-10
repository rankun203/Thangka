package com.yamin.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.yamin.tangka.R;

public class MoreActivity extends Activity implements OnClickListener,
		OnErrorListener, OnCompletionListener {

	private VideoView mVideoView;
	private TextView mLayTextView;
	private TextView mCleanTextView;
	private TextView mMountTextView;
	private TextView mCollectionTextView;

	private MediaController mMediaController;
	private int mCurrentPosition;
	private Uri mUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);

		initView();
		initData();
	}

	private void initView() {
		mVideoView = (VideoView) findViewById(R.id.video);
		mLayTextView = (TextView) findViewById(R.id.tv_lay);
		mCleanTextView = (TextView) findViewById(R.id.tv_clean);
		mMountTextView = (TextView) findViewById(R.id.tv_mount);
		mCollectionTextView = (TextView) findViewById(R.id.tv_collection);
	}

	private void initData() {
		mLayTextView.setOnClickListener(this);
		mCleanTextView.setOnClickListener(this);
		mMountTextView.setOnClickListener(this);
		mCollectionTextView.setOnClickListener(this);

		// Video file
		mUri = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.thangkalonger);

		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);

	}

	@Override
	protected void onStart() {
		mVideoView.setVideoURI(mUri);
		mVideoView.start();
		super.onStart();
	}

	@Override
	protected void onResume() {
		if (mCurrentPosition >= 0) {
			mVideoView.seekTo(mCurrentPosition);
			mCurrentPosition = -1;
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		mVideoView.stopPlayback();
		mCurrentPosition = mVideoView.getCurrentPosition();
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, LayActivity.class);
		String title = "";
		if (v == mLayTextView) {
			intent.putExtra("type", LayActivity.LAY);
			title = "唐卡的悬挂";
		} else if (v == mCleanTextView) {
			intent.putExtra("type", LayActivity.CLEAN);
			title = "唐卡的清洁";
		} else if (v == mMountTextView) {
			intent.putExtra("type", LayActivity.MOUNT);
			title = "唐卡的装裱";
		} else if (v == mCollectionTextView) {
			intent.putExtra("type", LayActivity.COLLECTION);
			title = "唐卡的收藏";
		}
		intent.putExtra("title", title);
		startActivity(intent);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}
}
