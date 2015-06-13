package com.medicine.app.utils;

import java.io.File;

import com.medicine.app.db.database.SDBHelper;

/**
 *  共同资源常量定义处理
 * @author wangyang
 *
 */

public interface CommonConst {
	public final static int DB_VERSION = 1;// 版本号
	public final static String TABLE_NAME = "medication_userinfo";// 表名
	 public static final String mDbName =  SDBHelper.DB_DIR + File.separator + "medication_data_sql.db";  //数据库
	

	/**
	 * soapAction
	 */
	public static final String SOAP_USER_INFO = "http://Medication_WebService.org/Inter_TB_KNO";   //个人信息
	public static final String SOAP_HFL_HISTORY_IDINDEX = "http://Medication_WebService.org/Get_HFL_history_IDindex"; //获取上一次上传计算数据的序号
	public static final String SOAP_TB_KNO_INFO = "http://Medication_WebService.org/Get_TB_KNO_Info"; // 获取知识数据信息根据ID
	public static final String SOAP_TB_KNO_LIST = "http://Medication_WebService.org/Get_TB_KNO_List"; // 获取知识数据列表信息
	public static final String SOAP_HFL_HISTORY = "http://Medication_WebService.org/Inter_HFL_history"; // 上传计算数据
	public static final String SOAP_KNO_SELECT_COUNT = "http://Medication_WebService.org/KNO_SELECT_COUNT"; // 查询知识数据记录最大条数编号
}
