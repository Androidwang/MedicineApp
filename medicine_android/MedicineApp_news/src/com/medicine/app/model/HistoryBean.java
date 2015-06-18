package com.medicine.app.model;
import android.content.ContentValues;
public class HistoryBean {
	/**
	 * 实体表名
	 */
	public static final String TABLE_NAME = "medication_history";
	/**
	 * 序号
	 */
	public static final String ID = "IDindex";
	/**
	 * 时间
	 */
	public static final String TIME = "Dtime";
	/**
	 * 目标INR下线
	 */
	public static final String INR_LOW = "OINRLow";
	/**
	 * 目标INR上线
	 */
	public static final String INR_UP = "OINRUP";
	/**
	 * 本次检测的INR
	 */
	public static final String NOW_INR = "NINR";
	/**
	 * 上次华法林用量
	 */
	public static final String LAST_HFL = "LASTHFL";
	/**
	 * 是否出血
	 */
	public static final String BLOOD = "Blood";
	/**
	 * 记录
	 */
	public static final String RECORD = "Record";
	
	private int id;
	private String time;
	private String low;
	private String up;
	private String now;
	private String last;
	private String blood;
	private String record;
	private ContentValues contentValues;
	/**
	 * @param id 序号
	 * @param time 时间
	 * @param low INR下线
	 * @param up INR上线
	 * @param now 本次检测的INR
	 * @param last 上次华法林用量
	 * @param blood 是否出血
	 * @param record 记录
	 */
	public HistoryBean(int id, String time, String low, String up, String now, String last, String blood, String record) {
		this.id = id;
		this.time = time;
		this.low = low;
		this.up = up;
		this.now = now;
		this.last = last;
		this.blood = blood;
		this.record = record;
	}
	/**
	 * @param time 时间
	 * @param low INR下线
	 * @param up INR上线
	 * @param now 本次检测的INR
	 * @param last 上次华法林用量
	 * @param blood 是否出血
	 * @param record 记录
	 */
	public HistoryBean(String time, String low, String up, String now, String last, String blood, String record) {
		this.time = time;
		this.low = low;
		this.up = up;
		this.now = now;
		this.last = last;
		this.blood = blood;
		this.record = record;
	}
	public ContentValues getContentValues() {
		contentValues = new ContentValues();
		contentValues.put(TIME, time);
		contentValues.put(INR_LOW, low);
		contentValues.put(INR_UP, up);
		contentValues.put(NOW_INR, now);
		contentValues.put(LAST_HFL, last);
		contentValues.put(BLOOD, blood);
		contentValues.put(RECORD, record);
		return contentValues;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}
	
	
}
