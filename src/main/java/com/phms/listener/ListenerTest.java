package com.phms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * class name:ListenerTest <BR>
 * class description: 监听器实现类 <BR>
 */
public class ListenerTest implements ServletContextListener {
	/** 日志记录 */
	private final Logger logger = LoggerFactory.getLogger(ListenerTest.class);

	/**
	 * @Override
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 *      <BR>
	 *      Method name: contextInitialized <BR>
	 *      Description: 监听器初始化 <BR>
	 *      Remark: <BR>
	 * @param sce <BR>
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("-------监听器初始化-------");
	}

	/**
	 * @Override
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 *      <BR>
	 *      Method name: contextDestroyed <BR>
	 *      Description: 销毁监听器 <BR>
	 *      Remark: <BR>
	 * @param sce <BR>
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("-------销毁监听器-------");
	}
}
