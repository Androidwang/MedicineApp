package com.medicine.app.db.database;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.medicine.app.model.UserInfoBean;
import com.medicine.app.utils.CommonConst;
/**
 * 数据库的增，删，改，查的处理 insertUser
 * 
 * @author wangyang
 *
 */

public class InsertUserDB implements CommonConst{
	private DBSqlHeper dbHelper;
	private static InsertUserDB mInstance;

	public InsertUserDB(Context context) {
		dbHelper = new DBSqlHeper(context);
	}

	public static InsertUserDB getInstance(Context context) {
		if (mInstance == null) {
			synchronized (InsertUserDB.class) {
				if (mInstance == null) {
					mInstance = new InsertUserDB(context);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param info
	 */
	public void insertUserInfo(UserInfoBean info) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"insert into "+CommonConst.TABLE_NAME +" (ID,ICODE,StartTime,Name,Sex,BornYearMonth,Address,illSrc,HFLbefore,HFLsrc,NowPace,INRTime) values (?,?,?,?,?,?,?,?,?,?,?,?)",
					new String[] { info.getID(), info.getICODE(),
							info.getStartTime(), info.getName(),
							info.getSex(), info.getBornYearMonth(),
							info.getAddress(),info.getIllSrc(),info.getHFLbefore(),info.getIllSrc(),
							info.getHFLsrc(),info.getNowPace(),info.getINRTime()
						});
			db.close();
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param info
	 */
	public void updateUserInfo(String title, String photo,
			String photoThumbnail, String nameTime) {
		SQLiteDatabase dbs = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null);
		String sql = "UPDATE "+CommonConst.TABLE_NAME+" SET title = " + "'" + title + "'"
				+ " , photo = " + "'" + photo + "'" + " , photoThumbnail = "
				+ "'" + photoThumbnail + "'" + " , isUploadSuccess = 1 "
				+ "WHERE name = " + "'" + nameTime + "'";
		if (dbs.isOpen()) {
			dbs.execSQL(sql);
			dbs.close();
		}
	}

	/**
	 * 查询用户信息
	 * 
	 * @param info
	 */
	public List<UserInfoBean> selectUserInfo() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		if (db.isOpen()) {
			Cursor mCursor = db.rawQuery("select * from "+ TABLE_NAME, null);
			if (mCursor != null) {
				while (mCursor.moveToNext()) {
					String id = mCursor.getString(mCursor.getColumnIndex("ID"));
					String idCode = mCursor.getString(mCursor.getColumnIndex("ICODE"));
					String startTime = mCursor.getString(mCursor.getColumnIndex("StartTime"));
					String name = mCursor.getString(mCursor.getColumnIndex("Name"));
					String sex = mCursor.getString(mCursor.getColumnIndex("Sex"));
					String bornYearMonth = mCursor.getString(mCursor.getColumnIndex("BornYearMonth"));
					String address = mCursor.getString(mCursor.getColumnIndex("Address"));
					String illSrc = mCursor.getString(mCursor.getColumnIndex("illSrc"));
					String hFLbefore = mCursor.getString(mCursor.getColumnIndex("HFLbefore"));
					String hFLsrc = mCursor.getString(mCursor.getColumnIndex("HFLsrc"));
					String nowPace = mCursor.getString(mCursor.getColumnIndex("NowPace"));
					String iNRTime = mCursor.getString(mCursor.getColumnIndex("INRTime"));
					list.add(new UserInfoBean(id, idCode, startTime, name, sex, bornYearMonth, address, illSrc, hFLbefore, hFLsrc, nowPace, iNRTime)
		);
				}
				mCursor.close();
			}
			db.close();
		}
		return list;
	}
}
