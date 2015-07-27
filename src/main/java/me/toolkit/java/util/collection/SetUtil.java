package me.toolkit.java.util.collection;

import me.toolkit.java.constant.SymbolConstant;
import me.toolkit.java.util.StringUtil;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 类说明: List相关工具类
 * @author wangdi0410@gmail.com
 */
public class SetUtil {

	
	public static <T> Set< T > convertToSet( List< T > collection ) {
		if ( CollectionUtil.isBlank( collection ) )
			return CollectionUtil.emptySet();
		return new HashSet< T >( collection );
	}
	
	
	
	
	/**
	 * parse the string to Set
	 * @param @param originalStr abc, def,helloword,myname
	 * @param @param splitStr ,
	 * @return Set<String>
	 */
	public static Set< String > parseSet( String originalStr, String splitStr ) {
		Set< String > set = new LinkedHashSet< String >();
		
		if ( StringUtil.isBlank( originalStr ) || StringUtil.isBlank( splitStr ) )
			return set;
		
		for( String str : originalStr.split( splitStr ) ){
			if( StringUtil.isBlank( str ) )
				continue;
			set.add( StringUtil.trimToEmpty( str ) );
		}
		return set;
	}
	
	/**
	 * parse the string to Set
	 * @param @param originalStr abc, def,helloword,myname
	 * @return Set<String>
	 */
	public static Set< String > parseSet( String originalStr ) {
		return parseSet( originalStr, SymbolConstant.COMMA );
	}

}