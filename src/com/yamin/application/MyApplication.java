package com.yamin.application;

import com.yamin.tangka.R;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.text.TextUtils;

public class MyApplication extends Application {

	private MediaPlayer mp;
	private boolean playing;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));  
	}

	private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {  
        String SYSTEM_REASON = "reason";  
        String SYSTEM_HOME_KEY = "homekey";  
        String SYSTEM_HOME_KEY_LONG = "recentapps";  
           
        @Override
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {  
                String reason = intent.getStringExtra(SYSTEM_REASON);  
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
                     //表示按了home键,程序到了后台
                	stopMusic();
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){  
                    //表示长按home键,显示最近使用的程序列表  
                }  
            }   
        }  
    };  
    
    public void initMusic() {
    	if (mp == null) {
    		mp = MediaPlayer.create(this, R.raw.bg_music);
    		mp.setLooping(true);
    		/* 当MediaPlayer.OnErrorListener会运行的Listener */
    		mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
    			@Override
    			/* 覆盖错误处理事件 */
    			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
    				// TODO Auto-generated method stub
    				try {
    					/* 发生错误时也解除资源与MediaPlayer的赋值 */
    					mp.release();
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				return false;
    			}
    		});
    	}
    }
    
	public void playMusic() {
		if (playing) {
			return;
		}
		try {
			if (mp != null) {
				mp.stop();
			}
			mp.prepare();
			mp.start();
			playing = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopMusic() {
		if (mp != null) {
			mp.stop();
			playing = false;
		}
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public void release() {
		if (mp != null) {
			mp.release();
			mp = null;
		}
	}

}
