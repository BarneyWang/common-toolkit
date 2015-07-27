package me.toolkit.java.util;


import me.toolkit.java.constant.SymbolConstant;
import me.toolkit.java.exception.IllegalParamException;

import java.util.Timer;
import java.util.TimerTask;

/**
 *  thead util
 * @author wangdi0410@gmail.com
 */
public class ThreadUtil {

	/**
	 *
	 * @param runnable  task
	 * @param threadNum
	 * @param sleepTime sleep time
	 */
	public static void startThread( Runnable runnable, String threadName, int threadNum, long sleepTime ) {

		for ( int i = 0; i < threadNum; i++ ) {
			Thread thread = new Thread( runnable, SymbolConstant.POUND + StringUtil.defaultIfBlank( threadName, "Thread" ) + SymbolConstant.MINUS_SIGN + i );
			thread.start();
		}
		try {
			Thread.sleep( sleepTime );
		} catch ( InterruptedException e ) {
		}
	}
	
	
	
	/**
	 *
	 * @param runnable
	 * @param threadNum
	 * @param sleepTime
	 */
	public static void startThread( Runnable runnable, int threadNum, long sleepTime ) {
		ThreadUtil.startThread( runnable, "Thread", threadNum, sleepTime );
	}
	
	

	/**
	 * single thread to execute task
	 * @param runnable
	 */
	public static void startThread( Runnable runnable ) {
		startThread( runnable, 1, 0 );
	}
	
	/**
	 * single thread to execute task
	 * @param runnable
	 */
	public static void startThread( Runnable runnable, String threadName) {
		startThread( runnable, StringUtil.trimToEmpty( threadName ), 1, 0 );
	}
	

	/**
	 *
	 * @param runnable
	 * @param sleepTime sleep time
	 */
	public static void startThread( Runnable runnable, long sleepTime ) {
		startThread( runnable, 1, sleepTime );
	}

	/**
	 * schedule
	 * @param task
	 * @param delay
	 * @param period
	 * @throws IllegalParamException 
	 */
	public static void scheduleAtFixedRateDelayTimeMillisDelay( TimerTask task, long delay, long period ) throws IllegalParamException {
		if( ObjectUtil.isBlank( task ) )
			throw new IllegalParamException( "task 为空" );
		new Timer().scheduleAtFixedRate( task, delay, period );
	}
	
	/**
	 *
	 * @param task
	 * @param hourOfTomorrow
	 * @param period
	 * @throws Exception 
	 * @throws IllegalParamException 
	 */
	public static void scheduleAtFixedRateDelayDaysHour( TimerTask task, int delayDays, int hourOfTomorrow, long period ) throws IllegalParamException, Exception {
		ThreadUtil.scheduleAtFixedRateDelayTimeMillisDelay( task, DateUtil.getTimeMillisToAfterDaysHour( delayDays, hourOfTomorrow ), period );
	}
	
	/**
	 * Sleep thread without exception.
	 * @param millis
	 */
	public static void sleep( long millis ){
		try {
			Thread.sleep( millis );
		} catch ( Throwable e ) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	

}