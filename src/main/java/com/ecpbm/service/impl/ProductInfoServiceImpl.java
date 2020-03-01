package com.ecpbm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.dao.ProductInfoDao;
import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;
import com.ecpbm.service.ProductInfoService;

@Service("productInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	ProductInfoDao productInfoDao;

	@Override
	public List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager) {
		// ��������params
		Map<String, Object> params = new HashMap<String, Object>();
		// ����װ�в�ѯ������productInfo�������params
		params.put("productInfo", productInfo);
		// ��������������Ʒ����
		int recordCount = productInfoDao.count(params);
		// ��pager��������rowCount����ֵ(��¼����)
		pager.setRowCount(recordCount);
		if (recordCount > 0) {
			// ��page�������params
			params.put("pager", pager);
		}
		// ��ҳ��ȡ��Ʒ��Ϣ
		return productInfoDao.selectByPage(params);
	}

	@Override
	public Integer count(Map<String, Object> params) {
		return productInfoDao.count(params);
	}

	@Override
	public void addProductInfo(ProductInfo pi) {
		productInfoDao.save(pi);
	}

	@Override
	public void modifyProductInfo(ProductInfo pi) {
		productInfoDao.edit(pi);
	}

	@Override
	public void modifyStatus(String ids, int flag) {
		productInfoDao.updateState(ids, flag);
	}

	@Override
	public List<ProductInfo> getOnSaleProduct() {
		return productInfoDao.getOnSaleProduct();
	}

	@Override
	public ProductInfo getProductInfoById(int id) {
		return productInfoDao.getProductInfoById(id);
	}
}
