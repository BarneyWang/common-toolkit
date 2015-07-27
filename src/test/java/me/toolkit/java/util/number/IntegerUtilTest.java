package me.toolkit.java.util.number;


import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author wangdi0410@gmail.com
 * @Date Jan 13, 2012
 */
public class IntegerUtilTest {

	@Test
	public void maxIfTooBig() {
		assertTrue( -5 == IntegerUtil.maxIfTooBig( -5, 25 ) );
		assertTrue( 0 == IntegerUtil.maxIfTooBig( 0, 25 ) );
		assertTrue( 12 == IntegerUtil.maxIfTooBig( 12, 25 ) );
		assertTrue( 25 == IntegerUtil.maxIfTooBig( 25, 25 ) );
		assertTrue( 25 == IntegerUtil.maxIfTooBig( 26, 25 ) );
		assertTrue( 0 == IntegerUtil.maxIfTooBig( 0, 0 ) );
		assertTrue( -5 == IntegerUtil.maxIfTooBig( 0, -5 ) );
	}

}
