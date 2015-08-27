package me.toolkit.java.util.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangdi5 on 2015/7/24.
 */
public class HttpClientPoolManager {

    private static Log logger = LogFactory.getLog(HttpClientPoolManager.class);

    /**
     * 实例
     */
    private static HttpClientPoolManager poolManager = null;
    private static PoolingHttpClientConnectionManager clientConnectionManager = null;

    /**
     * 线程池最大个数
     */
    private static int defaultMaxTotal = 50;
    /**
     * 默认的route
     */
    private static int defaultMaxPerRoute = 25;


    /**
     * 超市时间
     */
    private static long defualtTimeout=3000;

    private static boolean isRetry = false;

    private static int  retryCount=0;

    /**
     * 默认启用重试 默认3次
     */
    private static DefaultHttpRequestRetryHandler retryHandler= null;

    static{
        ResourceBundle rb=null;
        try{

            rb = ResourceBundle.getBundle("httpclientpool");
        }catch(MissingResourceException e){
            logger.debug("httpclient连接池启用默认的参数 defaultMaxTotal="+defaultMaxTotal+",defaultMaxPerRoute="+defaultMaxPerRoute);
        }
        if(rb!=null){
            defaultMaxTotal = Integer.valueOf((String) rb.getObject("maxperroute"));
            defaultMaxPerRoute =Integer.valueOf((String) rb.getObject("max"));
            defualtTimeout=Long.valueOf((String) rb.getObject("timeout"));
            int retryFlag=Integer.valueOf((String) rb.getObject("isRetry"));
            if(retryFlag==0){
                isRetry=true;
            }
            retryCount=Integer.valueOf((String) rb.getObject("retrycount"));
            retryHandler= new DefaultHttpRequestRetryHandler(retryCount, isRetry);

        }
        logger.debug("httpclient连接池启用设置的参数 defaultMaxTotal="+defaultMaxTotal+",defaultMaxPerRoute="+defaultMaxPerRoute);
    }


    private HttpClientPoolManager(int maxTotal, int defaultMaxPerRoute) {
        this.defaultMaxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

    }

    private HttpClientPoolManager() {
        clientConnectionManager.setMaxTotal(defaultMaxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

    }


    private static HttpClientPoolManager getInstance() {
        if (poolManager == null) {
            synchronized (HttpClientPoolManager.class) {
                if (poolManager == null) {
                    clientConnectionManager = new PoolingHttpClientConnectionManager(defualtTimeout, TimeUnit.MILLISECONDS);
                    poolManager = new HttpClientPoolManager();
                }
            }

        }

        return poolManager;
    }

    /**
     * 返回httpclient
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        if (clientConnectionManager == null) {
            synchronized (HttpClientPoolManager.class) {
                clientConnectionManager = new PoolingHttpClientConnectionManager(defualtTimeout, TimeUnit.MILLISECONDS);
                getInstance();
            }
        }
        //构造完毕
        return HttpClients.custom().setConnectionManager(clientConnectionManager).setRetryHandler(retryHandler).build();
    }


    /**
     * 返回状态
     * @return
     */
    public String getTotalStats(){
        if(clientConnectionManager==null)
            return null;
        PoolStats poolStats=clientConnectionManager.getTotalStats();
        return poolStats.toString();
    }
}
