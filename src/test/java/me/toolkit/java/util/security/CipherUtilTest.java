package me.toolkit.java.util.security;


import me.toolkit.java.constant.EmptyObjectConstant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author wangdi0410@gmail.com
 */
public class CipherUtilTest {
	
	static String str1 = "王迪是天才";
	static String str2 = "王迪是1个天才";
	static String str3 = "wangdi is a genius";
	
	@Test
	public void testEncryptAndDecrypt() throws Exception {
		
		for( int i = 0; i < 1000; i++ ){
			assertEquals( str1, CipherUtil.decrypt( CipherUtil.encrypt( str1 ) ) );
			assertEquals( str2, CipherUtil.decrypt( CipherUtil.encrypt( str2 ) ) );
			assertEquals( str3, CipherUtil.decrypt( CipherUtil.encrypt( str3 ) ) );
			assertEquals( EmptyObjectConstant.EMPTY_STRING, CipherUtil.decrypt( CipherUtil.encrypt( null ) ) );
		}
	}

}
