package com.yamin.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.yamin.tangka.R;
import com.yamin.bean.TangCard;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 相册适配器，图片的异步加载，需要利用ImageLoader（参考ImageLoader的手册），否则会卡顿和产生OOM异常
 * 
 * @author StephenZhou
 *
 */
public class ImageAdapter extends PagerAdapter {

	private List<TangCard> mCards;
	
	private Context mContext;
	
	private AssetManager mAssetManager;
	
	private OnCardClickListener mCardClickListener;
	
	public ImageAdapter(Context context, List<TangCard> cards) {
		// TODO Auto-generated constructor stub
//		options = new DisplayImageOptions.Builder()
//		.cacheInMemory(true)
//		.cacheOnDisk(true)
//		.considerExifParams(true)
//		.build();
		this.mContext = context;
		this.mCards = cards;
		mAssetManager = context.getAssets();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCards.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		View imageLayout = LayoutInflater.from(mContext).inflate(R.layout.image, null);
		view.addView(imageLayout, 0);
		ImageView backgroundView = (ImageView) imageLayout.findViewById(R.id.iv_background);
		backgroundView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCardClickListener == null) {
					return;
				}
				mCardClickListener.onClick(position);
			}
		});
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(mAssetManager.open(mCards.get(position).getBackground()));
			backgroundView.setImageBitmap(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imageLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
	
	public interface OnCardClickListener {
		public void onClick(int position);
	}
	
	public void setOnCardClickListener(OnCardClickListener listener) {
		this.mCardClickListener = listener;
	}
}



