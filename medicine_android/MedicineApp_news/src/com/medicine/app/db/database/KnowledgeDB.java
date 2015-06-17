package com.medicine.app.db.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.medicine.app.model.HistoryBean;
import com.medicine.app.model.KnowledgeBean;
import com.medicine.app.utils.CommonConst;

public class KnowledgeDB implements CommonConst {
	private DBSqlHeper dbHelper;
	private static KnowledgeDB mInstance;

	public KnowledgeDB(Context context) {
		dbHelper = new DBSqlHeper(context);
	}

	public static KnowledgeDB getInstance(Context context) {
		if (mInstance == null) {
			synchronized (KnowledgeDB.class) {
				if (mInstance == null) {
					mInstance = new KnowledgeDB(context);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 批量插入知识数据
	 * 
	 * @param datas
	 */
	public void insert(List<KnowledgeBean> datas) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "insert into " + KnowledgeBean.TABLE_NAME + " (id," + KnowledgeBean.ID + ","
				+ KnowledgeBean.DATA_TIME + "," + KnowledgeBean.HEADSTR + ","
				+ KnowledgeBean.CONTENT_M + "," + KnowledgeBean.CONTENT + ") values (?,?,?,?,?,?)";
		if (db.isOpen()) {
			SQLiteStatement statement = db.compileStatement(sql);
			db.beginTransaction();
			for (KnowledgeBean data : datas) {
				statement.bindString(2, data.getIDStr());
				statement.bindString(3, data.getDataTime());
				statement.bindString(4, data.getHeadStr());
				statement.bindString(5, data.getShortConent());
				statement.bindString(6, data.getContent());
				statement.executeInsert();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
		}

	}
	
	/**
	 * 获取最新一条数据的ID
	 * @return
	 */
	public String getLastDataId() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "+KnowledgeBean.TABLE_NAME+" order by id desc", null);
		String id = null;
		if(cursor.moveToFirst()) {
			id = cursor.getString(cursor.getColumnIndex(KnowledgeBean.ID));
		}
		return id;
	}
	
	/**
	 * 分页查询
	 * @param page 页数
	 * @param limit 每页显示几条
	 * @return
	 */
	public List<KnowledgeBean> getListByLimit(int page, int limit) {
		String limitStr = (page*limit)+","+limit;
		Log.d("getListByLimit", limitStr+"");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(	KnowledgeBean.TABLE_NAME, 
									null, null, null, null, null, 
									"id desc", 
									limitStr);
		List<KnowledgeBean> list = new ArrayList<KnowledgeBean>();
		if(cursor.moveToFirst()) {
			do {
				list.add(new KnowledgeBean(cursor.getInt(0),
											cursor.getString(1),
											cursor.getString(2), 
											cursor.getString(3), 
											cursor.getString(4), 
											cursor.getString(5))
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
			Cursor cursor = db.rawQuery("select count(*) from "+KnowledgeBean.TABLE_NAME, null);
			if(cursor.moveToFirst()) {
				count = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		}
		return count;
	}
}
