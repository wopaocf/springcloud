package com.itwq.xdvideo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装get post
 */
public class HttpUtils {
    /**
     * get 方法
     * @param url
     * @return
     */
    private  static final Gson gson=new Gson();

    public static Map<String,Object> doGet(String url){
        Map<String,Object> map=new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) //建立连接超时
                .setConnectionRequestTimeout(5000)   //请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//允许重定向
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try{

           HttpResponse httpResponse = httpClient.execute(httpGet);
           if (httpResponse.getStatusLine().getStatusCode()==200){
               String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                map = gson.fromJson(jsonResult,map.getClass());
           }

        }catch (Exception e){
                e.getMessage();
        }finally {
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
          return map;
    }

    /**
     * 封装post
     * @return
     */
    public static String dopost(String url,String data,int timeout) throws UnsupportedEncodingException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

         //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout) //建立连接超时
                .setConnectionRequestTimeout(timeout)   //请求超时时间
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(true)//允许重定向
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type","text/html; charset=utf-8");
        if (data!=null&&data instanceof String){//使用字符串传参
            StringEntity stringEntity = new StringEntity(data,"utf-8");
            httpPost.setEntity(stringEntity);
        }

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpentity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode()==200){
                String result = EntityUtils.toString(httpentity);
                return result;
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            try {
                httpClient.close();

            }catch (Exception e){
                e.getMessage();
            }
        }
        return null;
    }
}
