package com.fh.entity.system;

import java.util.List;


/**
 * 
* 说明：HRMS
* @author chima
* 作者单位： 
* 联系方式：
* 修改时间：2021-10-18
* @version 2.0
 */
public class AssignTraining {

	private String ASS_ID;						
	private String STATUZ;			
	private String TRAINING_ID;	
	private String USER_ID;
	private String CONTENT;
	private String TO_USERNAME;
	private String FROM_USERNAME;
	private String SEND_TIME;
	
	
	public String getASS_ID() {
		return ASS_ID;
	}
	public void setASS_ID(String aSS_ID) {
		ASS_ID = aSS_ID;
	}
	
	public String getTRAINING_ID() {
		return TRAINING_ID;
	}
	public void setTRAINING_ID(String tRAINING_ID) {
		TRAINING_ID = tRAINING_ID;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getSTATUZ() {
		return STATUZ;
	}
	public void setSTATUZ(String sTATUZ) {
		STATUZ = sTATUZ;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getTO_USERNAME() {
		return TO_USERNAME;
	}
	public void setTO_USERNAME(String tO_USERNAME) {
		TO_USERNAME = tO_USERNAME;
	}
	public String getFROM_USERNAME() {
		return FROM_USERNAME;
	}
	public void setFROM_USERNAME(String fROM_USERNAME) {
		FROM_USERNAME = fROM_USERNAME;
	}
	public String getSEND_TIME() {
		return SEND_TIME;
	}
	public void setSEND_TIME(String sEND_TIME) {
		SEND_TIME = sEND_TIME;
	}	
	
	
	
	
}
