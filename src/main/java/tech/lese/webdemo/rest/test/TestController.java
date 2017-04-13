package tech.lese.webdemo.rest.test;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tech.lese.webdemo.utils.ThreeDES;

@Controller
@RequestMapping("test")
public class TestController {
	
	@RequestMapping("login")
	public void doLogin(HttpServletResponse response){
		
		try {
			response.sendRedirect(getRedirectUrl("13779939945"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getRedirectUrl(String mobile){
		String shopId = "xmhgo";
		String urlcode = "1001";
		String authenticator = "";
		String key = "G1AS13H3CHCSAEOXMRT3UAVX";
		String desSrc = shopId + "$" + mobile + "$" + urlcode;
		byte[] authenticatorByte = ThreeDES.encryptMode(key, desSrc);
		authenticator = new String(Base64.getEncoder().encode(authenticatorByte));
		return "http://m.059mall.com.cn/zf/Weixin2APP.aspx?shopId="+shopId+"&authenticator="+authenticator+"&mobile="+mobile+"&urlcode="+urlcode;
		
	}
	
	public static void main(String[] args){
		System.out.println(getRedirectUrl("13779939945"));
	}

}
