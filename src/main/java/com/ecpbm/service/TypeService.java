package com.ecpbm.service;

import java.util.List;

import com.ecpbm.pojo.Type;

public interface TypeService {
	// ��ȡ��ʾ��Ʒ����
	public List<Type> getAll();
	
	// �����Ʒ����
	public int addType(Type type); 
	
	// ������Ʒ����
	public void updateType(Type type);
}
