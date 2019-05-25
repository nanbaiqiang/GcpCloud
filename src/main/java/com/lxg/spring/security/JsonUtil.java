package com.lxg.spring.security;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static String object2json(Object object)
	{
		 JSONObject jsonObj = JSONObject.fromObject(object);  
		 return jsonObj.toString();  
	}
	
	//将一个List转成json数组 
	public static String objectList2jsonArray(List list)
	{
		JSONArray json = JSONArray.fromObject(list); 
	    return json.toString();  			  
	}
	
	//将一个List转成json对象 
	public static String objectList2json(List list)
	{
		JSONObject jsonObj = new JSONObject();  

	    for (int i = 0; i < list.size(); i++) {  
	        jsonObj.put(list.get(i).toString(), list.get(i));  
	    }
	    
	    return jsonObj.toString();
	}
	
	//json to object
	public static Object json2Object(String json)
	{
		JSONObject jsonObject = JSONObject.fromObject(json);  
	    Object  object=(Object)JSONObject.toBean(jsonObject);  
	    return object;  			  
	}
	
	// [{id:'id1',code:'code1',name:'name1'},{id:'id2',code:'code2',name:'name2'}]  
	public static List json2ObjectList(String json)
	{
		  JSONArray array = JSONArray.fromObject(json);  
		  List list=(List)JSONArray.toList(array);  
		  return array;  			  
	}

}
