package me.toolkit.java.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi5 on 2015/7/27.
 */
public class HttpClientPoolManagerTest {

    private static  void executePost() throws UnsupportedEncodingException {
        String url="http://www.baidu.com";
        HttpPost post = new HttpPost();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("token", ""));
        nvps.add(new BasicNameValuePair("password", "secret"));
        post.setEntity(new UrlEncodedFormEntity(nvps));
        try {
            CloseableHttpResponse response1 = HttpClientPoolManager.getHttpClient().execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        String url="http://www.baidu.com";
        HttpGet Httpget = new HttpGet(url);


        HttpResponse response = null;
        String body = "";
        try{
            response = HttpClientPoolManager.getHttpClient().execute(Httpget);
            body = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {

            e.printStackTrace();
        }

        System.out.println(body);


        HttpResponse response2 = null;
        String body2 = "";
        try{
            response2 = HttpClientPoolManager.getHttpClient().execute(Httpget);
            body2 = EntityUtils.toString(response2.getEntity());
        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            try {
                HttpClientPoolManager.getHttpClient().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(body2);
    }
}
