package com.fh.entity.system;

import com.fh.entity.Page;

/**
 * 
* 说明：HRMS
* @author chima
* 作者单位： 
* 联系方式：
* 修改时间：2021-10-18
* @version 2.0
 */
public class User {
	private String USER_ID;		//用户id
	private String USERNAME;	//用户名
	private String PASSWORD; 	//密码
	private String NAME;		//姓名
	private String RIGHTS;		//权限
	private String ROLE_ID;		//角色id
	private String ROLE_IDS;	//副职角色id
	private String LAST_LOGIN;	//最后登录时间
	private String IP;			//用户登录ip地址
	private String STATUS;		//状态
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String SKIN;		//皮肤
	private String NAME_EN;
	private String BIANMA;
	private String DEPARTMENT_ID;
	private String FUNCTIONS;
	private String TEL;
	private String EMAIL;
	private String SEX;
	private String BIRTHDAY;
	private String NATION;
	private String JOBTYPE;
	private String JOBJOINTIME;
	private String FADDRESS;
	private String POLITICAL;
	private String PJOINTIME;
	private String SFID;
	private String MARITAL;
	private String DJOINTIME;
	private String POST;
	private String POJOINTIME;
	private String EDUCATION;
	private String SCHOOL;
	private String MAJOR;
	private String FTITLE;
	private String CERTIFICATE;
	private String CONTRACTLENGTH;
	private String CSTARTTIME;
	private String CENDTIME;
	private String ADDRESS;
	private String BZ;
	
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public String setPASSWORD(String pASSWORD) {
		return PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_IDS() {
		return ROLE_IDS;
	}
	public void setROLE_IDS(String rOLE_IDS) {
		ROLE_IDS = rOLE_IDS;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getNAME_EN() {
		return NAME_EN;
	}
	public void setNAME_EN(String nAME_EN) {
		NAME_EN = nAME_EN;
	}
	public String getBIANMA() {
		return BIANMA;
	}
	public void setBIANMA(String bIANMA) {
		BIANMA = bIANMA;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getFUNCTIONS() {
		return FUNCTIONS;
	}
	public void setFUNCTIONS(String fUNCTIONS) {
		FUNCTIONS = fUNCTIONS;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}
	public String getNATION() {
		return NATION;
	}
	public void setNATION(String nATION) {
		NATION = nATION;
	}
	public String getJOBTYPE() {
		return JOBTYPE;
	}
	public void setJOBTYPE(String jOBTYPE) {
		JOBTYPE = jOBTYPE;
	}
	public String getJOBJOINTIME() {
		return JOBJOINTIME;
	}
	public void setJOBJOINTIME(String jOBJOINTIME) {
		JOBJOINTIME = jOBJOINTIME;
	}
	public String getFADDRESS() {
		return FADDRESS;
	}
	public void setFADDRESS(String fADDRESS) {
		FADDRESS = fADDRESS;
	}
	public String getPOLITICAL() {
		return POLITICAL;
	}
	public void setPOLITICAL(String pOLITICAL) {
		POLITICAL = pOLITICAL;
	}
	public String getPJOINTIME() {
		return PJOINTIME;
	}
	public void setPJOINTIME(String pJOINTIME) {
		PJOINTIME = pJOINTIME;
	}
	public String getSFID() {
		return SFID;
	}
	public void setSFID(String sFID) {
		SFID = sFID;
	}
	public String getMARITAL() {
		return MARITAL;
	}
	public void setMARITAL(String mARITAL) {
		MARITAL = mARITAL;
	}
	public String getDJOINTIME() {
		return DJOINTIME;
	}
	public void setDJOINTIME(String dJOINTIME) {
		DJOINTIME = dJOINTIME;
	}
	public String getPOST() {
		return POST;
	}
	public void setPOST(String pOST) {
		POST = pOST;
	}
	public String getPOJOINTIME() {
		return POJOINTIME;
	}
	public void setPOJOINTIME(String pOJOINTIME) {
		POJOINTIME = pOJOINTIME;
	}
	public String getEDUCATION() {
		return EDUCATION;
	}
	public void setEDUCATION(String eDUCATION) {
		EDUCATION = eDUCATION;
	}
	public String getSCHOOL() {
		return SCHOOL;
	}
	public void setSCHOOL(String sCHOOL) {
		SCHOOL = sCHOOL;
	}
	public String getMAJOR() {
		return MAJOR;
	}
	public void setMAJOR(String mAJOR) {
		MAJOR = mAJOR;
	}
	public String getFTITLE() {
		return FTITLE;
	}
	public void setFTITLE(String fTITLE) {
		FTITLE = fTITLE;
	}
	public String getCERTIFICATE() {
		return CERTIFICATE;
	}
	public void setCERTIFICATE(String cERTIFICATE) {
		CERTIFICATE = cERTIFICATE;
	}
	public String getCONTRACTLENGTH() {
		return CONTRACTLENGTH;
	}
	public void setCONTRACTLENGTH(String cONTRACTLENGTH) {
		CONTRACTLENGTH = cONTRACTLENGTH;
	}
	public String getCSTARTTIME() {
		return CSTARTTIME;
	}
	public void setCSTARTTIME(String cSTARTTIME) {
		CSTARTTIME = cSTARTTIME;
	}
	public String getCENDTIME() {
		return CENDTIME;
	}
	public void setCENDTIME(String cENDTIME) {
		CENDTIME = cENDTIME;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	
}
