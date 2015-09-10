package com.yamin.adapter;

import java.io.IOException;
import java.util.List;

import com.yamin.bean.TangCard;
import com.yamin.tangka.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ProtraitAdapter extends PagerAdapter {

	private Context mContext;
	private List<TangCard> mCards;
	private OnProtraitClick mProtraitClick;
	private int mCurrentSelect;
	
	public ProtraitAdapter(Context context, List<TangCard> card) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mCards = card;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (int) Math.ceil(mCards.size() / 3.0);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.layout_protrait, null);
		container.addView(v);
		ImageView imageView[] = new ImageView[3];
		imageView[0] = (ImageView) v.findViewById(R.id.one);
		imageView[1] = (ImageView) v.findViewById(R.id.two);
		imageView[2] = (ImageView) v.findViewById(R.id.three);
		int count = mCards.size() - position * 3;
		
		
		if (count > 3) {
			count = 3;
		}
		for (int i = 0; i < count; i++) {
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream(mContext.getAssets().open(mCards.get(position * 3 + i).getProtrait()));
				imageView[i].setImageBitmap(bitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final int index = i;
			imageView[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mCurrentSelect = position * 3 + index;
					mProtraitClick.onProtraitClick(position * 3 + index);
				}
			});
			
//			if (position * 3 + i == mCurrentSelect) {
//				imageView[i].setBackgroundColor(Color.rgb(255, 255, 255));
//			}
			
		}
		
		return v;
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
	
	public void setmProtraitClick(OnProtraitClick mProtraitClick) {
		this.mProtraitClick = mProtraitClick;
	}

	public interface OnProtraitClick {
		public void onProtraitClick(int position);
	}

}
