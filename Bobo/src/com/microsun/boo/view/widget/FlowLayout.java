package com.microsun.boo.view.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
/**
 * 流式布局 特点与应用场景
 * 
 * 自定义ViewGroup：
 * 	1.onMeasure - 测量子View的宽高，设置自己的宽高
 * 	2.onLayout - 设置子View的位置
 * 
 * 测量 = 测量模式+测量值
 * 测量模式介绍：
 * 	EXACTLY - 100dp, fill_parent,match_parent
 *  AT_MOST - wrap_content
 *  UNSPECIFIED -想多大就多大，少见
 * @author wangxuyang1
 *
 */

public class FlowLayout extends ViewGroup {

	protected LayoutParams layoutParams;

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context) {
		this(context, null);
	}

	/**
	 * 测量子View的宽高，设置自己的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		int width = 0;
		int height = 0;

		int lineWidth = 0;
		int lineHeight = 0;

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			// 测量子view的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 子View占据的宽度
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			// 子View占据的高度
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;

			//换行判断
			if (lineWidth + childWidth > sizeWidth-getPaddingLeft()-getPaddingRight()) {

				width = Math.max(width, lineWidth);
				// 重置行宽
				lineWidth = childWidth;
				height += lineHeight;
				// 重置行高
				lineHeight = childHeight;

			} else {
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}

			if (i == childCount - 1) {
				width = Math.max(lineWidth, width);
				height += lineHeight;
			}

		}
		Log.i("wxy", String.format("sizeWith:%s,width:%s,modeWidth=EX:%s",
				sizeWidth, width, modeWidth == MeasureSpec.EXACTLY));
		Log.i("wxy", String.format("sizeHeight:%s,height:%s,modeHeight=EX:%s",
				sizeHeight, height, modeHeight == MeasureSpec.EXACTLY));
		setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth
				: width+getPaddingLeft()+getPaddingRight(), modeHeight == MeasureSpec.EXACTLY ? sizeHeight
				: height+getPaddingBottom()+getPaddingTop());
	}

	private List<List<View>> mAllViews = new ArrayList<List<View>>();
	private List<Integer> mLineHeights = new ArrayList<Integer>();
	/**
	 * 设置子View的位置
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mAllViews.clear();
		mLineHeights.clear();

		int width = getWidth();

		int lineWidth = 0;
		int lineHeight = 0;

		List<View> lineViews = new ArrayList<View>();

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 子View占据的宽度
			int childWidth = child.getMeasuredWidth();
			// 子View占据的高度
			int childHeight = child.getMeasuredHeight();

			if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > width-getPaddingLeft()-getPaddingRight()) {
				mLineHeights.add(lineHeight);
				mAllViews.add(lineViews);
				lineWidth = 0;
				lineHeight = childHeight + lp.bottomMargin + lp.topMargin;
				lineViews = new ArrayList<View>();
			}
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
					+ lp.bottomMargin);
			lineViews.add(child);
		}

		mAllViews.add(lineViews);
		mLineHeights.add(lineHeight);

		int left = getPaddingLeft();
		int top = getPaddingTop();
		int lineNum = mAllViews.size();
		for (int i = 0; i < lineNum; i++) {
			lineViews = mAllViews.get(i);
			lineHeight = mLineHeights.get(i);
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams mlp = (MarginLayoutParams) child
						.getLayoutParams();
				int lc = left + mlp.leftMargin;
				int tc = top + mlp.topMargin;
				int rc = lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();

				child.layout(lc, tc, rc, bc);

				left += child.getMeasuredWidth() + mlp.leftMargin
						+ mlp.rightMargin;
			}
			left = getPaddingLeft();
			top += lineHeight;
		}

	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		if (layoutParams == null) {
			layoutParams = new MarginLayoutParams(getContext(), attrs);
		}
		return layoutParams;
	}
}
