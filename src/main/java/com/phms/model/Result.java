package com.phms.model;
/**
* @author zwg
* @version 创建时间：2019年1月25日 上午9:54:04
* @ClassName DemandItem
* @Description 需求列表返回结果集
*/

import java.io.Serializable;
import java.util.List;

public class Result<T> implements Serializable {

	/**
	 * define a field serialVersionUID which type is long&#13;
	 */
	private static final long serialVersionUID = 1L;

	private int page;// 起始页
	private int limit;// 页数大小
	private int count;// 数据数量
	private String code;// 代码
	private String msg;// 信息
	private List<T> data;// 返回数据
	private T example;// 任何类型条件

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public Result(int page, int limit, int count, String code, String msg, List<T> data, T example) {
		this.page = page;
		this.limit = limit;
		this.count = count;
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.example = example;
	}

	public Result() {
	}

	@Override
	public String toString() {
		return "Result [page=" + page + ", limit=" + limit + ", count=" + count + ", code=" + code + ", msg=" + msg
				+ ", data=" + data + ", example=" + example + "]";
	}

}
