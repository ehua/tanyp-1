package com.tanyouping.weixiao.util;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanyp on 2018/3/5
 */
public class HttpRequest {

    @Test
    public void httpGet() throws Exception{
        Gson gson = new Gson();
        //1.将其想象成建一个浏览器的过程，HttpClients我个人感觉可以类比Collection和Collections的关系，提供HTTPClient的工具
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.可以想象为用什么方法去访问服务，就像表单提交时候选择Get还是Post
        HttpGet httpget = new HttpGet("http://cx.shouji.360.cn/phonearea.php?number="+ new String("15112379353"));
        //3.可以想象为点击鼠标的过程，或者是提交表单的过程。有返回值。。。。。
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            //同样可以进行拆箱的工作获取相关参数
            System.out.println(response.getProtocolVersion());
            //StatusLine 其实就是响应中的第一行，包含协议版本号，状态，短语
            System.out.println(response.getStatusLine().toString());
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
            //业务处理层的东西
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity,"UTF-8");
            Map<String,Object> map = gson.fromJson(content,HashMap.class);
            System.out.println(map);
        } finally {
            response.close();
            httpclient.close();
        }
    }

    @Test
    public void httpPost() throws Exception{
        Gson gson = new Gson();
        //1.将其想象成建一个浏览器的过程，HttpClients我个人感觉可以类比Collection和Collections的关系，提供HTTPClient的工具类
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.可以想象为用什么方法去访问服务，就像表单提交时候选择Get还是Post

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("api.kdniao.cc")
                .setPath("/Ebusiness/EbusinessOrderHandle.aspx")
                .setParameter("OrderCode","131747920834951309")
                .setParameter("ShipperCode","ZTO")
                .setParameter("LogisticCode","480011625952")
                .build();
        HttpPost httpPost = new HttpPost(uri);

        System.out.println(httpPost.getURI());
        //3.可以想象为点击鼠标的过程，或者是提交表单的过程。有返回值。。。。。
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            //同样可以进行拆箱的工作获取相关参数
            System.out.println(response.getProtocolVersion());
            //StatusLine 其实就是响应中的第一行，包含协议版本号，状态，短语
            System.out.println(response.getStatusLine().toString());
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());

            if (response.getStatusLine().getStatusCode() == 200){
                //业务处理层的东西
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity,"UTF-8");
                System.out.println(content);
                Map<String,Object> map = gson.fromJson(content,HashMap.class);
                System.out.println(map);
            }

        } finally {
            response.close();
            httpclient.close();
        }

    }
}
