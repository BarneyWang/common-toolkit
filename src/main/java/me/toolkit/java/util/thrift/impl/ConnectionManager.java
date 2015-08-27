package me.toolkit.java.util.thrift.impl;

import me.toolkit.java.util.thrift.ThriftConnectionProvider;
import org.apache.thrift.transport.TSocket;

/**
 *
 * Created by wangdi5 on 2015/7/29.
 */
@Deprecated
public class ConnectionManager {

    public static  final  ConnectionManager instance = null;

    private static ThriftConnectionProvider provider = null;

    public static ConnectionManager getInstance(){
        if(instance==null){
            provider = new ThriftConnectionProvicerImpl("",8585,2000);
            return new ConnectionManager();
        }
        return instance;
    }

    private ConnectionManager(){};



    public static TSocket getSocket(){
        return provider.getConnection();
    }

    public static void returnSocket(TSocket socket){
        provider.returnCon(socket);
    }



}
