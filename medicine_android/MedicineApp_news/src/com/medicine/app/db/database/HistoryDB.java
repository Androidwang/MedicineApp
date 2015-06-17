package com.medicine.app.db.database;

import java.util.ArrayList;
import java.util.List;

import com.medicine.app.model.HistoryBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HistoryDB {
	private DBSqlHeper dbHelper;
	private static HistoryDB mInstance;
	
	public HistoryDB(Context context) {
		dbHelper = new DBSqlHeper(context);
	}
	
	public static HistoryDB getInstance(Context context) {
		if(mInstance == null) {
			synchronized (HistoryDB.class) {
				if(mInstance == null) {
					mInstance = new HistoryDB(context);
				}
			}
		}
		return mInstance;
	}
	
	public boolean insert(HistoryBean data) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long row = db.insert(HistoryBean.TABLE_NAME, null, data.getContentValues());
		if(row == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<HistoryBean> selectAfterById(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<HistoryBean> list = new ArrayList<HistoryBean>();
		if(db.isOpen()) {
			Cursor mCursor = db.rawQuery("select * from " + HistoryBean.TABLE_NAME + " where "+HistoryBean.ID+">"+id, null);
			if(mCursor!=null) {
				while (mCursor.moveToNext()) {
					int mId = mCursor.getInt(mCursor.getColumnIndex(HistoryBean.ID));
					String time = mCursor.getString(mCursor.getColumnIndex(HistoryBean.TIME));
					String low = mCursor.getString(mCursor.getColumnIndex(HistoryBean.INR_LOW));
					String up = mCursor.getString(mCursor.getColumnIndex(HistoryBean.INR_UP));
					String now = mCursor.getString(mCursor.getColumnIndex(HistoryBean.NOW_INR));
					String last = mCursor.getString(mCursor.getColumnIndex(HistoryBean.LAST_HFL));
					String blood = mCursor.getString(mCursor.getColumnIndex(HistoryBean.BLOOD));
					String record = mCursor.getString(mCursor.getColumnIndex(HistoryBean.RECORD));
					list.add(new HistoryBean(mId, time, low, up, now, last, blood, record));
				}
			}
			mCursor.close();
			db.close();
		}
		return list;
	}
	
	/**
	 * 获取最新一条数据的ID
	 * @return
	 */
	public String getLastDataId() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select last_insert_rowid() from "+HistoryBean.TABLE_NAME, null);
		int id = 0;
		if(cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		return id+"";
	}
	
	/**
	 * 分页查询
	 * @param page 页数
	 * @param limit 每页显示几条
	 * @return
	 */
	public List<HistoryBean> getListByLimit(int page, int limit) {
		String limitStr = (page*limit)+","+limit;
		Log.d("getListByLimit", limitStr+"");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(	HistoryBean.TABLE_NAME, 
									null, null, null, null, null, 
									HistoryBean.ID+" desc", 
									limitStr);
		List<HistoryBean> list = new ArrayList<HistoryBean>();
		if(cursor.moveToFirst()) {
			do {
				list.add(new HistoryBean(	cursor.getInt(0), 
										 	cursor.getString(1), 
										 	cursor.getString(2), 
										 	cursor.getString(3), 
										 	cursor.getString(4), 
										 	cursor.getString(5), 
										 	cursor.getString(6), 
										 	cursor.getString(7)
										 )
						);
			} while (cursor.moveToNext());
		}
		return list;
	}
	/**
	 * 获取表数据总条数
	 * @return
	 */
	public int getCount() {
		int count = 0;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if(db.isOpen()) {
			Cursor cursor = db.rawQuery("select count(*) from "+HistoryBean.TABLE_NAME, null);
			if(cursor.moveToFirst()) {
				count = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		}
		return count;
	}
}
