package me.toolkit.java.util.thrift.impl;

import me.toolkit.java.util.thrift.ThriftConnectionProvider;
import me.toolkit.java.util.thrift.spring.impl.ThriftConnectionProviderViaSpringImpl;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi5 on 2015/7/29.
 */
@Deprecated
public class ThriftConnectionProvicerImpl implements ThriftConnectionProvider {


    private static final Logger logger = LoggerFactory.getLogger(ThriftConnectionProviderViaSpringImpl.class);

    /**
     * 服务的IP地址
     */
    private String serviceIP;
    /**
     * 服务的端口
     */
    private int servicePort;
    /**
     * 连接超时配置
     */
    private int conTimeOut;
    /**
     * 可以从缓存池中分配对象的最大数量
     */
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
    /**
     * 缓存池中最大空闲对象数量
     */
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
    /**
     * 缓存池中最小空闲对象数量
     */
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
    /**
     * 阻塞的最大数量
     */
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;

    /**
     * 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法
     */
    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;

    /**
     * 对象缓存池
     */
    private ObjectPool objectPool = null;


    public  ThriftConnectionProvicerImpl(String serviceIP, int servicePort, int conTimeOut) {
        this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.conTimeOut = conTimeOut;
    }

    public  ThriftConnectionProvicerImpl(String serviceIP,
                                             int servicePort,
                                             int conTimeOut,
                                             int maxActive,
                                             int maxIdle,
                                             int minIdle,
                                             int maxWait) {
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.maxWait = maxWait;
        new ThriftConnectionProvicerImpl(serviceIP, servicePort, conTimeOut);
    }


    public  ThriftConnectionProvicerImpl(String serviceIP,
                                             int servicePort,
                                             int conTimeOut,
                                             int maxActive,
                                             int maxIdle,
                                             int minIdle,
                                             int maxWait,boolean onBorrow,
                                             boolean onReturn,
                                             boolean whileIdle) {
        this.testOnBorrow = onBorrow;
        this.testOnReturn = onReturn;
        this.testWhileIdle = whileIdle;
        new ThriftConnectionProvicerImpl(serviceIP, servicePort, conTimeOut,maxActive,maxIdle,minIdle,maxWait);
    }



    /**
     * 取链接池中的一个链接
     *
     * @return
     */
    @Override
    public TSocket getConnection() {
        try {
            TSocket socket = (TSocket) objectPool.borrowObject();
            return socket;
        } catch (Exception e) {
            throw new RuntimeException("error getConnection()", e);
        }
    }

    /**
     * 返回链接
     *
     * @param socket
     */
    @Override
    public void returnCon(TSocket socket) {

        try {
            objectPool.returnObject(socket);
        } catch (Exception e) {
            throw new RuntimeException("error returnCon()", e);
        }
    }


    /**
     * 关闭线程池
     */
    public void destory() {
        try {
            objectPool.close();
        } catch (Exception e) {
            throw new RuntimeException("error destory()", e);
        }
    }


    public static void main(String[] args) {
        ThriftConnectionProvider provider = new ThriftConnectionProvicerImpl("1",1,1);
    }
}
