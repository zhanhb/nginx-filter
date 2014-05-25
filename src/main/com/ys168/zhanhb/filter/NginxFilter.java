package com.ys168.zhanhb.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class NginxFilter implements Filter {

    private static final String seperator = "\\s*,\\s*";
    private Set<String> excludes;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludesString = filterConfig.getInitParameter("excludes");
        if (excludesString != null) {
            excludes = new HashSet<String>(Arrays.asList(excludesString.trim().split(seperator)));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request instanceof HttpServletRequest ? new NginxRequest((HttpServletRequest) request) : request, response);
    }

    @Override
    public void destroy() {
    }

    private class NginxRequest extends HttpServletRequestWrapper {

        NginxRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getRemoteAddr() {
            String forwardedFor = getHeader("X-Forwarded-For");
            if (forwardedFor != null) {
                String[] split = forwardedFor.trim().split(seperator);
                Set<String> trust = excludes;
                if (trust != null) {
                    String ip = super.getRemoteAddr().trim();
                    for (int i = split.length; trust.contains(ip) && --i >= 0;) {
                        if (isValidAddress(split[i])) {
                            ip = split[i];
                        }
                    }
                    return ip;
                } else {
                    for (int i = 0; i < split.length; ++i) {
                        if (isValidAddress(split[i])) {
                            return split[i];
                        }
                    }
                }
            }
            return super.getRemoteAddr();
        }

        @Override
        public String getRemoteHost() {
            return getRemoteAddr();
        }

        private boolean isValidAddress(String str) {
            return str != null && str.length() > 0 && !str.equalsIgnoreCase("unknown");
        }
    }
}
