package com.phms.pojo;

public class UserParameter extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String userName;
	private Integer post;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}
}
