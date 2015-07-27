package me.toolkit.java.filter;

import me.toolkit.java.util.StringUtil;
import me.toolkit.java.util.system.SystemUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Description: Setting variable for every request.
 * @author wangdi0410 / wangdi0410@gmail.com
 * @Date Jul 29, 2012
 */
public class BaseRequestVariableFilter implements Filter {

	public void destroy() {
	}

	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

		HttpServletRequest req = ( HttpServletRequest ) request;

		StringBuffer requestURL = req.getRequestURL();
		String basePath = requestURL.substring( 0, requestURL.indexOf( req.getContextPath() ) ) + req.getContextPath();
		String hostName = SystemUtil.getHostName();
		request.setAttribute( "baseUrl", basePath );
		request.setAttribute( "basePath", basePath );
		request.setAttribute( "hostName", StringUtil.trimToEmpty( hostName ) );
		setFlagIsFirerox( req );
		chain.doFilter( request, response );
	}

	private void setFlagIsFirerox( HttpServletRequest request ) {
		String s = request.getHeader( "user-agent" );
		boolean isFirefox = false;
		if ( null != s && s.toLowerCase().indexOf( "firefox" ) > 0 ) {
			isFirefox = true;
		}
		request.setAttribute( "isFirefox", isFirefox );
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {
	}
}