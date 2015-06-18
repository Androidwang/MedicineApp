package com.medicine.app.model;

public class HistoryItemBean {
	
	private String historyTagNum;
	private String historyTime;
	private String historyContent;
	private String historyShortContent;
	public HistoryItemBean(String historyTagNum, String historyTime, String historyShortContent,
			String historyContent) {
		super();
		this.historyTagNum = historyTagNum;
		this.historyTime = historyTime;
		this.historyContent = historyContent;
		this.historyShortContent = historyShortContent;
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
	public String getHistoryShortContent() {
		return historyShortContent;
	}
	public void setHistoryShortContent(String historyShortContent) {
		this.historyShortContent = historyShortContent;
	}
	
	

}
