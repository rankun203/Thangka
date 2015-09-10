package com.yamin.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import com.yamin.tangka.R;
import com.yamin.adapter.ImageAdapter;
import com.yamin.adapter.ImageAdapter.OnCardClickListener;
import com.yamin.application.MyApplication;
import com.yamin.bean.TangCard;
import com.yamin.utils.DataCleanManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

public class SplashActivity extends BaseActivity implements OnCardClickListener, OnClickListener {

	public final static int SCROLL_WHAT = 10;
	
	private ImageView mIconImageView;
	private ViewGroup mMainLayout;
//	private RecyclerView mRecyclerView;
	private ViewPager mCardViewPager;
	private Handler mHandler;
	private ImageAdapter mAdapter;
//	private MyAdapter mAdapter;
	private List<TangCard> mCards;
	
	private Button mMoreButton;
	private Button mHomeButton;
	private Button mDesignButton;
	
	private long interval = 5000;
	
	private Handler handler;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData(TangCard.CARD_TYPE_PUSA);
    }

    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	mApplication.initMusic();
    }
    
    private void initData(String type) {
    	try {
			mCards = TangCard.parseSet(getAssets().open("tangka.xml"), type, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	mAdapter = new ImageAdapter(this, mCards);
    	mAdapter.setOnCardClickListener(this);
    	mCardViewPager.setAdapter(mAdapter);
    	handler = new MyHandler();
    }
    
    public void startAutoScroll() {
        sendScrollMessage(interval);
    }
 
    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }
 
    private class MyHandler extends Handler {
 
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
 
            switch (msg.what) {
                case SCROLL_WHAT:
                    scrollOnce();
                    sendScrollMessage(interval);
                    break;
            }
        }
    }
    
    private void scrollOnce() {
    	if (mCards != null) {
    		int index = (mCardViewPager.getCurrentItem() + 1) % mCards.size();
    		mCardViewPager.setCurrentItem(index, true);
    	}
    	
    }
    
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1500;
     
        public FixedSpeedScroller(Context context) {
            super(context);
        }
     
        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }
     
        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
     
        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
     
        public void setmDuration(int time) {
            mDuration = time;
        }
     
        public int getmDuration() {
            return mDuration;
        }
    }
    
    private void initView() {
    	mIconImageView = (ImageView) findViewById(R.id.iv_icon);
    	mMainLayout = (ViewGroup) findViewById(R.id.mainLayout);
    	mCardViewPager = (ViewPager) findViewById(R.id.vp_tang);
    	
    	try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mCardViewPager.getContext(),
                    new AccelerateInterpolator());
            field.set(mCardViewPager, scroller);
            scroller.setmDuration(1000);
        } catch (Exception e) {
        }
    	
//    	mCardViewPager.setScrollDurationFactor(1000);
    	mMoreButton = (Button) findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(this);
    	mHomeButton = (Button) findViewById(R.id.btn_home);
    	mHomeButton.setOnClickListener(this);
    	mDesignButton = (Button) findViewById(R.id.btn_design);
    	mDesignButton.setOnClickListener(this);
    	mHandler = new Handler();
    	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Animation animation = new AlphaAnimation(1.0f, 0.0f);
				animation.setDuration(1000);
				animation.setFillAfter(true);
				mIconImageView.startAnimation(animation);
				
				animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(500);
				animation.setStartOffset(1000);
				animation.setFillAfter(true);
				mMainLayout.startAnimation(animation);
				animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						mMainLayout.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						go();
					}
				});
			}
			
		}, 2000);
    }
    
    private void go() {
    	sendScrollMessage(interval);
    	mApplication.playMusic();
    	
    }

	@Override
	public void onClick(int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, CardActivity.class);
		intent.putExtra("card", mCards.get(position));
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_more:
			mApplication.stopMusic();
			Intent intent = new Intent(this, MoreActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_home:
			intent = new Intent(this, CatalogActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_design:
			intent = new Intent(this, DesignActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			initData(data.getStringExtra("type"));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mApplication.release();
		DataCleanManager.cleanExternalCache(this);
	}
}
