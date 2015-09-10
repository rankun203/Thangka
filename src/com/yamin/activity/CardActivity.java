package com.yamin.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yamin.bean.TangCard;
import com.yamin.bean.TangkaTag;
import com.yamin.tangka.R;
import com.yamin.utils.StringRotator;
import com.yamin.view.ExImageView;
import com.yamin.view.TagView;

public class CardActivity extends Activity implements OnClickListener{

	private ExImageView mProtraitImageView;
	private TextView mNameTextView;
	private TextView mChineseTextView;
	private TextView mEnglishTextView;

	private Button mBackButton;
	private Button mShareButton;

	private TangCard mCard;
	private StringRotator mRotator;

	private ScrollView mScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		initView();
		initData();
	}

	private void initView() {
		mScrollView = (ScrollView) findViewById(R.id.sv_layout);
//		mScrollView.setMode(Mode.PULL_FROM_END);
//		mScrollView.setOnRefreshListener(this);
		mNameTextView = (TextView) findViewById(R.id.tv_name);
		mBackButton = (Button) findViewById(R.id.btn_home);
		mBackButton.setOnClickListener(this);
		mShareButton = (Button) findViewById(R.id.btn_share);
		mShareButton.setOnClickListener(this);
		mProtraitImageView = (ExImageView) findViewById(R.id.iv_protrait);
		mChineseTextView = (TextView) findViewById(R.id.tv_content_chinese);
		mEnglishTextView = (TextView) findViewById(R.id.tv_content_english);
	}

	private void initData() {
		mRotator = new StringRotator();
		mCard = (TangCard) getIntent().getSerializableExtra("card");
		try {
			mProtraitImageView.setImageUrl(getAssets().open(
					mCard.getBackground()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		List<TagView> tangKaTags = new ArrayList<TagView>();
		String tagName = mCard.getTagName();
		if (tagName.length() != 0) {
			if (tagName.contains(",")) {
				String[] tagNames = mCard.getTagName().split(",");
				for (int i = 0, size = tagNames.length; i < size; i++) {
					if (!TextUtils.isEmpty(tagNames[i])) {
						TangkaTag.ThangkaTags tag = TangkaTag.ThangkaTags
								.valueOf(tagNames[i]);
						TagView tagView = new TagView(this);
						tagView.setTangKaTag(tag);
						tangKaTags.add(tagView);
					}
				}
			} else {
				TangkaTag.ThangkaTags tag = TangkaTag.ThangkaTags
						.valueOf(tagName);
				TagView tagView = new TagView(this);
				tagView.setTangKaTag(tag);
				tangKaTags.add(tagView); 
			}
			mProtraitImageView.setTangkaTags(tangKaTags);
		}
		mNameTextView.setText(convertVerticalText(mCard.getName()));
		mEnglishTextView.setText(mCard.getEnglish());
		String chinese = mCard.getChinese();
		boolean deal = false;
		while (chinese.length() > 200) {
			deal = true;
			chinese = chinese.substring(0, chinese.lastIndexOf('。'));
		}

		if (deal) {
			chinese = chinese + '。';
		}
		System.out.println(chinese.length());
		StringBuilder builder = new StringBuilder(chinese.length() * 2);
		chinese = mRotator.rotate(chinese);
		for (int i = 0, size = chinese.length(); i < size; i++) {
			char c = chinese.charAt(i);
			builder.append(c);
			if (c == '\r') {
				continue;
			}
			builder.append(' ');
		}
		mChineseTextView.setText(builder.toString());

	}

	private String convertVerticalText(String text) {
		StringBuilder showName = new StringBuilder();
		for (int i = 0, size = text.length(); i < size; i++) {
			showName.append(text.charAt(i));
			showName.append('\n');
		}

		return showName.toString();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_share:
			File file = null;
			try {
				InputStream is = getAssets().open(mCard.getProtrait());
				file = new File(getExternalCacheDir(), mCard.getProtrait()
						.replaceAll(File.separator, "_"));
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1444];
				int byteread;
				while ((byteread = is.read(buffer)) != -1) {
					fos.write(buffer, 0, byteread);
				}
				is.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");
			intent.putExtra(Intent.EXTRA_SUBJECT, "唐卡");
			intent.putExtra(Intent.EXTRA_TEXT, mCard.getName());
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, "分享到"));
			break;
		case R.id.btn_home:
			finish();
			break;
		default:
			break;
		}
	}
}
