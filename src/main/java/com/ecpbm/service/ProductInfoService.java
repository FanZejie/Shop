package com.ecpbm.service;

import java.util.List;
import java.util.Map;

import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;

public interface ProductInfoService {
	// ��ҳ��ʾ��Ʒ
	List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager);

	// ��Ʒ����
	Integer count(Map<String, Object> params);

	// �����Ʒ
	public void addProductInfo(ProductInfo pi);
	
	// �޸���Ʒ
	public void modifyProductInfo(ProductInfo pi);
	
	// ������Ʒ״̬
	void modifyStatus(String ids, int flag);

	// ��ȡ������Ʒ�б�
	List<ProductInfo> getOnSaleProduct(); 
	
	// ���ݲ�Ʒid��ȡ��Ʒ����
	ProductInfo getProductInfoById(int id);
}
