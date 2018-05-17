package com.eyun.user.web.rest.vm;

import java.io.Serializable;

public class AuthenticationVM implements Serializable {

	private String realName;

    private String idnuber;

    private String frontImg;

    private String reverseImg;

    private Integer status;

    private String statusString;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdnuber() {
		return idnuber;
	}

	public void setIdnuber(String idnuber) {
		this.idnuber = idnuber;
	}

	public String getFrontImg() {
		return frontImg;
	}

	public void setFrontImg(String frontImg) {
		this.frontImg = frontImg;
	}

	public String getReverseImg() {
		return reverseImg;
	}

	public void setReverseImg(String reverseImg) {
		this.reverseImg = reverseImg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	
}
