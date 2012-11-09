package com.oilchem.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * User: leochen
 * Date: 12-4-30
 * Time: 下午3:50
 */
public abstract class CookieUtils {
    public static final int MAX_AGE = 60 * 60 * 24 * 7;


    public static final String DEFAULT_ENCODING = "UTF-8";


    private CookieUtils(){}


    /**
     *
     * @param response
     * @param key
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String key,
                                 String value) {
        addCookie(response, key, value, MAX_AGE);

    }

    public static void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     *
     * @param response
     * @param key
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String key,
                                 String value, int maxAge) {
        try {
            value = URLEncoder.encode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     *
     * @param request
     * @param key
     * @return
     */
    public static String findCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (key.equals(cookies[i].getName())) {
                    try {
                        return URLDecoder.decode(cookies[i].getValue(), DEFAULT_ENCODING);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

}
