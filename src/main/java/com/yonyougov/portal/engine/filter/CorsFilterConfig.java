package com.yonyougov.portal.engine.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Locker
 * @date 15/05/2018 10:44 AM
 * @since 1.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        final HttpServletResponse response = (HttpServletResponse) res;
        // 设置允许跨域的域名，需要发送cookie信息，所以此处需要指定具体的域名
        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Headers", "*");
        // 允许跨域的请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        /**
         * ajax请求的时候如果带有xhrFields:{withCredentials:true}，
         * 那么服务器后台在配置跨域的时候就必须要把Access-Control-Allow-Credentials这个请求头加上去
         */
        // 允许发送Cookie信息
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 支持HTTP 1.1
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        // 支持HTTP 1.0. response.setHeader("Expires", "0");
        response.setHeader("Pragma", "no-cache");

        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }

    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
