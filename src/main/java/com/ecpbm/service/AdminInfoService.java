package com.ecpbm.service;

import com.ecpbm.pojo.AdminInfo;

public interface AdminInfoService {
	// ��¼��֤
	public AdminInfo login(AdminInfo ai);

	// ���ݹ���Ա��Ż�ȡ����Ա���󼰹����Ĺ���Ȩ��
	public AdminInfo getAdminInfoAndFunctions(Integer id);
}
