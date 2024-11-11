package com.phms.pojo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * BaseBean用于分页/排序
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = -4714709574354070550L;

	private Integer page;// easyUi/layui
	private Integer pageSize;// easyUi
	private String sort;// easyUi
	private String order;// easyUi
	private Integer rows;// easyUi
	private Integer limit;// layui
	private Integer fromNum;// 权限系统

	public Integer getFromNum() {
		return fromNum;
	}

	public void setFromNum(Integer fromNum) {
		this.fromNum = fromNum;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}