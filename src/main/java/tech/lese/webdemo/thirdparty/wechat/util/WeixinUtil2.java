package tech.lese.webdemo.thirdparty.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import tech.lese.webdemo.pojo.TemplagePojo;
import tech.lese.webdemo.utils.AppConfigurator;
import tech.lese.webdemo.utils.JsonUtil;
import tech.lese.webdemo.utils.MD5;


public class WeixinUtil2 {
	public static String send_templatemsg_url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	/**
	 * 根据code获取微信openId
	 * 通过code换取网页授权access_token
	 * 
	 * @param code
	 * @return
	 */
	public static Map<String,String> getWxOpenIDandAccess_token(String code,String wxId,String wxSecret) {
		Map<String,String> result_map=new HashMap<String, String>();
		String result_str = ""; // /auth2token?appid=%s&secret=%s&code=%s&grant_type=authorization_code
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet method = new HttpGet(
				AppConfigurator.getProperty("DOMAIN")+"/api/auth2token?appid="
						+ wxId + "&secret=" + wxSecret + "&code=" + code
						+ "&grant_type=authorization_code");
		try {
			// 设置编码
			CloseableHttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				result_str = "-" + statusCode;
			} else {
				result_str = EntityUtils.toString(response.getEntity());
				JSONObject json = JSONObject.fromObject(result_str);
				if (json.get("errcode")!=null && !"".equals(json.get("errcode"))) {
					result_map.put("errcode", json.getString("errcode")) ;
					result_map.put("errmsg", json.getString("errmsg")) ;
				}else{
					if (json.get("access_token") != null) {
						result_map.put("access_token", json.getString("access_token")) ;
					} 
					if (json.get("expires_in") != null) {
						result_map.put("expires_in", json.getString("expires_in")) ;
					} 
					if (json.get("refresh_token") != null) {
						result_map.put("refresh_token", json.getString("refresh_token")) ;
					} 
					if (json.get("openid") != null) {
						result_map.put("openid", json.getString("openid")) ;
					} 
					if (json.get("scope") != null) {
						result_map.put("scope", json.getString("scope")) ;
					}
				}
			}
			response.close();
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			try {
				client.close();
			} catch (Exception e) {

			}
		}
		return result_map;
	}
	
	/**
	 * 检查绑定
	 * @param openid
	 * @return
	 */
	public static Map<String,Object> checkBind(String openid) {
		 // /checkbind?openid=XXXXX&time=1460701064076&sign=XXXXXXX
		long time = System.currentTimeMillis()/1000;
		String url=AppConfigurator.getProperty("DOMAIN")+"/api/checkbind?openid="+ openid + "&time=" + time + "&sign="+getsign(openid,time);
		return httpMethod(url);
	}
	
	/**
	 * 	获取getJStikect
	 * @return
	 */
	public static String getJsapiTicket() {
		 // /jsticket?access_token=%s&type=jsapi    返回   ticket
		String url=AppConfigurator.getProperty("DOMAIN")+"/api/jsticket?access_token="+getToken()+"&type=jsapi";
		Map<String,Object> result_map=httpMethod(url);
		if(result_map!=null && result_map.get("ticket")!=null){
			return result_map.get("ticket").toString();
		}
		return "";
	}
	
	/**
	 * 	获取Token
	 * @return
	 */
	private  static String sendMBXX(TemplagePojo tpojo) {
		Map<String, Object> patm=new HashMap<String, Object>();
		patm.put("touser", tpojo.getOpenid());
		patm.put("template_id", tpojo.getTemplate_id());
		patm.put("topcolor", "#FF0000");
		patm.put("url", tpojo.getUrl());
		Map<String, Object> data=new HashMap<String, Object>();
		Map<String, Object> tmp1=new HashMap<String, Object>();
		tmp1.put("value", tpojo.getFirstData());
		tmp1.put("color", "#173177");
		data.put("first", tmp1);
		
		tmp1=new HashMap<String, Object>();
		tmp1.put("value", tpojo.getKeyword1());
		tmp1.put("color", "#173177");
		data.put("keyword1", tmp1);
		
		tmp1=new HashMap<String, Object>();
		tmp1.put("value", tpojo.getKeyword2());
		tmp1.put("color", "#173177");
		data.put("keyword2", tmp1);
		
		if(tpojo.getKeyword3()!=null && !"".equals(tpojo.getKeyword3())){
			tmp1=new HashMap<String, Object>();
			tmp1.put("value", tpojo.getKeyword3());
			tmp1.put("color", "#173177");
			data.put("keyword3", tmp1);
		}
		
		if(tpojo.getKeyword4()!=null && !"".equals(tpojo.getKeyword4())){
			tmp1=new HashMap<String, Object>();
			tmp1.put("value", tpojo.getKeyword4());
			tmp1.put("color", "#173177");
			data.put("keyword4", tmp1);
		}
			
			
			tmp1=new HashMap<String, Object>();
			tmp1.put("value", tpojo.getRemark());
			tmp1.put("color", "#173177");
		data.put("remark", tmp1);
		
		patm.put("data", data);
		return JsonUtil.toJsonObj(patm).toString();
	}
	
	/**

	 * 发送模板消息

	 * @param accessToken

	 * @param jsonData

	*/

	public static void sendTemplateMsg(TemplagePojo tpojo){
			String jsonData=sendMBXX(tpojo);
			String accessToken=getToken();
			String requestUrl=send_templatemsg_url.replace("ACCESS_TOKEN", accessToken);
			JSONObject jsonObject = httpRequest(requestUrl,"GET", jsonData);
			if(jsonObject!=null){
				if("0".equals(jsonObject.getString("errcode"))){
					System.out.println("发送模板消息成功！");
				}else{
					System.out.println(jsonObject.getString("errcode")+"::"+jsonObject.getString("errmsg"));
				}
			}

	}
	
	public static String getToken() {
		 // /token?grant_type=client_credential&appid=%s&appsecret=%s   返回 access_token
	    String appid = AppConfigurator.getProperty("APPID");
       String appSecret=AppConfigurator.getProperty("APPSECRET");
      
		String url=AppConfigurator.getProperty("DOMAIN")+"/api/token?grant_type=client_credential&appid="+appid+"&appsecret="+appSecret;
//       String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret;
		Map<String,Object> result_map=httpMethod(url);
		if(result_map!=null && result_map.get("access_token")!=null){
			return result_map.get("access_token").toString();
		}
		return "";
	}
	

	
	/**

	 * 发起https请求并获取结果

	*

	 * @param requestUrl 请求地址

	 * @param requestMethod 请求方式（GET、POST）

	 * @param outputStr 提交的数据

	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)

	*/

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				URL url = new URL(requestUrl);
				HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
				httpUrlConn.setSSLSocketFactory(ssf);
				httpUrlConn.setDoOutput(true);
				httpUrlConn.setDoInput(true);
				httpUrlConn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				httpUrlConn.setRequestMethod(requestMethod);
				if ("GET".equalsIgnoreCase(requestMethod)		)
					httpUrlConn.connect();
				// 当有数据需要提交		时
				if (null != outputStr) 		{
				OutputStream outputStream = httpUrlConn.getOutputStream()		;
					// 注意编码格式，防止中文乱		码
					outputStream.write(outputStr.getBytes("UTF-8"))		;
					outputStream.close()		;
				}
	
			
				// 将返回的输入流转换成字符		串
				InputStream inputStream = httpUrlConn.getInputStream()		;
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8")		;
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader)		;
				String str = null		;
				while ((str = bufferedReader.readLine()) != null) 		{
							buffer.append(str)		;
				}
				bufferedReader.close()		;
				inputStreamReader.close()		;
				// 释放资源
				inputStream.close()		;
				inputStream = null		;
				httpUrlConn.disconnect()		;
				jsonObject = JSONObject.fromObject(buffer.toString())	;
			}  catch (Exception e) 	{
				e.printStackTrace();
			}


			return jsonObject;
	}
	
	
	
	/**
	 * 发http请求
	 * @param url
	 * @return
	 */
	public static Map<String,Object> httpMethod(String url) {
		Map<String,Object> result_map=new HashMap<String, Object>();
		String result_str = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		try {
			// 设置编码
			CloseableHttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				result_str = "-" + statusCode;
			} else {
				result_str = EntityUtils.toString(response.getEntity());
				result_map=JsonUtil.strToMap(result_str);
			}
			response.close();
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			try {
				client.close();
			} catch (Exception e) {

			}
		}
		return result_map;
	}
	
	
	
	/**
	 * sign = md5(appid + “&” + md5(“phone=13860177051&proid=20001”) + “&” + appsecret)
	 * @param openid
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private static String getsign(String openid,long t) {
		String signature="";
        String appid = AppConfigurator.getProperty("APPID");
        String appSecret=AppConfigurator.getProperty("APPSECRET");
        
		String sign1 = "openid="+openid+"&time="+t;
		sign1=MD5.md5Hex(sign1);
		signature=appid+"&"+sign1+"&"+appSecret;
		signature=MD5.md5Hex(signature);
		return signature;
	}
	
	public static String getWxSignature(String url,String t,String nonceStr) throws Exception{
		String signature = "";
		List<String> list = new ArrayList<String> ();
		list.add("url="+url);
		list.add("noncestr="+nonceStr);
		list.add("timestamp="+t);
		list.add("jsapi_ticket="+getJsapiTicket());
		Collections.sort(list);
		String str = list.get(0)+"&"+list.get(1)+"&"+list.get(2)+"&"+list.get(3);
		signature = SHA1(str);
		return signature;
	}
	public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
