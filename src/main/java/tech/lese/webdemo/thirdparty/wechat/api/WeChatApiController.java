package tech.lese.webdemo.thirdparty.wechat.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tech.lese.webdemo.thirdparty.wechat.util.WeixinUtil;
import tech.lese.webdemo.utils.AppConfigurator;
import tech.lese.webdemo.utils.CookieUtil;
import tech.lese.webdemo.utils.JsonUtil;

@Controller(value="/wechat")
public class WeChatApiController {
	/**https://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
	 * 1 第一步：用户同意授权，获取code
		2 第二步：通过code换取网页授权access_token
		3 第三步：刷新access_token（如果需要）
		4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
		5 附：检验授权凭证（access_token）是否有效
	 */
	
	
	@RequestMapping(value = "/openUrl")
    public void openUrl( HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String appid = AppConfigurator.getProperty("APPID");
            //跳转至需要获取openid的页面（目标页面）,目标url中如果携带参数用@符号分割，非&
            String targetUrl = request.getParameter("url");
            String redirectUrl=AppConfigurator.getProperty("PRODUCE.PROJECT.URL")+"/wechat/callBack?url=" + targetUrl;//微信回调的接口
            String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_base&state=124512d#wechat_redirect";
            response.sendRedirect(authorizeUrl);	
        } catch (Exception e) {
            e.printStackTrace();
//            response.sendRedirect(Const.WEB_URL + "/m/public/error.html");
        }
    }
	
	//微信回调接口
	@RequestMapping(value = "/callBack")
    public void callBack( HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String code = request.getParameter("code");
            String url = request.getParameter("url").replaceAll("@","&");
            String appid = AppConfigurator.getProperty("APPID");
            String appSecret=AppConfigurator.getProperty("APPSECRET");
            if (!StringUtils.isEmpty(code)) {
            	 String backUrl="";
            	 Map<String, String> rmap=WeixinUtil.getWxOpenIDandAccess_token(code, appid, appSecret);
	                String openid=rmap.get("openid");
	                //1）openid保存在cookie中
	                if(openid!=null &&!"".equals(openid)){
	                	 //cookie过期时间
    	                int maxAge = 5 * 60;
    	                CookieUtil.addCookie(response, "openid",  openid, maxAge);
	                }
	                //2)openid拼接在访问路径中
	                if(url.contains("?")){
	                	backUrl=url+"&openid="+openid;
	                }else{
	                	backUrl=url+"?openid="+openid;
	                }
	                
	                response.sendRedirect(backUrl);
	        }else{
	        	//跳转错误页面，提示未获取到code值
	        }
        } catch (Exception e) {
            e.printStackTrace();
            //response.sendRedirect(Const.WEB_URL + "/m/public/error.html");
        }
    }
	
	
	/**
	    * 配合 wxJssdk.js使用
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	    @RequestMapping(value = "/getSignature")
	    @ResponseBody
	    public String  getSignature( HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	Map<String, Object> result=new HashMap<String, Object>();
	    	String signature="";
	    	String noncestr = request.getParameter("NONCESTR");
	        String url = request.getParameter("URL");
	        String timestame = request.getParameter("TIMESTAME");
	        try {
	            signature= WeixinUtil.getWxSignature(url, timestame, noncestr);
	            result.put("msg", "success");
	        } catch (Exception e) {
	        	 result.put("msg", "error:"+e.getMessage());
	            e.printStackTrace();
	        }
	        result.put("signature", signature);
	        result.put("appid", AppConfigurator.getProperty("APPID"));
	        return JsonUtil.toJsonObj(result).toString();
	    }
}
