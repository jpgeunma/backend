package com.jpmarket.config.jwt;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Filter res " + res);
        System.out.println("Filter req " + req);

        // TODO for chatting test ==============
        String origin = req.getHeader("Origin");
        List<String> allowedOrigins = Arrays.asList("http://localhost:3000");
        res.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        res.setHeader("Vary", "Origin");
        // ============================
        //res.setHeader("Access-Control-Allow-Origin", "*");
        // This is Origin
        //============================

        res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        res.setHeader("Access-Control-Expose-Headers", "Authorization, X-Total-Count, Link");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept," +
                " Accept-Encoding, Accept-Language, Host, Referer, Connection, User-Agent, authorization, sw-useragent, sw-version");

        System.out.println("req.getMethod: " + req.getMethod());

        if (req.getMethod().equals("OPTIONS")) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}