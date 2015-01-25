package com.microsun.boo.view.adapter;


import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 首个List对应子元素中所代表的组，第二个List对应孙子元素在子元素组中的位置. Map亦将支持这样的特殊元素。（子元素嵌套组元素的情况）
 * 将XML文件中定义的静态数据映射到组及其视图的简单的适配器. 你可以用 Map的列表，为组指定其后台数据。每个数组元素对应一个可展开列表的一个组。
 * Maps 包含每行的数据。你还可以指定 XML 文件来定义用于显示组的视图， 并通过 Map 的键值映射到指定的视图。该过程适用于组的子元素。
 * 单级以外的可展开列表的后台数据类型为List<List<Map>>，
 * 第一级列表对应可扩展视图组中的组视图，第二级列表对应组的子组视图， 最后 Map 保持子组视图的子视图的数据。
 * 
 * @author BenZeph （以下代码和注释均参考了工具屋，网址：http://code.google.com/p/toolib/）
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

	private List<? extends Map<String, ?>> mGroupData;
	private int mExpandedGroupLayout;
	private int mCollapsedGroupLayout;
	private String[] mGroupFrom;
	private int[] mGroupTo;

	private List<? extends List<? extends Map<String, ?>>> mChildData;
	private int mChildLayout;
	private int mLastChildLayout;
	private String[] mChildFrom;
	private int[] mChildTo;

	private LayoutInflater mInflater;

	/**
	 * 调用另外一个构造函数，其中From和To的含义和不同的ListView相同，
	 * 可以参考ListView或者SimpleExpandableListViewAdapter
	 * 
	 * @param context
	 * @param groupData
	 * @param groupLayout
	 * @param groupFrom
	 * @param groupTo
	 * @param childData
	 * @param childLayout
	 * @param childFrom
	 * @param childTo
	 */
	public MyExpandableListViewAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		this(context, groupData, groupLayout, groupLayout, groupFrom, groupTo,
				childData, childLayout, childLayout, childFrom, childTo);
	}

	/**
	 * 
	 * @param context
	 * @param groupData
	 * @param expandedGroupLayout
	 * @param collapsedGroupLayout
	 * @param groupFrom
	 * @param groupTo
	 * @param childData
	 * @param childLayout
	 * @param lastChildLayout
	 * @param childFrom
	 * @param childTo
	 */
	public MyExpandableListViewAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, int lastChildLayout, String[] childFrom,
			int[] childTo) {
		mGroupData = groupData;
		mExpandedGroupLayout = expandedGroupLayout;
		mCollapsedGroupLayout = collapsedGroupLayout;
		mGroupFrom = groupFrom;
		mGroupTo = groupTo;
		mChildData = childData;
		mChildLayout = childLayout;
		mLastChildLayout = lastChildLayout;
		mChildFrom = childFrom;
		mChildTo = childTo;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// 取得与指定分组、指定子项目关联的数据。
		return mChildData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// 取得给定分组中给定子视图的ID。 该组ID必须在组中是唯一的。组合的ID （参见getCombinedGroupId(long)）
		// 必须不同于其他所有ID（分组及子项目的ID）。
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// 取得显示给定分组给定子位置的数据用的视图。
		View v;
		if (convertView == null) {
			v = newChildView(isLastChild, parent);
		} else {
			v = convertView;
		}
		bindChildView(v, mChildData.get(groupPosition).get(childPosition),
				mChildFrom, mChildTo);
		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// 取得指定分组的子元素数。
		return mChildData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// 取得与给定分组关联的数据。
		return mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// 取得分组数
		return mChildData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// 取得指定分组的ID。该组ID必须在组中是唯一的。组合的ID （参见getCombinedGroupId(long)）
		// 必须不同于其他所有ID（分组及子项目的ID）。
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// 取得用于显示给定分组的视图。 这个方法仅返回分组的视图对象， 要想获取子元素的视图对象，
		// 就需要调用 getChildView(int, int, boolean, View, ViewGroup)。
		View v;
		if (convertView == null) {
			v = newGroupView(isExpanded, parent);
		} else {
			v = convertView;
		}
		bindGroupView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
		return v;
	}

	@Override
	public boolean hasStableIds() {
		// 是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID。
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// 指定位置的子视图是否可选择。
		return true;
	}

	/**
	 * 创建新的组视图
	 * 
	 * @param isExpanded
	 * @param parent
	 * @return
	 */
	public View newGroupView(boolean isExpanded, ViewGroup parent) {
		return mInflater.inflate((isExpanded) ? mExpandedGroupLayout
				: mCollapsedGroupLayout, parent, false);
	}

	/**
	 * 创建新的子视图
	 * 
	 * @param isLastChild
	 * @param parent
	 * @return
	 */
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		return mInflater.inflate((isLastChild) ? mLastChildLayout
				: mChildLayout, parent, false);
	}

	/**
	 * 绑定组数据
	 * 
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	private void bindGroupView(View view, Map<String, ?> data, String[] from,
			int[] to) {
		// 绑定组视图的数据，针对Group的Layout都是TextView的情况
		int len = to.length;
		for (int i = 0; i < len; i++) {
			TextView v = (TextView) view.findViewById(to[i]);
			if (v != null) {
				v.setText((String) data.get(from[i]));
			}
		}
	}

	/**
	 * 绑定子数据
	 * 
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	private void bindChildView(View view, Map<String, ?> data, String[] from,
			int[] to) {
		TextView v = (TextView) view.findViewById(to[0]);
		if (v != null) {
			v.setText((String) data.get(from[0]));
		}
		CheckBox c = (CheckBox) view.findViewById(to[1]);
		if (c != null) {
			if (data.get(from[1]).equals(0)) {
				c.setChecked(true);
			} else {
				c.setChecked(false);
			}
		}
	}
}
