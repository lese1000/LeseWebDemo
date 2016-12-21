package tech.lese.webdemo.utils;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//import net.sf.jsonJSONObject;

/**
 * 
 * @author zenglin
 * @since 2009-06-23
 * @see Dto
 * @see java.io.Serializable
 */
public class JsonDto extends LinkedHashMap<String, Object> implements Serializable {
	public final static String KEY_CODE = "code";
	public final static String KEY_MSG = "msg";
	public final static String KEY_METHOD = "method";
	public final static String KEY_OBJECT = "obj";
	public final static String KEY_LIST = "list";
	public final static String KEY_CRYPTO="crypto";
	public final static String KEY_PAGE="page";
	
	public final static String CODE_SUCCESS = "0000";
	public final static String CODE_FAILE = "0001";
	public final static String CODE_VALIDATE_FAIL = "1111";//接口预留
	public final static String CODE_ERROR = "9999";//错误
	public final static String CODE_CRYPTO="8888";
	
	public final static String MSG_SUCCESS = "成功";//
	public final static String MSG_FAILE = "失败";//
	public final static String MSG_VALIDATE_FAILE = "验证失败";//
	public final static String MSG_ERROR = "参数异常或服务端异常";//
	
	public JsonDto(){
		this.setDefaultFaile();
		//put(KEY_CRYPTO, CODE_CRYPTO);
	}
	
	public JsonDto(String key, Object value){
		this.setDefaultFaile();
		put(key, value);
		//put(KEY_CRYPTO, CODE_CRYPTO);
	}
	
	public JsonDto(String success,String key, Object value){
		this(success);
		put(key,value);
		//put(KEY_CRYPTO, CODE_CRYPTO);
		
	}
	
	public JsonDto(String success){
		if(this.CODE_SUCCESS.equals(success)){
			this.setDefaultSuccess();
		}else{
			this.setDefaultFaile();
		}
		//put(KEY_CRYPTO, CODE_CRYPTO);
	}
	
	public void setCode(String code ){
		this.put(KEY_CODE,code);
	}
	
	private void setDefaultFaile(){
		this.put(KEY_CODE, CODE_FAILE);
		this.put(KEY_MSG, MSG_FAILE);
	}
	private void setDefaultSuccess(){
		this.put(KEY_CODE, CODE_SUCCESS);
		this.put(KEY_MSG, MSG_SUCCESS);
	}
	
	public JsonDto setObj(Object obj){
		this.put(KEY_OBJECT, obj);
		return this;
	}
	
	public JsonDto setList(Object obj){
		this.put(KEY_LIST, obj);
		return this;
	}
	
	public void setSuccess(){
		setSuccess( MSG_SUCCESS);
	}
	public JsonDto setSuccess(String msg){
		this.put(KEY_CODE, CODE_SUCCESS);
		this.put(KEY_MSG, msg);
		return this;
	}
	
	public void setFaile(){
		this.setFaile(MSG_FAILE);
	}
	
	public JsonDto setFaile(String faileMsg){
		this.put(KEY_CODE, CODE_FAILE);
		this.put(KEY_MSG, faileMsg);
		return this;
	}
	
	public void setValidateFaile(){
		this.put(KEY_CODE, CODE_VALIDATE_FAIL);
		this.put(KEY_MSG, MSG_VALIDATE_FAILE);
	}
	public void setError(){
		this.put(KEY_CODE, CODE_ERROR);
		this.put(KEY_MSG, MSG_ERROR);
	}
	
	public void setCryptoDataIsTrue(){
		this.put(KEY_CRYPTO, CODE_CRYPTO);
	}
	
/*	public String toJson(){
		JSONObject jobj = JSONObject.fromObject(this);
		return jobj.toString();
	}*/

	public void setPage(PageDto page){
		this.put(KEY_PAGE, page);
	}
	
	public static class PageDto{
		private int pageNumber;//第几页
		private int pageSize;//每页多少条
		private Long totalElements;//总记录数
		private int totalPages;//总页数
		
		
		public int getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public Long getTotalElements() {
			return totalElements;
		}
		public void setTotalElements(Long totalElements) {
			this.totalElements = totalElements;
		}
		public int getTotalPages() {
			return totalPages;
		}
		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}
		
	}
}
