package com.phms.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * class name:PropertyUtil <BR>
 */
public class PropertyUtil {

	/** 读取src下sender.properties配置文件内容 */
	private final static String CONFIGURE_LOCATION = "config.properties";
	/** 接收Properties内容 */
	private final static Properties property = new Properties();

	/**
	 * Method name: getConfigureProperties <BR>
	 * Description: 读取src下第三方配置文件thirdConfig.properties配置文件内容 <BR>
	 * Remark: <BR>
	 * 
	 * @param propertyName
	 * @return String<BR>
	 */
	public static String getConfigureProperties(String propertyName) {
		try {
			InputStreamReader in = new InputStreamReader(
					PropertyUtil.class.getClassLoader().getResourceAsStream(CONFIGURE_LOCATION), "UTF-8");
			property.load(in);
			String result = property.getProperty(propertyName);
			if (null != result && !"".equalsIgnoreCase(result)) {
				return result.trim();
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}