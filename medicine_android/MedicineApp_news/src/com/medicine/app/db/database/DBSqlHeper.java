package com.medicine.app.db.database;

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
	
	public DBSqlHeper(Context context) {
		super(context, mDbName, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ "(ID TEXT, ICODE TEXT, StartTime TEXT, Name TEXT, Sex TEXT, BornYearMonth TEXT, llhistory TEXT,llhistorydetails TEXT)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

}
