package me.toolkit.java.util.thrift.spring.impl;

import me.toolkit.java.util.thrift.ThriftConnectionProvider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi5 on 2015/7/29.
 */
public class ConnectionManager implements MethodInterceptor {


    /** 日志记录器 */
    public Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    /** 保存local对象 */
    ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();
    /** 连接提供池 */
    public ThriftConnectionProvider connectionProvider;



    @Override
    public Object invoke(MethodInvocation arg0) throws Throwable
    {
        TSocket socket = null;
        try
        {
            socket = connectionProvider.getConnection();
            socketThreadSafe.set(socket);
            Object ret = arg0.proceed();
            return ret;
        }
        catch (Exception e)
        {
            logger.error("error ConnectionManager.invoke()", e);
            throw new Exception(e);
        }
        finally
        {
            connectionProvider.returnCon(socket);
            socketThreadSafe.remove();
        }
    }
    /**
     * 取socket
     *
     * @return
     */
    public TSocket getSocket()
    {
        return socketThreadSafe.get();
    }
    public ThriftConnectionProvider getConnectionProvider()
    {
        return connectionProvider;
    }
    public void setConnectionProvider(ThriftConnectionProvider connectionProvider)
    {
        this.connectionProvider = connectionProvider;
    }

}
