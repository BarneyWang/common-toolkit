package me.toolkit.java.util.thrift.test;

import me.toolkit.java.util.thrift.ThriftPoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi5 on 2015/7/31.
 */
public class ConnectionFactory implements PoolableObjectFactory {

    /**
     * 日志记录器
     */
    public static final Logger logger = LoggerFactory
            .getLogger(ThriftPoolableObjectFactory.class);

    private String serviceIP;
    private int servicePort;
    private int timeout;

    public ConnectionFactory(String serviceIP, int servicePort ,int timeout) {
        this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.timeout  = timeout;

    }


    @Override
    public Object makeObject() throws Exception {
        try {
            TTransport transport = new TSocket(this.serviceIP,
                    this.servicePort, this.timeout);
            transport.open();
            return transport;
        } catch (Exception e) {
            logger.error("error ThriftPoolableObjectFactory()", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroyObject(Object o) throws Exception {

        if (o instanceof TSocket) {
            TSocket socket = (TSocket) o;
            if (socket.isOpen()) {
                socket.close();
            }
        }
    }

    @Override
    public boolean validateObject(Object o) {
        try {
            if (o instanceof TSocket) {
                TSocket thriftSocket = (TSocket) o;
                if (thriftSocket.isOpen()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void activateObject(Object o) throws Exception {

        //TODO
    }

    @Override
    public void passivateObject(Object o) throws Exception {
        //TODO

    }
}
