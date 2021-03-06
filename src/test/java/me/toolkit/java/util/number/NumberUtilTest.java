package me.toolkit.java.util.number;


import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author wangdi0410@gmail.com
 * @Date Jan 13, 2012
 */
public class NumberUtilTest {

	
	@Test
	public void isNegative() {
		assertTrue( NumberUtil.isNegative( -1 ) );
		assertTrue( NumberUtil.isNegative( -0.1 ) );
		assertTrue( !NumberUtil.isNegative( 0 ) );
		assertTrue( !NumberUtil.isNegative( 1 ) );
		assertTrue( !NumberUtil.isNegative( 1.2 ) );
	}
	
	@Test
	public void isNegativeArray() {
		assertTrue( NumberUtil.isNegative( -1,-2 ) );
		assertTrue( NumberUtil.isNegative( -0.1,2 ) );
		assertTrue( !NumberUtil.isNegative( 0,1 ) );
		assertTrue( !NumberUtil.isNegative( 1,2 ) );
		assertTrue( !NumberUtil.isNegative( 1.2,2.6 ) );
	}
	
	
	
	
	@Test
	public void isInInterval() {
		
		assertTrue( !NumberUtil.isInInterval( "", "41521" ) );
		assertTrue( !NumberUtil.isInInterval( null, "41521" ) );
		assertTrue( !NumberUtil.isInInterval( null, "wangdi0410" ) );
		assertTrue( !NumberUtil.isInInterval( null, null ) );
		assertTrue( !NumberUtil.isInInterval( null, "" ) );
		
		assertTrue( !NumberUtil.isInInterval( "(2,5)", "1" ) );
		assertTrue( !NumberUtil.isInInterval( "(2,5)", "2" ) );
		
		assertTrue( NumberUtil.isInInterval( "(2,5)", "3" ) );
		assertTrue( NumberUtil.isInInterval( "(2,5]", "3" ) );
		
		assertTrue( NumberUtil.isInInterval( "(2,5]", "5" ) );
		assertTrue( !NumberUtil.isInInterval( "(2,5]", "6" ) );
		
		
		assertTrue( !NumberUtil.isInInterval( "(2,5)", "1.3" ) );
		assertTrue( NumberUtil.isInInterval( "(2,5)", "2.3" ) );
		
		assertTrue( NumberUtil.isInInterval( "(2,5)", "3.3" ) );
		assertTrue( NumberUtil.isInInterval( "(2,5]", "3.3" ) );
		
		assertTrue( !NumberUtil.isInInterval( "(2,5]", "5.3" ) );
		assertTrue( !NumberUtil.isInInterval( "(2,5]", "6.3" ) );
		
	}

}
