package com.ecpbm.pojo;

import java.util.List;

public class TreeNode {
	private int id;  // �ڵ�id
	private String text;  // �ڵ�����
	private int fid;   // ���ڵ�id
	private List<TreeNode> children; // �������ӽڵ�

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
