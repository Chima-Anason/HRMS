package com.fh.entity.system;

/**
 *
 * Class name：Deduction and Allowance Category
 * Class description：
 * Author Unit：
 * Contact：
 * 
 * @version 1.0
 */

public class SalaryCategory {

	private String CAT_ID;		//ID
	private String CAT_NAME;	//名称
	private String EN_NAME;
	private String PARENT_ID;	//上级ID
	
	public String getCAT_ID() {
		return CAT_ID;
	}
	public void setCAT_ID(String cAT_ID) {
		CAT_ID = cAT_ID;
	}
	public String getCAT_NAME() {
		return CAT_NAME;
	}
	public void setCAT_NAME(String cAT_NAME) {
		CAT_NAME = cAT_NAME;
	}
	public String getEN_NAME() {
		return EN_NAME;
	}
	public void setEN_NAME(String eN_NAME) {
		EN_NAME = eN_NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}


}
