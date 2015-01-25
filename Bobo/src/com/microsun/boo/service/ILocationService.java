package com.microsun.boo.service;

/**
 * 位置服务接口
 * 
 * @author wangxuyang1
 * 
 */
public interface ILocationService {
	String getProvince();

	String getCity();

	String getCityCode();

	String getDistrict();

	String getAddrStr();

	double getLatitude();

	double getAltitude();

}
