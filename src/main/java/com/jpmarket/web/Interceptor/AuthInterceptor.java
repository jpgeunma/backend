package com.jpmarket.web.Interceptor;

import com.jpmarket.config.utils.DoubleSubmitCheckToken;
import lombok.val;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthInterceptor  extends HandlerInterceptorAdapter {


    private static final ThreadLocal<String> EXPECTED_TOKEN = new ThreadLocal<>();

    private static final ThreadLocal<String> ACTUAL_TOKEN = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // コントローラーの動作前
        val expected = DoubleSubmitCheckToken.getExpectedAuthToken(request);
        val actual = DoubleSubmitCheckToken.getActualAuthToken(request);
        System.out.println("preHandle " + expected + " " + actual);
        EXPECTED_TOKEN.set(expected);
        ACTUAL_TOKEN.set(actual);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // コントローラーの動作後
        if (StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
            // POSTされたときにトークンが一致していれば新たなトークンを発行する
            val expected = DoubleSubmitCheckToken.getExpectedAuthToken(request);
            val actual = DoubleSubmitCheckToken.getActualAuthToken(request);
            System.out.println("exptected " + expected + " actual " + actual);
            if (expected != null && actual != null && Objects.equals(expected, actual)) {
                DoubleSubmitCheckToken.renewToken(request);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 処理完了後
        EXPECTED_TOKEN.remove();
        ACTUAL_TOKEN.remove();
    }
}
