/**
 * 
 */
package com.microsun.boo.view;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.microsun.boo.BaseActivity;
import com.microsun.boo.MainActivity;
import com.microsun.boo.R;

/**
 * @author wangxuyang1
 * 
 */
public class NotificationDemoActivty extends BaseActivity {
	private AtomicInteger seqNo = new AtomicInteger(1);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_notify_main);
	}

	private void createNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);

		// Create Intent to launch this Activity again if the notification is
		// clicked.
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(this, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(intent);

		// Sets the ticker text
		builder.setTicker(getResources()
				.getString(R.string.custom_notification));

		// Sets the small icon for the ticker
		builder.setSmallIcon(R.drawable.icon);
		builder.setDefaults(Notification.DEFAULT_SOUND);
		// Cancel the notification when clicked
		builder.setAutoCancel(true);

		// Build the notification
		Notification notification = builder.build();

		// Inflate the notification layout as RemoteViews
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.notification);

		// Set text on a TextView in the RemoteViews programmatically.
		final String time = DateFormat.getTimeInstance().format(new Date())
				.toString();
		final String text = getResources().getString(R.string.collapsed, time);
		contentView.setTextViewText(R.id.textView, text);

		/*
		 * Workaround: Need to set the content view here directly on the
		 * notification. NotificationCompatBuilder contains a bug that prevents
		 * this from working on platform versions HoneyComb. See
		 * https://code.google.com/p/android/issues/detail?id=30495
		 */
		notification.contentView = contentView;

		// Add a big content view to the notification if supported.
		// Support for expanded notifications was added in API level 16.
		// (The normal contentView is shown when the notification is collapsed,
		// when expanded the
		// big content view set here is displayed.)
		if (Build.VERSION.SDK_INT >= 16) {
			// Inflate and set the layout for the expanded notification view
			RemoteViews expandedView = new RemoteViews(getPackageName(),
					R.layout.notification_expanded);
			notification.bigContentView = expandedView;
		}

		// START_INCLUDE(notify)
		// Use the NotificationManager to show the notification
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(seqNo.getAndIncrement(), notification);
	}

	public void showNotificationClicked(View v) {
		createNotification();
	}
}