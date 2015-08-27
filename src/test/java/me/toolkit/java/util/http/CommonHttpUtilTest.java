package me.toolkit.java.util.http;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wangdi5 on 2015/8/17.
 */
public class CommonHttpUtilTest {


    public static String testUpload() throws IOException {
String str =  CommonHttpUtil.uploadFile("http://localhost:8080/fileupload",new File("C:\\Users\\wangdi5\\Desktop\\}VM(OMT4@QUGO2A06)US4SA.png"), Maps.<String, String>newHashMap());
        System.out.println(str);
        return  str;
    }


    public static void main(String[] args) throws IOException {
//        testUpload();
       testPost();
//        testGet();
    }

    private static void testGet() {
        String url="http://10.154.252.64:8081/playlist/records/";
        HashMap<String,String> map = Maps.newHashMap();
        map.put("limit","10");
        map.put("start","0");
        map.put("token","test");
        try {
            String str = CommonHttpUtil.executeGet(url,map);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void testPost() {
      String uri = "http://123123123123123.com?va=1&dsa=2&";
        System.out.println(uri.lastIndexOf("&"));
        System.out.println(uri.substring(1,2));
        System.out.println(uri.length());
        System.out.println(uri.substring(0,uri.length()));
        System.out.println(Strings.nullToEmpty("12"));

    }
}
