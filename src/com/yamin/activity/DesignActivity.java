package com.yamin.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.yamin.adapter.ProtraitAdapter;
import com.yamin.adapter.ProtraitAdapter.OnProtraitClick;
import com.yamin.bean.TangCard;
import com.yamin.tangka.R;

public class DesignActivity extends Activity implements OnClickListener,
		OnProtraitClick {

	private Button mBackButton;
	private Button mTipButton;
	private Button mShareButton;
	private ImageView mCardImageView;
	private ViewPager mCardViewPager;
	private List<TangCard> mCards;

	private ProtraitAdapter mAdapter;
	private int mProtraitIndex;
	private int mBackgroundIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_design);
		initView();
		initData();

	}

	private void initView() {
		mCardImageView = (ImageView) findViewById(R.id.iv_card);
		mCardImageView.setOnClickListener(this);
		mBackButton = (Button) findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(this);
		mTipButton = (Button) findViewById(R.id.btn_tip);
		mTipButton.setOnClickListener(this);
		mShareButton = (Button) findViewById(R.id.btn_share);
		mShareButton.setOnClickListener(this);
		mCardViewPager = (ViewPager) findViewById(R.id.vp_protrait);
	}

	private void initData() {
		try {
			mCards = TangCard.parseSet(getAssets().open("tangka.xml"),
					TangCard.CARD_TYPE_ALL, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<TangCard> cardList = new ArrayList<TangCard>();

		cardList.add(new TangCard().setName("世尊"));
		cardList.add(new TangCard().setName("金刚手菩萨"));
		cardList.add(new TangCard().setName("东方持国天王"));
		cardList.add(new TangCard().setName("南方增长天王"));
		cardList.add(new TangCard().setName("西方广目天王"));
		cardList.add(new TangCard().setName("北方多闻天王"));

		for (int j = 0, s = cardList.size(); j < s; j++) {
			for (int i = 0, size = mCards.size(); i < size; i++) {
				if (mCards.get(i).getName().equals(cardList.get(j).getName())) {
					mCards.remove(i);
					break;
				}
			}
		}

		show();
		mAdapter = new ProtraitAdapter(this, mCards);
		mAdapter.setmProtraitClick(this);
		mCardViewPager.setAdapter(mAdapter);
	}

	private Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		// int fgWidth = foreground.getWidth();
		// int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		Bitmap newbmp = Bitmap
				.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(foreground, 0, 0, null);// 在 0，0坐标开始画入fg ，可以从任意位置画入
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}

	Bitmap background;
	
	private void show() {
		try {
			Bitmap protrait = BitmapFactory.decodeStream(getAssets().open(
					(mCards.get(mProtraitIndex).getProtrait())));
			background = BitmapFactory.decodeStream(getAssets().open(
					(mCards.get(mBackgroundIndex).getBackground())));
			background = toConformBitmap(background, protrait);
			mCardImageView.setImageBitmap(background);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveMyBitmap(File f, Bitmap mBitmap){
		  try {
		   f.createNewFile();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		  }
		  FileOutputStream fOut = null;
		  try {
		   fOut = new FileOutputStream(f);
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  }
		  mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		  try {
		   fOut.flush();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  try {
		   fOut.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_share:
			File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
			saveMyBitmap(file, background);
			Intent intent = new Intent(Intent.ACTION_SEND);   
	        intent.setType("image/*");   
	        intent.putExtra(Intent.EXTRA_SUBJECT, "唐卡");   
	        intent.putExtra(Intent.EXTRA_TEXT, "我设计的唐卡");
	        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(Intent.createChooser(intent, "分享到")); 
			break;
		case R.id.iv_card:
			mBackgroundIndex = (mBackgroundIndex + 1) % mCards.size();
			show();
			break;
		case R.id.btn_tip:
			new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage(
							"金刚手本尊不能和寂静本尊背景互换\r\n"
									+ "虚空藏菩萨后面有显示宫殿说明是本尊的刹土背景不能用于其他本尊")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create().show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onProtraitClick(int position) {
		// TODO Auto-generated method stub
		mProtraitIndex = position;
		show();
	}

}
