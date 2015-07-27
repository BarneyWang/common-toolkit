package me.toolkit.java.util.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;

/**
 * Created by wangdi5 on 2015/7/24.
 */
public class HttpClientPoolManager {



    /**
     * 实例
     */
    private static HttpClientPoolManager poolManager = null;
    private static PoolingHttpClientConnectionManager clientConnectionManager = null;

    private int maxTotal = 50;

    private int defaultMaxPerRoute = 25;

    private HttpClientPoolManager(int maxTotal, int defaultMaxPerRoute) {
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private HttpClientPoolManager() {
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }


    private static HttpClientPoolManager getInstance() {
        if (poolManager == null) {
            synchronized (HttpClientPoolManager.class) {
                if (poolManager == null) {
                    clientConnectionManager = new PoolingHttpClientConnectionManager();
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
                clientConnectionManager = new PoolingHttpClientConnectionManager();
                getInstance();
            }
        }
        //构造完毕
        return HttpClients.custom().setConnectionManager(clientConnectionManager).build();
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
