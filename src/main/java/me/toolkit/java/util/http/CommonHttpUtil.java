package me.toolkit.java.util.http;

import com.google.common.collect.Lists;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdi5 on 2015/8/17.
 */
public class CommonHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonHttpUtil.class);

    public static final int count = 3;





    public static String get(){
        String content="";
        for(int i=0;i<count;i++){

        }
        return content;
    }


    /**
     *
     * 发送get
     * @param url
     * @param parameters
     * @return
     * @throws IOException
     */
    public static String executeGet(String url, Map<String, String> parameters) throws IOException {
        StringBuilder uri = new StringBuilder();
        String getUrl ="";
        //添加参数
        uri.append(url);
        if (!parameters.isEmpty()) {

            uri.append("?");
            Iterator<Map.Entry<String, String>> it = parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                uri.append(entry.getKey()).append("=").append(entry.getValue());
                uri.append("&");
            }
        }
        getUrl = uri.toString();
        if(getUrl.lastIndexOf("&")==(getUrl.length()-1)){
            getUrl = getUrl.substring(0,getUrl.length()-1);
        }
        HttpGet get = new HttpGet(getUrl);
        CloseableHttpResponse response = null;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            response = HttpClientPoolManager.getHttpClient().execute(get);
            if (logger.isDebugEnabled()) {
                logger.debug("http response code=" + response.getStatusLine().getStatusCode());
            }
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            is = resEntity.getContent();
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {

            response.close();
            reader.close();
            is.close();
        }
    }


    /**
     * 执行post
     *
     * @param url
     * @param parameters
     * @return
     */
    public static String executePost(String url, Map<String, String> parameters) throws IOException {
        HttpPost post = new HttpPost(url);
        EntityBuilder entityBuilder = EntityBuilder.create();
        Iterator<Map.Entry<String, String>> it = parameters.entrySet().iterator();
        List<BasicNameValuePair> parasList = Lists.newArrayList();
        //添加参数
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            parasList.add(new BasicNameValuePair(key.toString(), value.toString()));
        }

        CloseableHttpResponse response = null;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            response = HttpClientPoolManager.getHttpClient().execute(post);
            if (logger.isDebugEnabled()) {
                logger.debug("http response code=" + response.getStatusLine().getStatusCode());
            }
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            is = resEntity.getContent();
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
            return sb.toString();
        } finally {

            response.close();
            reader.close();
            is.close();
        }

    }


    /**
     * 文件上传
     *
     * @param url        上传接口url
     * @param file       上传文件
     * @param parameters 上传参数
     * @return 上传接口返回结果
     */
    public static String uploadFile(String url, File file, Map<String, String> parameters) throws IOException {

        HttpPost filePost = new HttpPost(url);
        FileBody fileBody = new FileBody(file);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        Iterator<Map.Entry<String, String>> it = parameters.entrySet().iterator();
        //添加参数
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            StringBody strBody = new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8));
            multipartEntityBuilder.addPart(entry.getKey(), strBody);

        }

        filePost.setEntity(multipartEntityBuilder.addPart("file", fileBody).build());

        CloseableHttpResponse response = null;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            response = HttpClientPoolManager.getHttpClient().execute(filePost);
            if (logger.isDebugEnabled()) {
                logger.debug("http response code=" + response.getStatusLine().getStatusCode());
            }
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            is = resEntity.getContent();
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }

            return sb.toString();
        } finally {

            response.close();
            reader.close();
            is.close();

        }


    }

}
