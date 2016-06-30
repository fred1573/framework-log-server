package com.tomato.framework.log.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author Hunhun
 */
public class HttpUtil {
    public static final String ASSIGN_SIGN = "=";
    public static final String JOIN_SIGN = "&";
    public static final int TIME_OUT = 10000;//单位毫秒

    /**
     * get
     */
    public String get(String url, boolean hasConnTimeout) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            //是否使用超时
            if(hasConnTimeout) {
                httpget.setConfig(getRequestConfig(TIME_OUT));
            }
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
                return null;
            }
        }catch (ConnectTimeoutException e) {
            throw new RuntimeException("接口：" + url + "请求超时");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
    /**
     * 重载get方法定时
     */
    public String get(String url) {
        return get(url, true);
    }

    /**
     * Build get url   host+params
     */
    public String buildUrl(String host, String file, Map<String, Object> params){
        StringBuilder sb = new StringBuilder().append(host);
        if(StringUtils.isBlank(file)){
            return sb.toString();
        }
        sb.append(file);
        if(params == null || params.size() <= 0){
            return sb.toString();
        }
        sb.append("?");
        for(String key : params.keySet()){
            try {
                sb.append(key).append(ASSIGN_SIGN).append(URLEncoder.encode(params.get(key).toString(), "utf-8")).append(JOIN_SIGN);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.substring(0, sb.lastIndexOf(JOIN_SIGN));
    }

    /**
     * 获取requestConfig
     */
    private static RequestConfig getRequestConfig(int i) {
        return RequestConfig.custom().
                setSocketTimeout(i).
                setConnectTimeout(i).
                setConnectionRequestTimeout(i).
                setStaleConnectionCheckEnabled(true).
                build();
    }
}
