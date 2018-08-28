package me.tanyp.util.http;

import com.google.gson.Gson;
import me.tanyp.util.basic.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanyp on 2018/3/5
 */
public class HttpRequest {

    public static String httpError() {
        Gson gson = new Gson();
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("ret", false);
        map.put("msg", "参数不可忽略");
        map.put("code", "006");
        map.put("data", "{}");
        return gson.toJson(map);
    }

    public static Map<String, Object> httpGet(String url) {
        if (StringUtils.isEmpty(url)) return Collections.EMPTY_MAP;
        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "UTF-8");
                map = gson.fromJson(content, HashMap.class);
                map.put("status", response.getStatusLine().getStatusCode());
                map.put("ret", true);
            } else {
                map.put("status", response.getStatusLine().getStatusCode());
                map.put("ret", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e);
            map.put("ret", false);
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                map.put("msg", e);
                map.put("ret", false);
            }
        }
        return map;
    }

    public static String httpPost(String json, String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(json, Charset.forName("UTF-8"));
        se.setContentType("application/json");
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Charset", "UTF-8");
        httpPost.setEntity(se);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                return "{\"msg\":\"请求异常\",\"ret\":\"false\",\"code\":\"0001\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"msg\":\"请求异常\",\"ret\":\"false\",\"code\":\"0001\"}";
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "{\"msg\":\"请求异常\",\"ret\":\"false\",\"code\":\"0001\"}";
            }
        }
    }
}
