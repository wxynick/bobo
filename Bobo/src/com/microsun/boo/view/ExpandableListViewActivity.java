package com.microsun.boo.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.microsun.boo.R;
import com.microsun.boo.view.adapter.MyExpandableListViewAdapter;
import com.umeng.analytics.MobclickAgent;

public class ExpandableListViewActivity extends ExpandableListActivity {
	private int total=340;
	private int pageSize = 10;
	private List<Map<String, String>> pageData ;
	
	List<List<Map<String, Object>>> childData;
	
	private void initData(){
		Random random = new Random(1000);
		total = random.nextInt(1000);
		Log.d("wxy", "==================total:"+total);
		if (pageData==null) {
			pageData = new ArrayList<Map<String, String>>();
		}
		if (childData==null) {
			childData = new ArrayList<List<Map<String,Object>>>();
		}
		pageData.clear();
		childData.clear();
		int pageNum =  total % pageSize ==0 ? total/pageSize:total/pageSize+1;
		int num = 0;
		for (int i =0 ;i<pageNum;i++) {
			Map<String, String> gData = new HashMap<String, String>();
			List<Map<String, Object>> cData = new ArrayList<Map<String, Object>>();
			gData.put("groupTextView", String.format("%s~%s", i*pageSize+1,Math.min(((i+1)*pageSize), total)));
			pageData.add(gData);
			for (int j = 1; j <=pageSize&&num<total; j++) {
				num =i*pageSize+j;
				Map<String, Object> cdData = new HashMap<String, Object>();
				cdData.put("childTextView", String.format("第%s集", num));
				cdData.put("childCheckBox", num%5!=0?1:0);
				cData.add(cdData);
			}
			childData.add(cData);
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_expandedlist_main);
		initData();
		
		/*// 创建两个一级条目标题
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		Map<String, String> groupData1 = new HashMap<String, String>();
		groupData1.put("groupTextView", "新闻");// group对应layout中的id：group
		Map<String, String> groupData2 = new HashMap<String, String>();
		groupData2.put("groupTextView", "科技");
		groupData.add(groupData1);
		groupData.add(groupData2);

		// 创建第一个一级条目下的的二级条目
		List<Map<String, Object>> child1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> childData1 = new HashMap<String, Object>();
		childData1.put("childTextView", "网易头条新闻");// 同理
		childData1.put("childCheckBox", 0);// 同理
		Map<String, Object> childData2 = new HashMap<String, Object>();
		childData2.put("childTextView", "凤凰网新闻");
		childData2.put("childCheckBox", 1);
		child1.add(childData1);
		child1.add(childData2);

		// 创建第二个一级条目下的的二级条目
		List<Map<String, Object>> child2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> childData3 = new HashMap<String, Object>();
		childData3.put("childTextView", "TechWeb");
		childData3.put("childCheckBox", 0);
		Map<String, Object> childData4 = new HashMap<String, Object>();
		childData4.put("childTextView", "月光博客");
		childData4.put("childCheckBox", 1);
		child2.add(childData3);
		child2.add(childData4);

		// 将二级条目放在一个集合里，供显示时使用
		List<List<Map<String, Object>>> childData = new ArrayList<List<Map<String, Object>>>();
		childData.add(child1);
		childData.add(child2);*/
		MyExpandableListViewAdapter adapter = new MyExpandableListViewAdapter(
				getApplicationContext(), pageData, R.layout.demo_expandedlist_groups,
				new String[] { "groupTextView"},
				new int[] { R.id.groupTextView}, childData, R.layout.demo_expandedlist_childs,
				new String[] { "childTextView", "childCheckBox" }, new int[]{
						R.id.childTextView, R.id.childCheckBox });
		setListAdapter(adapter);
	}
	
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

	/**
	 * 设置哪个二级目录被默认选中
	 */
	@Override
	public boolean setSelectedChild(int groupPosition, int childPosition,
			boolean shouldExpandGroup) {
		// do something
		return super.setSelectedChild(groupPosition, childPosition,
				shouldExpandGroup);
	}

	/**
	 * 设置哪个一级目录被默认选中
	 */
	@Override
	public void setSelectedGroup(int groupPosition) {
		// do something
		super.setSelectedGroup(groupPosition);
	}

	/**
	 * 当二级条目被点击时响应
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// do something
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}

}