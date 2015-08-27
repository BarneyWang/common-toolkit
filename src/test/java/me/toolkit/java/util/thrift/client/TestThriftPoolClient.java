package me.toolkit.java.util.thrift.client;

import me.toolkit.java.util.thrift.spring.impl.ConnectionManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangdi5 on 2015/7/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/thrift/thriftpool-spring.xml")
public class TestThriftPoolClient {



    private ConnectionManager connectionManager;




    @Test
    public void testPool(){
        TProtocol protocol = new TBinaryProtocol(connectionManager.getSocket());
        //TODO 逻辑

//        PushRPCService.Client client = new PushRPCService.Client(protocol);
//        try
//        {
//            List<Long> onLineIdList = client.getOnLineRoleIdList(roleIdList);
//            return onLineIdList;
//        }
//        catch (TException e)
//        {
//            logger.error("error getOnLineRoleIdList()", e);
//        }
//        return new ArrayList<Long>();



    }





    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
