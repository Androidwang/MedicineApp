package com.medicine.app.webservice;
import java.io.IOException;
import java.util.HashMap;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault12;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
public class WebService {
	public static SoapObject common(String soapAction, String methodName, HashMap<String, Object> map, String nameSpace, String endPoint) {   
       Log.d("WebService", "methodName:"+methodName);
       Log.d("WebService", "map:"+map.toString());
		// 指定WebService的命名空间和调用的方法名   
       SoapObject rpc = new SoapObject(nameSpace, methodName);   
       // 设置需调用WebService接口需要传入的参数   
       if (null != map && map.size() > 0) {   
           Object[] key = map.keySet().toArray();   
           for (int i = 0; i < key.length; i++) {   
               rpc.addProperty(key[i].toString(), map.get(key[i]));   
           }   
       }   
       // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本   
       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);   
       envelope.bodyOut = rpc;   
       // 设置是否调用的是dotNet开发的WebService   
       envelope.dotNet = true;   
       // 设置连接超时时间为20秒   
     HttpTransportSE transport = new HttpTransportSE(endPoint, 20000);   
       try {   
           // 调用WebService   
           transport.call(soapAction, envelope);   
       } catch (IOException e) {   
           e.printStackTrace();   
       } catch (XmlPullParserException e) {   
           e.printStackTrace();   
       }
       if(envelope.bodyIn instanceof SoapFault12) {
    	   String str = ((SoapFault12) envelope.bodyIn).toString();
    	   Log.d("webservice-fault", str);
    	   return null;
       } else {
    	// 获取返回的数据   
           SoapObject soapObject = (SoapObject) envelope.bodyIn;   
           return soapObject;  
       }
        
   }   
}  

