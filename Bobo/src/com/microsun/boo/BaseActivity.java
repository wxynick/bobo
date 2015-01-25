/**
 * 
 */
package com.microsun.boo;

import cn.jpush.android.api.JPushInterface;

import com.letv.mobile.android.app.AppUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author wangxuyang1
 * 
 */
public class BaseActivity extends Activity {
	private static final String A_TAG = "AC_LifeCycle";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onCreate", this.getClass()
					.getCanonicalName()));
		}

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onStart", this.getClass()
					.getCanonicalName()));
		}
		super.onStart();
	}

	@Override
	protected void onRestart() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onRestart", this.getClass()
					.getCanonicalName()));
		}
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onRestoreInstanceState", this
					.getClass().getCanonicalName()));
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onSaveInstanceState", this.getClass()
					.getCanonicalName()));
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onResume", this.getClass()
					.getCanonicalName()));

		}
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onPause", this.getClass()
					.getCanonicalName()));
		}
		super.onPause();
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}

	@Override
	protected void onPostResume() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onPostResume", this.getClass()
					.getCanonicalName()));
		}

		super.onPostResume();
	}

	@Override
	protected void onStop() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onStop", this.getClass()
					.getCanonicalName()));
		}

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (AppUtils.getFramework().isInDebugMode()) {
			Log.i(A_TAG, String.format("%s-->onDestroy", this.getClass()
					.getCanonicalName()));
		}

		super.onDestroy();
	}

}
