package com.phms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * class name:TimeFilter <BR>
 * class description: 过滤器实现类<BR>
 */

public class TimeFilter implements Filter {

	/** 日志记录 */
	private final Logger logger = LoggerFactory.getLogger(TimeFilter.class);

	/**
	 * @Override
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig) <BR>
	 *      Method name: init <BR>
	 *      Description: 初始化过滤器<BR>
	 *      Remark: <BR>
	 * @param filterConfig
	 * @throws ServletException <BR>
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("=======初始化过滤器=========");
	}

	/**
	 * @Override
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain) <BR>
	 *      Method name: doFilter <BR>
	 *      Description: 处理过滤 <BR>
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException <BR>
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// long start = System.currentTimeMillis();
		filterChain.doFilter(request,  	response);
		// logger.info("filter 耗时：{}",(System.currentTimeMillis() - start));
	}

	/**
	 * @Override
	 * @see javax.servlet.Filter#destroy() <BR>
	 *      Method name: destroy <BR>
	 *      Description: 销毁过滤器 <BR>
	 *      Remark: <BR>
	 *      <BR>
	 */
	@Override
	public void destroy() {
		logger.info("=======销毁过滤器=========");
	}
}
