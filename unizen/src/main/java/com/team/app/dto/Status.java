package com.team.app.dto;

import java.io.Serializable;

public class Status  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusDesc;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
