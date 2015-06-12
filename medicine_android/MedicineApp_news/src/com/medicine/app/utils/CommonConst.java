package com.medicine.app.utils;

import android.os.Environment;

/**
 *  共同资源常量定义处理
 * @author wangyang
 *
 */

public interface CommonConst {
	public final static int DB_VERSION = 1;// 版本号
	public final static String TABLE_NAME = "himi";// 表名
	public static final String DATABASE_NAME = "Himi.db";
	
	
	
	
	public static final String PACKAGE_NAME = "com.medicine.app";
	public static final String DB_PATH = "/data"+ Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;

	
	
}
