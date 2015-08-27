package me.toolkit.java.util.thrift.test;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;

/**
 * Created by wangdi5 on 2015/7/31.
 */
public class ConnectionPoolFactory {


    private static  ConnectionPoolFactory factory = null;
    public static final String serviceIP="";
    public static final int servicePort=9090;
    public static final int timeout=2000;
    public static GenericObjectPool.Config  config= null;
    private GenericObjectPool pool;

   static{
       config = new GenericObjectPool.Config();
       config.maxActive=200;
   }


    private ConnectionPoolFactory(){};

    public static  ConnectionPoolFactory getInstance(){
        if(factory == null){
            factory = new ConnectionPoolFactory(config,serviceIP, servicePort,timeout);
            return factory;
        }
        return factory;
    }




    private ConnectionPoolFactory(GenericObjectPool.Config config,String serviceIP,int servicePort,int timeout){
        ConnectionFactory factory = new ConnectionFactory(serviceIP, servicePort,timeout);
        pool = new GenericObjectPool(factory, config);
    }

    public TSocket getConnection() throws Exception{
        return (TSocket)pool.borrowObject();
    }

    public void releaseConnection(TSocket tSocket){
        try{
            pool.returnObject(tSocket);
        }catch(Exception e){
            if(tSocket != null){
                try{
                    tSocket.close();
                }catch(Exception ex){
                    //TODO
                }
            }
        }
    }



}
