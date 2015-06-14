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
	 * methodName
	 */
	public static final String METHOD_USER_INFO = "Inter_User";
	public static final String METHOD_HFL_HISTORY_IDINDEX = "Get_HFL_history_IDindex"; //获取上一次上传计算数据的序号
	public static final String METHOD_TB_KNO_INFO = "Get_TB_KNO_Info"; // 获取知识数据信息根据ID
	public static final String METHOD_TB_KNO_LIST = "Get_TB_KNO_List"; // 获取知识数据列表信息
	public static final String METHOD_HFL_HISTORY = "Inter_HFL_history"; // 上传计算数据
	public static final String METHOD_KNO_SELECT_COUNT = "KNO_SELECT_COUNT"; // 查询知识数据记录最大条数编号
	/**
	 * webservice
	 */
	public static final String NAME_SPACE = "http://Medication_WebService.org/";   
	public static final String END_POINT_SALE = "http://www.huafalin.com:8000/MedC_WebS.asmx";  
	
	/**
	 * soapAction
	 */
	public static final String SOAP_USER_INFO = NAME_SPACE + METHOD_USER_INFO;   //个人信息
	public static final String SOAP_HFL_HISTORY_IDINDEX = NAME_SPACE + METHOD_HFL_HISTORY_IDINDEX; //获取上一次上传计算数据的序号
	public static final String SOAP_TB_KNO_INFO = NAME_SPACE + METHOD_TB_KNO_INFO; // 获取知识数据信息根据ID
	public static final String SOAP_TB_KNO_LIST = NAME_SPACE + METHOD_TB_KNO_LIST; // 获取知识数据列表信息
	public static final String SOAP_HFL_HISTORY = NAME_SPACE + METHOD_HFL_HISTORY; // 上传计算数据
	public static final String SOAP_KNO_SELECT_COUNT = NAME_SPACE + METHOD_KNO_SELECT_COUNT; // 查询知识数据记录最大条数编号
}
