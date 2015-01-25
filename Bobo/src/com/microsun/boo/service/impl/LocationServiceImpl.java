/**
 * 
 */
package com.microsun.boo.service.impl;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.letv.mobile.android.app.AppUtils;
import com.letv.mobile.core.microkernel.api.AbstractModule;
import com.microsun.boo.IBoboAppContext;
import com.microsun.boo.service.ILocationService;

/**
 * @author wangxuyang1
 * 
 */
public class LocationServiceImpl extends AbstractModule<IBoboAppContext>
		implements ILocationService {
	private LocationClient mLocationClient;
	private BDLocation mLocation;
	private BDLocationListener mLocationListener;

	@Override
	protected void initServiceDependency() {
		// TODO Auto-generated method stub

	}

	private void initLBS() {
		mLocationClient = new LocationClient(context.getApplication()
				.getAndroidApplication()); // 声明LocationClient类
		mLocationListener = new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null)
					return;
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
				}

				Log.d("lbs", sb.toString());
				mLocation = location;
			}
		};
		mLocationClient.registerLocationListener(mLocationListener); // 注册监听函数
		if (AppUtils.getFramework().isInDebugMode()) {
			mLocationClient.setDebug(true);
		}
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// bd09ll返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(20000);// 设置发起定位请求的间隔时间为60000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.letv.mobile.core.microkernel.api.AbstractModule#startService()
	 */
	@Override
	protected void startService() {
		AppUtils.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				initLBS();				
			}
		});
		context.registerService(ILocationService.class, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.letv.mobile.core.microkernel.api.AbstractModule#stopService()
	 */
	@Override
	protected void stopService() {
		if (mLocationClient != null) {
			if (mLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mLocationListener);
				mLocationListener = null;
			}
			mLocationClient.stop();
			mLocationClient = null;
			mLocation = null;
		}
		context.unregisterService(ILocationService.class, this);
	}

	@Override
	public String getCity() {
		if (mLocation != null) {
			return mLocation.getCity();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.microsun.boo.service.ILocationService#getDistract()
	 */
	@Override
	public String getDistrict() {
		if (mLocation != null) {
			return mLocation.getDistrict();
		}
		return null;
	}

	@Override
	public String getCityCode() {
		if (mLocation != null) {
			return mLocation.getCityCode();
		}
		return null;
	}

	@Override
	public String getAddrStr() {
		if (mLocation != null) {
			return mLocation.getAddrStr();
		}
		return null;
	}

	@Override
	public double getLatitude() {
		if (mLocation != null) {
			return mLocation.getLatitude();
		}
		return 0;
	}

	@Override
	public double getAltitude() {
		if (mLocation != null) {
			return mLocation.getAltitude();
		}
		return 0;
	}

	@Override
	public String getProvince() {
		if (mLocation != null) {
			return mLocation.getProvince();
		}
		return null;
	}

}
