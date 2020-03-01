package com.ecpbm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ecpbm.pojo.Functions;

public interface FunctionDao {
	// ���ݹ���Աid,��ȡ����Ȩ��
	@Select("select * from functions where id in (select fid from powers where aid = #{aid} )")
	public List<Functions> selectByAdminId(Integer aid);
}
