package com.phms.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * class name:MyBatisConfiguration <BR>
 * class description: MyBatis数据库配置 <BR>
 * Remark: <BR>
 */
@Configuration
public class MyBatisConfiguration {
	/**
	 * Method name: sqlSessionFactory <BR>
	 * Description: 当容器里没有指定的 Bean 的情况下创建该对象 <BR>
	 * Remark: <BR>
	 * 
	 * @param dataSource
	 * @return SqlSessionFactoryBean<BR>
	 */
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 设置数据源
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 设置mybatis的主配置文件
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
		// 设置别名包
		sqlSessionFactoryBean.setTypeAliasesPackage("com.phms.mapper");
		return sqlSessionFactoryBean;
	}

	/**
	 * Method name: mapperScannerConfigurer <BR>
	 * Description: 当 SqlSessionFactoryBean 实例存在时创建对象 <BR>
	 * Remark: <BR>
	 * 
	 * @return MapperScannerConfigurer<BR>
	 */
	@Bean
	@ConditionalOnBean(SqlSessionFactoryBean.class)
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.phms.mapper");
		return mapperScannerConfigurer;
	}
}