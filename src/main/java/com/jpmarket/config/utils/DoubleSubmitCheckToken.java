package com.jpmarket.config.utils;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmarket.web.userDto.SignUpRequest;
import lombok.val;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.WebUtils;
import org.apache.commons.collections4.map.LRUMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
public class DoubleSubmitCheckToken {

    public static final String DOUBLE_SUBMIT_CHECK_PARAMETER = "doubleCheckToken";

    private static final String DOUBLE_SUBMIT_CHECK_CONTEXT =
            DoubleSubmitCheckToken.class.getName() + ".CONTEXT";

    private static final Random random = new Random();

    public static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 画面から渡ってきたトークンを返します。
     *
     * @param request
     * @return actual token
     */
    public static String getActualAuthToken(HttpServletRequest request) throws IOException {
        return request.getParameter(DOUBLE_SUBMIT_CHECK_PARAMETER);
    }

    /**
     * セッションに保存されているトークンを返します。
     *
     * @param request
     * @return expected token
     */
    @SuppressWarnings("unchecked")
    public static String getExpectedAuthToken(HttpServletRequest request) {
        return getExpectedToken(request, null);
    }

    /**
     * 指定のキーをもとにセッションに保存されているトークンを返します。
     *
     * @param request
     * @param key
     * @return expected token
     */
    @SuppressWarnings("unchecked")
    public static String getExpectedToken(HttpServletRequest request, String key) {
        String token = null;

        if (key == null) {
            key = request.getRequestURI();
        }

        Object mutex = getMutex(request);
        if (mutex != null) {
            synchronized (mutex) {
                token = getToken(request, key);
            }
        }

        return token;
    }

    /**
     * 指定した属性名で値を取得します。
     *
     * @param request
     * @param attributeName
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(HttpServletRequest request, String attributeName) {
        T ret = null;
        val session = request.getSession(false);
        val mutex = getMutex(request);
        if (mutex != null) {
            synchronized (mutex) {
                ret = (T) session.getAttribute(attributeName);
            }
        }
        return ret;
    }

    /**
     * 指定した属性名で値を設定します。
     *
     * @param request
     * @param attributeName
     * @param value
     * @return
     */
    public static void setAttribute(HttpServletRequest request, String attributeName, Object value) {
        val session = request.getSession(false);
        val mutex = getMutex(request);
        if (mutex != null) {
            synchronized (mutex) {
                session.setAttribute(attributeName, value);
            }
        }
    }

    public static Object getMutex(HttpServletRequest request) {
        val session = request.getSession(false);

        if (session != null) {
            return WebUtils.getSessionMutex(session);
        }

        return null;
    }


    /**
     * 指定のキーをもとにセッションにトークンを設定します。
     *
     * @param request
     * @param key
     * @return token
     */
    @SuppressWarnings("unchecked")
    public static String renewToken(HttpServletRequest request, String key) {
        if (key == null) {
            key = request.getRequestURI();
            System.out.println("renewToken " + key);
        }
        val token = generateToken();

        Object mutex = getMutex(request);
        if (mutex != null) {
            synchronized (mutex) {
                setToken(request, key, token);
            }
        }

        return token;
    }

    /**
     * セッションにトークンを設定します。
     *
     * @param request
     * @return token
     */
    @SuppressWarnings("unchecked")
    public static String renewToken(HttpServletRequest request) {
        return renewToken(request, null);
    }

    /**
     * トークンを生成します。
     *
     * @return token
     */
    public static String generateToken() {
        return String.valueOf(random.nextInt(Integer.MAX_VALUE));
    }

    /**
     * セッションに格納されたLRUMapを取り出す。存在しない場合は作成して返す。
     *
     * @param request
     * @return
     */
    protected static LRUMap getLRUMap(HttpServletRequest request) {
        LRUMap map = getAttribute(request, DOUBLE_SUBMIT_CHECK_CONTEXT);

        if (map == null) {
            map = new LRUMap(10);
        }

        return map;
    }

    /**
     * トークンを取得する。
     *
     * @param request
     * @param key
     * @return
     */
    protected static String getToken(HttpServletRequest request, String key) {
        LRUMap map = getLRUMap(request);
        val token = (String) map.get(key);
        return token;
    }

    /**
     * トークンを保存する。
     *
     * @param request
     * @param key
     * @param token
     */
    protected static void setToken(HttpServletRequest request, String key, String token) {
        LRUMap map = getLRUMap(request);
        map.put(key, token);
        setAttribute(request, DOUBLE_SUBMIT_CHECK_CONTEXT, map);
    }
}