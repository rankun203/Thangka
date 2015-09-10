package com.yamin.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yamin.tangka.R;

public class LayActivity extends Activity {

	public final static int LAY = 1;
	public final static int CLEAN = 2;
	public final static int MOUNT = 3;
	public final static int COLLECTION = 4;

	private Button mBackButton;
	private TextView mTitleTextView;
	private ImageView mSource;
	private com.yamin.view.JustifyTextView mContentTextView;

	private final String lay = "唐卡不能挂在潮湿的地方，更不能弄到水，因为是天然矿石颜料，遇水会溶解。像南方不比北方，空气比较湿润，"
			+ "为避免防潮，张挂时最好使用镜框。如果一定要藏式装裱，应将唐卡定期的晾一下，尽量保持干燥，否则会脱色掉色，并避免在梅雨季节张挂。潮湿季节可利用空调、抽湿机，"
			+ "使唐卡保持在恒温、干燥状态。悬挂的唐卡不能直接面对太阳，不能暴晒，尽量将唐卡放在阴面恒温处，"
			+ "因为唐卡是画在棉布上的，如果暴晒的话使棉布膨胀把画面上的颜色拉开出现裂缝，长期暴晒颜色也会变谈。"
			+ "唐卡也不宜长期烟薰，虽然它是矿石、植物、黄金等画的，但是长期的烟薰也会使唐卡表面稍微发黄。\r\n";
	private final String clean = "一般唐卡表面有一层黄布，平时是拉下来的，所以不需要刻意清洁，"
			+ "如果平时是将唐卡画面直接暴露在室内，清洁时，只需要干净柔软的干毛巾，将唐卡表面的浮尘轻轻地擦拭干净，不可以用湿布、棉球擦拭，也不能让化学清洗剂之类的东西沾染唐卡。\r\n";
	private final String mount = "唐卡的装裱分为两种，一种是藏式装裱，一种是汉式装裱(装框)。藏式装裱类似国画中的卷轴装裱，将唐卡和布料缝合在一起，木条作为两轴，这种的装裱方法在唐卡最初出现就已经使用。这种装裱方法只有藏区才有，费用根据使用的材料来区分。汉式装裱是和装饰画一样的，用卡纸(一般须在两层以上)隔开唐卡和玻璃的空隙，装在木框里面。木框的选配可以根据自己家中装修的风格或个人喜好来定制，也可以根据唐卡画面的风格来定制，建议您选用质量较好的材质，以免木框的变型对唐卡有影响。一般来讲，藏式装裱适合北方，因为北方天气干燥，而南方空气中的湿度比较大，对唐卡的长期保存有些不利，在南方的朋友建议您最好选用框式装裱。框式装裱时要注意，唐卡表面要和玻璃留有一定的间隔，不要完全接触，这样对唐卡的保存会有影响。\r\n";
	private final String collection = "收藏时可以卷轴，但在卷轴收藏时必须把唐卡的绫边熨烫平整，在唐卡的画心上覆盖几层棉料纸，以保持画心与绫边同样的厚度。保持通风干燥，唐卡不能见水潮湿。最好放在木箱(以樟木为好)中，放入防虫剂。保存时先用报纸(报纸油墨能防潮)包好，再加塑料薄膜。本站唐卡画师建议最好不要长期卷起来保存，一年最少“亮相”一两次，特别是秋季时让其在不是直射的太阳光下微微晒1～2分钟，注意避免强烈的阳光。唐卡虽然和其它的美术作品不同，不过它也是可以卷起来的，但是卷的时候要注意从下(佛像的底部)往上(佛像的头部)卷，切记不能颠倒。总之一幅唐卡出现掉色、裂缝、变色，即使补救效果也不好的。\r\n";
	
	private final int[] IMAGE_SOURCE = {R.drawable.lay};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lay);

		initView();
		initData();
	}

	private void initView() {
		mBackButton = (Button) findViewById(R.id.btn_back);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mSource = (ImageView) findViewById(R.id.iv_photo);
		mContentTextView = (com.yamin.view.JustifyTextView) findViewById(R.id.tv_content);
	}

	private void initData() {
		int type = getIntent().getIntExtra("type", 0);
		String title = getIntent().getStringExtra("title");
		mTitleTextView.setText(title);
		String content = "";
		switch (type) {
		case LAY:
			content = lay;
			mSource.setVisibility(View.VISIBLE);
			mSource.setImageResource(IMAGE_SOURCE[0]);
			break;
		case CLEAN:
			content = clean;
			break;
		case MOUNT:
			content = mount;
			break;
		case COLLECTION:
			content = collection;
			break;
		}
		mContentTextView.setText(content);
		
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	
	public String StringFilter(String str) throws PatternSyntaxException {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
