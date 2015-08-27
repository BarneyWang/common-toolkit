package me.toolkit.java.util.thrift;

import org.apache.thrift.transport.TSocket;

/**
 * Created by wangdi5 on 2015/7/29.
 */
public interface ThriftConnectionProvider {


    /**
     * 取链接池中的一个链接
     *
     * @return
     */
    public TSocket getConnection();
    /**
     * 返回链接
     *
     * @param socket
     */
    public void returnCon(TSocket socket);
}
