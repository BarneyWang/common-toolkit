package me.toolkit.java.util.thrift.impl;

import me.toolkit.java.util.thrift.ThriftConnectionProvider;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangdi5 on 2015/7/30.
 */
@Deprecated
public class DynamicClientProxy<T> implements InvocationHandler {

    public Object createProxy(Class<T> ts) {

        return Proxy.newProxyInstance(ts.getClassLoader(), ts.getInterfaces(), this);
    }

    /** 连接提供池 */
    public ThriftConnectionProvider connectionProvider;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TSocket socket = null;
        try
        {
            socket = connectionProvider.getConnection();
        }  catch (Exception e)
        {
            throw new Exception(e);
        }
        return null;
    }


    public static void main(String[] args) {
        TSocket tsocket = new TSocket("127.0.0.1", 8080);
         tsocket.setTimeout(2000);
        TProtocol protocol = new TBinaryProtocol(tsocket);
        DynamicClientProxy<TProtocol>  proxy = new     DynamicClientProxy<TProtocol>();

    }
}
