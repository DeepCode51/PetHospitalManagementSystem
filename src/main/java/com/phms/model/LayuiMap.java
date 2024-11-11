package com.phms.model;

import java.util.HashMap;

/**
 * class name: LayuiMap返回模型 <BR>
 * class description: please write your description <BR>
 */
public class LayuiMap extends HashMap<String, Object> {

	/**
	 * define a field serialVersionUID which type is long
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method name: success <BR>
	 * Description: 返回成功 <BR>
	 * 
	 * @return LayuiMap<BR>
	 */
	public LayuiMap success() {
		this.put("code", 0);
		this.put("src", "");
		return this;
	}

	/**
	 * Method name: fail <BR>
	 * Description: 返回失败 <BR>
	 * 
	 * @return LayuiMap<BR>
	 */
	public LayuiMap fail() {
		this.put("code", -1);
		return this;
	}

	/**
	 * Method name: message <BR>
	 * Description: 返回信息 <BR>
	 * 
	 * @param message
	 * @return LayuiMap<BR>
	 */
	public LayuiMap message(Object message) {
		this.put("message", message);
		return this;
	}

	/**
	 * 返回数据
	 */
	public LayuiMap data(Object message) {
		this.put("data", message);
		return this;
	}

}
