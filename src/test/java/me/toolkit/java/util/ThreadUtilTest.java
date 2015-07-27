package me.toolkit.java.util;

import org.junit.Test;

/**
 * @author wangdi0410@gmail.com
 */
public class ThreadUtilTest {

	@Test
	public void testStartThreadRunnableInt() {
		System.out.println( System.currentTimeMillis( ) );
		ThreadUtil.startThread( new Job(), 20 );
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println( System.currentTimeMillis( ) );
		super.finalize();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}


class Job implements Runnable{

	@Override
	public void run() {
		System.out.println( Thread.currentThread().getName() + System.nanoTime() );
	}
	
}
