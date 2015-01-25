package com.microsun.boo.view;

import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.InstrumentedActivity;

public class JPushBaseActivity extends InstrumentedActivity {
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}


	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
