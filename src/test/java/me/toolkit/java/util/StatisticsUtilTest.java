package me.toolkit.java.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 压测工具
 * @author wangdi0410@gmail.com
 */
public class StatisticsUtilTest {

	
	public Log log = LogFactory.getLog( StatisticsUtilTest.class );
	@Test
	public void testStatisticsTps() throws InterruptedException {
		log.info( "压测开始" );
		StatisticsUtil.start( 1 );
		
		ThreadUtil.startThread( new Runnable() {
			@Override
			public void run() {
				while( true ){
					StatisticsUtil.totalTransactions.addAndGet( 1 );
				}
			}
		} );
		
		Thread.sleep( 5 * 1000 );
	}
}
