package com.microsun.boo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.microsun.boo.R;
import com.microsun.boo.view.widget.SlidingMenu;

public class SlidingMenuDemoActivity extends Activity
{

	private SlidingMenu mLeftMenu ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.demo_sliding_menu_main);
		
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
	}
	
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}

}
