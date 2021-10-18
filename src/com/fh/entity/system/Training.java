package com.fh.entity.system;

import java.util.List;

/**
 * 
* 类名称：数据字典
* 类描述： 
* @author FH QQ 313596790[青苔]
* 作者单位： 
* 联系方式：
* 修改时间：2017年12月16日
* @version 2.0
 */
public class Training {

	private String TRAINING_ID;
	private String NAME;			//名称
	private String HOW_LONG;			
	private String STATUS;			
	private String STARTTIME;	
	private String ENDTIME;	
	private String BZ;				//备注
	private Training train;
	private boolean hasTraining = false;
	
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getHOW_LONG() {
		return HOW_LONG;
	}
	public void setHOW_LONG(String hOW_LONG) {
		HOW_LONG = hOW_LONG;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getSTARTTIME() {
		return STARTTIME;
	}
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}
	public String getENDTIME() {
		return ENDTIME;
	}
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}
	public Training getTrain() {
		return train;
	}
	public void setTrain(Training train) {
		this.train = train;
	}
	public boolean isHasTraining() {
		return hasTraining;
	}
	public void setHasTraining(boolean hasTraining) {
		this.hasTraining = hasTraining;
	}
	public String getTRAINING_ID() {
		return TRAINING_ID;
	}
	public void setTRAINING_ID(String tRAINING_ID) {
		TRAINING_ID = tRAINING_ID;
	}
	
	
	
}
