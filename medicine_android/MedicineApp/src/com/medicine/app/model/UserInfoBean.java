package com.medicine.app.model;

public class UserInfoBean {
	/**
	 * ID,ICODE,StartTime,Name,Sex,BornYearMonth,llhistory,llhistorydetails
	 */
	
	private String ID;
	private String ICODE;
	private String StartTime;
	private String Name;
	private String Sex;
	private String BornYearMonth;
	private String llhistory;
	private String llhistorydetails;
	
	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfoBean(String iD, String iCODE, String startTime, String name,
			String sex, String bornYearMonth, String llhistory,
			String llhistorydetails) {
		super();
		ID = iD;
		ICODE = iCODE;
		StartTime = startTime;
		Name = name;
		Sex = sex;
		BornYearMonth = bornYearMonth;
		this.llhistory = llhistory;
		this.llhistorydetails = llhistorydetails;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getICODE() {
		return ICODE;
	}
	public void setICODE(String iCODE) {
		ICODE = iCODE;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getBornYearMonth() {
		return BornYearMonth;
	}
	public void setBornYearMonth(String bornYearMonth) {
		BornYearMonth = bornYearMonth;
	}
	public String getLlhistory() {
		return llhistory;
	}
	public void setLlhistory(String llhistory) {
		this.llhistory = llhistory;
	}
	public String getLlhistorydetails() {
		return llhistorydetails;
	}
	public void setLlhistorydetails(String llhistorydetails) {
		this.llhistorydetails = llhistorydetails;
	}
	
	

}
