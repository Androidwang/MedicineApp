package com.medicine.app.db.database;

import com.medicine.app.model.HistoryBean;
import com.medicine.app.model.KnowledgeBean;
import com.medicine.app.utils.CommonConst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库的创建，以及表的创建
 * @author wangyang
 *
 */
public class DBSqlHeper extends SQLiteOpenHelper implements CommonConst{
	private static final String CREATE_USERINFO_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+ "(ID TEXT, ICODE TEXT, StartTime TEXT, Name TEXT,PhoneNum TEXT,Sex TEXT, BornYearMonth TEXT, Address TEXT,illSrc TEXT,HFLbefore TEXT,HFLsrc TEXT,NowPace TEXT,INRTime TEXT)";
	private static final String CREATE_KNOWLEDGE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + KnowledgeBean.TABLE_NAME + "("+KnowledgeBean.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KnowledgeBean.DATA_TIME+" TEXT, "+KnowledgeBean.HEADSTR+" TEXT, "+KnowledgeBean.CONTENT_M+" TEXT, "+KnowledgeBean.CONTENT+" TEXT)";
	private static final String CREATE_HISTORY_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " +HistoryBean.TABLE_NAME+ "("+HistoryBean.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+HistoryBean.TIME+" TEXT, "+HistoryBean.INR_LOW+" TEXT, "+HistoryBean.INR_UP+" TEXT, "+HistoryBean.NOW_INR+" TEXT, "+HistoryBean.LAST_HFL+" TEXT, "+HistoryBean.BLOOD+" TEXT, "+HistoryBean.RECORD+" TEXT)";
	public DBSqlHeper(Context context) {
		super(context, mDbName, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USERINFO_TABLE_SQL);
		db.execSQL(CREATE_KNOWLEDGE_TABLE_SQL);
		db.execSQL(CREATE_HISTORY_TABLE_SQL);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

}
