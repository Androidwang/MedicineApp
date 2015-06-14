package com.medicine.app.db.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
			synchronized (InsertUserDB.class) {
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
		String sql = "insert into " + KnowledgeBean.TABLE_NAME + " (" + KnowledgeBean.ID + ","
				+ KnowledgeBean.DATA_TIME + "," + KnowledgeBean.HEADSTR + ","
				+ KnowledgeBean.CONTENT_M + "," + KnowledgeBean.CONTENT + ") values (?,?,?,?,?)";
		if (db.isOpen()) {
			SQLiteStatement statement = db.compileStatement(sql);
			db.beginTransaction();
			for (KnowledgeBean data : datas) {
				statement.bindString(1, data.getId());
				statement.bindString(2, data.getDataTime());
				statement.bindString(3, data.getHeadStr());
				statement.bindString(4, data.getShortConent());
				statement.bindString(5, data.getContent());
				statement.executeInsert();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
		}

	}

}
