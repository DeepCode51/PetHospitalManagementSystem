package com.phms.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * class name:DruidConfiguration <BR>
 * class description: Druid数据库配置 <BR>
 */
@Configuration
public class DruidConfiguration {
	/**
	 * Method name: dataSource <BR>
	 * Description: 加载application配置文件 <BR>
	 * Remark: <BR>
	 * 
	 * @return DruidDataSource<BR>
	 */
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean(initMethod = "init", destroyMethod = "close")
	public DruidDataSource dataSource() {
		DruidDataSource ds = new DruidDataSource();
		ds.setProxyFilters(Arrays.asList(statFilter()));
		return ds;
	}

	/**
	 * Method name: statFilter <BR>
	 * Description: 用于统计监控信息 <BR>
	 * Remark: <BR>
	 * 
	 * @return Filter<BR>
	 */
	@Bean
	public Filter statFilter() {
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(5000);
		filter.setLogSlowSql(true);
		filter.setMergeSql(true);
		return filter;
	}
}
