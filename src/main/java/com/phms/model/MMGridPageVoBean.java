package com.phms.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangjian mmGrid的表格参数bean
 * @param <T>
 */
public class MMGridPageVoBean<T> implements Serializable {

	private static final long serialVersionUID = 5033466102339979707L;
	private long total;// 总条数
	private List<T> rows;// 展示记录
	private Object other;// 其他

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}