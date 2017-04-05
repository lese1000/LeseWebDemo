package tech.lese.webdemo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

public class JsonUtil {
	/**
	 * json字符串转成map
	 * @param str
	 * @return
	 */
	public static Map<String, Object> strToMap(String str){
		Map<String,Object> map=new HashMap<String, Object>();
		 String key = "";
		if(str!=null && !"".equals(str)){
			JSONObject jsonObj = JSONObject.fromObject(str);
			try {
	            Iterator<String> keyIter = jsonObj.keys();
	            while (keyIter.hasNext()) {
	            	key = (String) keyIter.next();
	            	map.put(key, jsonObj.get(key));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
        return map;
	}
	
	public static List<Map<String, Object>> strToMapList(String str){
		List<Map<String, Object>> rlist=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		 String key = "";
		if(str!=null && !"".equals(str)){
			 JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(str);
		        if(jsonArray!=null){
		            List list=(List)JSONSerializer.toJava(jsonArray);
		            for(Object o:list){
		                JSONObject jsonObject=JSONObject.fromObject(o);
		                try {
		    	            Iterator<String> keyIter = jsonObject.keys();
		    	            while (keyIter.hasNext()) {
		    	            	key = (String) keyIter.next();
		    	            	map.put(key, jsonObject.get(key));
		    	            }
		    	            rlist.add(map);
		    	            map=new HashMap<String, Object>();
		    	        } catch (Exception e) {
		    	            e.printStackTrace();
		    	        }
		            }
		        }
		}
        return rlist;
	}
	/**
	 * json字符串转成实体类
	 * @param <T>
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> T strToEntity(String str,T clazz ){
		JSONUtils.getMorpherRegistry().registerMorpher(
				                 new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
		 JSONObject jsonObject=JSONObject.fromObject(str);
         T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
         return obj;
	}
	
	/**
     * 封装将json对象转换为java集合对象
     * 
     * @param <T>
     * @param clazz
     * @param jsons 
     * @return
     */
	public static <T> List<T> strToEntityList(T clazz, String jsons) {
		JSONUtils.getMorpherRegistry().registerMorpher(
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
        List<T> objs=null;
        JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(jsons);
        if(jsonArray!=null){
            objs=new ArrayList<T>();
            List list=(List)JSONSerializer.toJava(jsonArray);
            for(Object o:list){
                JSONObject jsonObject=JSONObject.fromObject(o);
                T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }
	
	public static JSONArray toJsonArr(Object obj){
		return JSONArray.fromObject(obj);
	}
	
	public static JSONObject toJsonObj(Object obj){
		return JSONObject.fromObject(obj);
	}
	/**
	 * 为map中value为null改为""
	 * @param map
	 * @return
	 */
	public  static Map<String, Object> mapTrim(Map<String, Object> map,String def){
		if(!map.isEmpty()){
			try {
	            Set<String> keyIter = map.keySet();
	            for(String key:keyIter){
	            	map.put(key, ToString(map.get(key),def));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
        return map;
	}
	
	public  static List<Map<String, Object>> mapListTrim(List<Map<String, Object>> mapList,String def){
		List<Map<String, Object>> rlist=new ArrayList<Map<String,Object>>();
		if(mapList!=null &&!mapList.isEmpty()){
			for(Map<String, Object> map :mapList){
				rlist.add(mapTrim(map,def));
			}
		}
        return rlist;
	}
	private static  String ToString(Object obj,String def) {
		if (obj == null)
			return def;
		return obj.toString().trim();
	}
	
}
