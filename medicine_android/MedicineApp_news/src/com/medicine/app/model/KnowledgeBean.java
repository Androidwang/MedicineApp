package com.medicine.app.model;

public class KnowledgeBean {
	/**
	 * 实体表名
	 */
	public static final String TABLE_NAME = "medication_knowledge";
	/**
	 * ID 知识编号
	 */
	public static final String ID = "IDStr";
	/**
	 * DataTime 编辑时间
	 */
	public static final String DATA_TIME = "DataTime";
	/**
	 * 标题
	 */
	public static final String HEADSTR = "Headstr";
	/**
	 * 缩略简介
	 */
	public static final String CONTENT_M = "ContentM";
	/**
	 * 知识内容
	 */
	public static final String CONTENT = "Content";

	public int id;
	public String IDStr;
	public String dataTime;
	public String headStr;
	public String shortConent;
	public String content;

	/**
	 * @param id 编号
	 * @param dataTime 编辑时间
	 * @param headStr 标题
	 * @param shortContent 缩略简介
	 * @param content 知识内容
	 */
	public KnowledgeBean(int id, String IDStr, String dataTime, String headStr, String shortContent,
			String content) {
		this.id = id;
		this.IDStr = IDStr; 
		this.dataTime = dataTime;
		this.headStr = headStr;
		this.shortConent = shortContent;
		this.content = content;
	}
	/**
	 * @param dataTime 编辑时间
	 * @param headStr 标题
	 * @param shortContent 缩略简介
	 * @param content 知识内容
	 */
	public KnowledgeBean(String IDStr, String dataTime, String headStr, String shortContent,
			String content) {
		this.IDStr = IDStr;
		this.dataTime = dataTime;
		this.headStr = headStr;
		this.shortConent = shortContent;
		this.content = content;
	}
	public String getIDStr() {
		return IDStr;
	}
	public void setIDStr(String iDStr) {
		IDStr = iDStr;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getHeadStr() {
		return headStr;
	}

	public void setHeadStr(String headStr) {
		this.headStr = headStr;
	}

	public String getShortConent() {
		return shortConent;
	}

	public void setShortConent(String shortConent) {
		this.shortConent = shortConent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
