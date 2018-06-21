package com.taiping.esb;


import java.io.Serializable;

public class RouterInfo implements Serializable {
	
	private static final long serialVersionUID = 8311323836824371783L;
	
	private String routerId;
	private String appId;
	private String serviceId;
	private String address;
	private String formatter;
	private String contentType;
	private String encoding;
	private String needTransform;
	private String requestId;
	private String providerId;
	private String accessToken;
	private String sendTime;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getNeedTransform() {
		return needTransform;
	}
	public void setNeedTransform(String needTransform) {
		this.needTransform = needTransform;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setRouterId(String routerId) {
		this.routerId = routerId;
	}
	public String getRouterId() {
		return routerId;
	}
	
}
