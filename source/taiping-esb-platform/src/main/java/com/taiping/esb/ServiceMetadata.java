package com.taiping.esb;

import java.io.Serializable;

public class ServiceMetadata implements Serializable {

	private static final long serialVersionUID = -27804680221400939L;
	
	private String serviceId;
	private String name;
	private String description;
	private String contentType;
	private String outerPotocol;
	private String serviceType;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getOuterPotocol() {
		return outerPotocol;
	}
	public void setOuterPotocol(String outerPotocol) {
		this.outerPotocol = outerPotocol;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceType() {
		return serviceType;
	}
	
}
