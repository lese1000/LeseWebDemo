package tech.lese.webdemo.utils;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : 
 * @date: 2016/4/1
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        addCookie(response, name, value, -1);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(AppConfigurator.getProperty("PRODUCE.COOKIE.DOMAIN"));
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
