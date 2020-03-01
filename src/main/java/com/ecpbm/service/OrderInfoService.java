package com.ecpbm.service;

import java.util.List;
import java.util.Map;
import com.ecpbm.pojo.*;

public interface OrderInfoService {
	// ��ҳ��ʾ����
	List<OrderInfo> findOrderInfo(OrderInfo orderInfo, Pager pager);

	// ��������
	Integer count(Map<String, Object> params);

	// ��Ӷ�������
	public int addOrderInfo(OrderInfo oi);

	// ��Ӷ�����ϸ
	public int addOrderDetail(OrderDetail od);

	// ���ݶ�����Ż�ȡ������Ϣ
	public OrderInfo getOrderInfoById(int id);

	// ���Ӷ�����Ż�ȡ������ϸ��Ϣ
	public List<OrderDetail> getOrderDetailByOid(int oid);

	// ɾ������
	public int deleteOrder(int id);
}
