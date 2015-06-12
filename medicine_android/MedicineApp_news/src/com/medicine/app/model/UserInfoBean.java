package com.medicine.app.model;

public class UserInfoBean {
	/**
	 * ID,ICODE,StartTime,Name,Sex,BornYearMonth,Address,illSrc,HFLbefore,HFLsrc,NowPace,INRTime
	 */
	
	private String ID;
	private String ICODE;
	private String StartTime;
	private String Name;
	private String Sex;
	private String BornYearMonth;
	private String Address;
	private String illSrc;
	private String HFLbefore;
	private String HFLsrc;
	private String NowPace;
	private String INRTime;
	
	
	
	public UserInfoBean(String iD, String iCODE, String startTime, String name,
			String sex, String bornYearMonth, String address, String illSrc,
			String hFLbefore, String hFLsrc, String nowPace, String iNRTime) {
		super();
		ID = iD;
		ICODE = iCODE;
		StartTime = startTime;
		Name = name;
		Sex = sex;
		BornYearMonth = bornYearMonth;
		Address = address;
		this.illSrc = illSrc;
		HFLbefore = hFLbefore;
		HFLsrc = hFLsrc;
		NowPace = nowPace;
		INRTime = iNRTime;
	}
	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getIllSrc() {
		return illSrc;
	}
	public void setIllSrc(String illSrc) {
		this.illSrc = illSrc;
	}
	public String getHFLbefore() {
		return HFLbefore;
	}
	public void setHFLbefore(String hFLbefore) {
		HFLbefore = hFLbefore;
	}
	public String getHFLsrc() {
		return HFLsrc;
	}
	public void setHFLsrc(String hFLsrc) {
		HFLsrc = hFLsrc;
	}
	public String getNowPace() {
		return NowPace;
	}
	public void setNowPace(String nowPace) {
		NowPace = nowPace;
	}
	public String getINRTime() {
		return INRTime;
	}
	public void setINRTime(String iNRTime) {
		INRTime = iNRTime;
	}
	
	

	
	
}
