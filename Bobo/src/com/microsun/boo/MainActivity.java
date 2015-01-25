package com.microsun.boo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.microsun.boo.circularfloatingactionmenu.samples.DemoActivity;
import com.microsun.boo.view.ChatDemoAcitivty;
import com.microsun.boo.view.ExpandableListViewActivity;
import com.microsun.boo.view.FlowLayoutDemoActivity;
import com.microsun.boo.view.JPushDemoActivity;
import com.microsun.boo.view.NotificationDemoActivty;
import com.microsun.boo.view.SlidingMenuDemoActivity;
import com.microsun.boo.view.widget.CustomProgressDialog;

public class MainActivity extends BaseActivity {
	private final static int CLOSE_PROGRESS = 1;
	private ListView mMenu;
	private ListAdapter mAdapter;
	private CustomProgressDialog progressDialog;
	private String[] tags = new String[] { "流式布局demo", "通知", "侧滑菜单", "定制进度条",
			"聊天界面布局", "极光推送demo","百度定位", "底部菜单", "下拉刷新", "WebView与JS交互", "浮球菜单" ,"expand list"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initData();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CLOSE_PROGRESS:
				if (progressDialog.isShowing()) {
					progressDialog.hide();
				}
				break;
			default:
				break;
			}
		};
	};

	void initView() {
		mMenu = (ListView) findViewById(R.id.mMenu);
		progressDialog = CustomProgressDialog.createDialog(this);
		progressDialog.setTitile("Loading");
		progressDialog.setMessage("加载中...");
		mHandler.sendEmptyMessageDelayed(CLOSE_PROGRESS, 2000);
	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos,
				long arg3) {
			switch (pos) {
			case 0:
				Intent intent = new Intent(MainActivity.this,
						FlowLayoutDemoActivity.class);
				startActivity(intent);
				break;
			case 1:
				startActivity(new Intent(MainActivity.this,
						NotificationDemoActivty.class));
				break;
			case 2:
				startActivity(new Intent(MainActivity.this,
						SlidingMenuDemoActivity.class));
				break;
			case 3:
				progressDialog.show();
				mHandler.sendEmptyMessageDelayed(CLOSE_PROGRESS, 5000);
				break;
			case 4:
				startActivity(new Intent(MainActivity.this,
						ChatDemoAcitivty.class));
				break;
			case 5:
				startActivity(new Intent(MainActivity.this,
						JPushDemoActivity.class));
				break;
			case 10:
				startActivity(new Intent(MainActivity.this,
						DemoActivity.class));
				break;
			case 11:
				startActivity(new Intent(MainActivity.this,
						ExpandableListViewActivity.class));
				break;
			}
		}
	};

	void initData() {
		mAdapter = new ArrayAdapter<String>(this, R.layout.main_item, tags);
		mMenu.setAdapter(mAdapter);
		mMenu.setOnItemClickListener(listener);
	}

}
