package com.fh.entity.system;

import java.sql.Date;

@SuppressWarnings("unused")
public class Bank {
	
	private String BANK_ID;	
	private String ACC_NAME;	
	private String ACC_NO;	
	private String BANK_NAME;
	private String USER_ID;
	private Date CREATED_TIME;             //创建时间
	private Date EDITED_TIME;
	public String getBANK_ID() {
		return BANK_ID;
	}
	public void setBANK_ID(String bANK_ID) {
		BANK_ID = bANK_ID;
	}
	public String getACC_NAME() {
		return ACC_NAME;
	}
	public void setACC_NAME(String aCC_NAME) {
		ACC_NAME = aCC_NAME;
	}
	public String getACC_NO() {
		return ACC_NO;
	}
	public void setACC_NO(String aCC_NO) {
		ACC_NO = aCC_NO;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public Date getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(Date cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	public Date getEDITED_TIME() {
		return EDITED_TIME;
	}
	public void setEDITED_TIME(Date eDITED_TIME) {
		EDITED_TIME = eDITED_TIME;
	}

	
	
}
