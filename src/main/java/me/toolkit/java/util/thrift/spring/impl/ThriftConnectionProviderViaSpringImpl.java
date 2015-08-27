package me.toolkit.java.util.thrift.spring.impl;

import me.toolkit.java.util.thrift.ThriftConnectionProvider;
import me.toolkit.java.util.thrift.ThriftPoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 结合Spring
 * 缺点:跟spring结合太深
 * 需要使用到aop 拦截thrift
 * Created by wangdi5 on 2015/7/29.
 */
public class ThriftConnectionProviderViaSpringImpl implements ThriftConnectionProvider,InitializingBean,DisposableBean {


    private static  final Logger logger = LoggerFactory.getLogger(ThriftConnectionProviderViaSpringImpl.class);

    /** 服务的IP地址 */
    private String serviceIP;
    /** 服务的端口 */
    private int servicePort;
    /** 连接超时配置 */
    private int conTimeOut;
    /** 可以从缓存池中分配对象的最大数量 */
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
    /** 缓存池中最大空闲对象数量 */
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
    /** 缓存池中最小空闲对象数量 */
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
    /** 阻塞的最大数量 */
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;

    /** 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;

    /** 对象缓存池 */
    private ObjectPool objectPool = null;



    public TSocket getConnection() {
        try
        {
            TSocket socket = (TSocket) objectPool.borrowObject();
            return socket;
        }
        catch (Exception e)
        {
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

        try
        {
            objectPool.returnObject(socket);
        }
        catch (Exception e)
        {
            throw new RuntimeException("error returnCon()", e);
        }

    }

    @Override
    public void destroy() throws Exception {

        try
        {
            objectPool.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("erorr destroy()", e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 对象池
        objectPool = new GenericObjectPool();
        //
        ((GenericObjectPool) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
        ((GenericObjectPool) objectPool).setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        // 设置factory
        ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(
                serviceIP, servicePort, conTimeOut);
        objectPool.setFactory(thriftPoolableObjectFactory);

    }
}
