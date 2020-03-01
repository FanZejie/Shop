package com.ecpbm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ecpbm.pojo.Type;

public interface TypeDao {
	// ��ѯ������Ʒ����
	@Select("select * from type")
	public List<Type> selectAll();

	// �������ͱ�Ų�ѯ��Ʒ����
	@Select("select * from type where id = #{id}")
	Type selectById(int id);
	
	// �����Ʒ����
	@Insert("insert into type(name) values(#{name})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(Type type);
	
	// ������Ʒ����
	@Update("update type set name = #{name} where  id = #{id}")
	public int update(Type type);	
}
