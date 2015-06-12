package com.medicine.app.model;

public class HistoryItemBean {
	
	private String historyTagNum;
	private String historyTime;
	private String historyContent;
	
	public HistoryItemBean(String historyTagNum, String historyTime,
			String historyContent) {
		super();
		this.historyTagNum = historyTagNum;
		this.historyTime = historyTime;
		this.historyContent = historyContent;
	}
	public String getHistoryTagNum() {
		return historyTagNum;
	}
	public void setHistoryTagNum(String historyTagNum) {
		this.historyTagNum = historyTagNum;
	}
	public String getHistoryTime() {
		return historyTime;
	}
	public void setHistoryTime(String historyTime) {
		this.historyTime = historyTime;
	}
	public String getHistoryContent() {
		return historyContent;
	}
	public void setHistoryContent(String historyContent) {
		this.historyContent = historyContent;
	}
	
	

}
