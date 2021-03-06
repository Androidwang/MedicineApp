package com.medicine.app.db.database;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
					"insert into "+CommonConst.TABLE_NAME +" (ID,ICODE,StartTime,Name,PhoneNum,Sex,BornYearMonth,Address,illSrc,HFLbefore,HFLsrc,NowPace,INRTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new String[] { info.getID(), info.getICODE(),
							info.getStartTime(), info.getName(),info.getPhoneNum(),
							info.getSex(), info.getBornYearMonth(),
							info.getAddress(),info.getIllSrc(),info.getHFLbefore(),
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
					String phoneNum = mCursor.getString(mCursor.getColumnIndex("PhoneNum"));
					System.out.println("phoneNum--"+phoneNum);
					
					String sex = mCursor.getString(mCursor.getColumnIndex("Sex"));
					
					String bornYearMonth = mCursor.getString(mCursor.getColumnIndex("BornYearMonth"));
					String address = mCursor.getString(mCursor.getColumnIndex("Address"));
					String illSrc = mCursor.getString(mCursor.getColumnIndex("illSrc"));
					String hFLbefore = mCursor.getString(mCursor.getColumnIndex("HFLbefore"));
					String hFLsrc = mCursor.getString(mCursor.getColumnIndex("HFLsrc"));
					String nowPace = mCursor.getString(mCursor.getColumnIndex("NowPace"));
					String iNRTime = mCursor.getString(mCursor.getColumnIndex("INRTime"));
					list.add(new UserInfoBean(id, idCode, startTime, name,phoneNum, sex, bornYearMonth, address, illSrc, hFLbefore, hFLsrc, nowPace, iNRTime)
		);
				}
				mCursor.close();
			}
			db.close();
		}
		return list;
	}

	/**
	 * 获取某个column的值
	 * @param columnName
	 * @return
	 */
	public String getColumnValue(String columnName) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String result = null;
		if (db.isOpen()) {
			Cursor mCursor = db.rawQuery("select * from "+ TABLE_NAME, null);
			if (mCursor != null) {
				while (mCursor.moveToNext()) {
					result = mCursor.getString(mCursor.getColumnIndex(columnName));
					break;
				}
				mCursor.close();
			}
			db.close();
		}
		
		return result;
	}
}
