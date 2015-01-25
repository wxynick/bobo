package com.microsun.boo.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.letv.mobile.android.app.AppUtils;
import com.microsun.boo.AppUtil;
import com.microsun.boo.BaseActivity;
import com.microsun.boo.R;
import com.microsun.boo.service.ILocationService;
import com.microsun.boo.view.widget.FlowLayout;

public class FlowLayoutDemoActivity extends BaseActivity {
	private FlowLayout mFlowLayout;
	private String[] tags = new String[] { "xhtml", "htmlXXXXXXXXXXXXXXXXXXX",
			"android", "html5", "ios", "The big data", "Spring", "Hibernate",
			"JSP", "bobo", "Javascript", "Java", "AOP", "city", "addr" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_flow_layout_main);
		initView();
		initData();
	}

	void initView() {
		mFlowLayout = (FlowLayout) findViewById(R.id.mFlowLayout);
	}

	void initData() {
		String city = null;
		String district = null;
		if (AppUtils.getService(ILocationService.class) != null) {
			city = AppUtils.getService(ILocationService.class).getCity();
			district = AppUtils.getService(ILocationService.class)
					.getDistrict();
		}

		LayoutInflater mInflater = LayoutInflater.from(this);
		for (int i = 0; i < tags.length; i++) {
			TextView tv = (TextView) mInflater.inflate(R.layout.tag_item,
					mFlowLayout, false);
			tv.setText(tags[i]);
			if (tags[i].equalsIgnoreCase("city") && !TextUtils.isEmpty(city)) {
				tv.setText(city);
			}
			if (tags[i].equalsIgnoreCase("addr")
					&& !TextUtils.isEmpty(district)) {
				tv.setText(district);
			}
			// MarginLayoutParams mlp = new
			// MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
			// MarginLayoutParams.WRAP_CONTENT);
			// mFlowLayout.addView(tv,mlp);
			mFlowLayout.addView(tv);
		}

	}
}
