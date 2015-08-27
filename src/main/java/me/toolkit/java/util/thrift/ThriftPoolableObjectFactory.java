package me.toolkit.java.util.thrift;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi5 on 2015/7/29.
 */
public class ThriftPoolableObjectFactory implements PoolableObjectFactory {


    /**
     * 日志记录器
     */
    public static final Logger logger = LoggerFactory
            .getLogger(ThriftPoolableObjectFactory.class);
    /**
     * 服务的IP
     */
    private String serviceIP;
    /**
     * 服务的端口
     */
    private int servicePort;
    /**
     * 超时设置
     */
    private int timeOut;


    /**
     * @param serviceIP
     * @param servicePort
     * @param timeOut
     */
    public ThriftPoolableObjectFactory(String serviceIP, int servicePort,
                                       int timeOut) {
        this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.timeOut = timeOut;
    }

    @Override
    public Object makeObject() throws Exception {
        try {
            TTransport transport = new TSocket(this.serviceIP,
                    this.servicePort, this.timeOut);
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
        // DO NOTHING
    }

    @Override
    public void passivateObject(Object o) throws Exception {
        // DO NOTHING
    }

    public String getServiceIP() {
        return serviceIP;
    }

    public void setServiceIP(String serviceIP) {
        this.serviceIP = serviceIP;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
