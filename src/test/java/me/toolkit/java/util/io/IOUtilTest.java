package me.toolkit.java.util.io;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Test of IOUtil
 * @author wangdi0410@gmail.com
 */
public class IOUtilTest extends TestCase {

	@Test
	public void testConvertStringAndInputStream() throws IOException{
		
		String str = "我是一个中文\n";
		ByteArrayInputStream inputstream = new ByteArrayInputStream( str.getBytes( "GBK" ) );

		try {
			assertEquals( str, IOUtil.convertInputStream2String( inputstream, "GBK" ) );
		} catch ( IOException e ) {
		}
	}
	
	
	
	
	
	
	
	
}
