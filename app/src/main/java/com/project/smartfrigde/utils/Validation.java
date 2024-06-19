package com.project.smartfrigde.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class Validation {
    public static Long extractUserID(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static final String STOMP_WEB_SOCKET_URL = "ws:// 192.168.11.3:9999/ws";
    public static final String WEB_SOCKET_URL = "ws:// 192.168.11.3:9999/handle";
    public static final String WEB_SERVICE_URL = "http://192.168.11.3:9999";
    public static final int TYPE_USER = 0;
    public static final int TYPE_BOT = 1;
    public static final int TYPE_LOADING = 2;
    public static final String APIKEY = "AIzaSyDd8JllNQLsL-bC-cxhz5J-u-Dld6lcb7U";
    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public static Boolean isValidNumber(String number){
        for (int i = 0; i < number.length(); i++) {
            if (Character.isLetter(number.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
