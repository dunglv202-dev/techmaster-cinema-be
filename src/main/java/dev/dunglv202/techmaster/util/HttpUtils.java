package dev.dunglv202.techmaster.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
    public static String extractIpAddress(HttpServletRequest request) {
        String proxyForwardInfo = request.getHeader("X-FORWARDED-FOR");
        if (proxyForwardInfo != null && !proxyForwardInfo.isBlank()) {
            return proxyForwardInfo.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
