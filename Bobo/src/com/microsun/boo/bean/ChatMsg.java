package com.microsun.boo.bean;

public class ChatMsg {
	private String msgId;
	private String msgContent;
	/**
	 * 聊天对象的用户昵称
	 */
	private String nickName;
	/**
	 * 消息时间
	 */
	private long msgTime;
	/***
	 * 聊天对象的用户图像
	 */
	private String iconUri;

	private MsgContentType contentType = MsgContentType.TEXT;

	private MsgType msgType = MsgType.Send;

	
	
	
	
	
	public enum MsgContentType {
		TEXT, IMAGE, audio, Video
	}

	public enum MsgType {
		Received, Send
	}

}
