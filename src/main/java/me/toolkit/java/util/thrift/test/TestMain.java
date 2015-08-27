package me.toolkit.java.util.thrift.test;

import org.apache.thrift.transport.TSocket;

/**
 * Created by wangdi5 on 2015/7/31.
 */
public class TestMain {


    /**
     * @param args
     */
    public static void main(String[] args) {

        TSocket socket = null;
        try {
            socket = ConnectionPoolFactory.getInstance().getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {

                ConnectionPoolFactory.getInstance().releaseConnection(socket);

            }
        }

    }

}
