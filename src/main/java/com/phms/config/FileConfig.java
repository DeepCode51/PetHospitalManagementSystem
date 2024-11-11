package com.phms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件上传
 */
@Configuration
@PropertySource(value = "classpath:config.properties")
public class FileConfig implements WebMvcConfigurer {

	@Value("${file.location}")
	private String path;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
		 * @Description: 对文件的路径进行配置,创建一个虚拟路径/file/** ，即只要在<img src=
		 *               "/file/images/20180522/9aa64b2b-a558-421e-929c-537ff0aecdba.jpg"
		 *               />便可以直接引用图片 这是图片的物理路径 "file:/+本地图片的地址"
		 *
		 */
		// 读取配置文件中的上传路径
		registry.addResourceHandler("/file/**").addResourceLocations(path);
	}
}
