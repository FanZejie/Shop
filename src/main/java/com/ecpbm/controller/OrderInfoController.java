package com.ecpbm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecpbm.pojo.OrderDetail;
import com.ecpbm.pojo.OrderInfo;
import com.ecpbm.pojo.Pager;
import com.ecpbm.service.OrderInfoService;
import com.ecpbm.service.ProductInfoService;
import com.ecpbm.service.UserInfoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonParseException;

@Controller
@RequestMapping("/orderinfo")
public class OrderInfoController {
	@Autowired
	OrderInfoService orderInfoService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	ProductInfoService productInfoService;

	// ��ҳ��ʾ
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(Integer page, Integer rows, OrderInfo orderInfo) {
		// ��ʼ��һ����ҳ�����pager
		Pager pager = new Pager();
		pager.setCurPage(page);
		pager.setPerPageRows(rows);
		// ��������params�����ڷ�װ��ѯ����
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderInfo", orderInfo);
		// ��ȡ���������Ķ�������
		int totalCount = orderInfoService.count(params);
		// ��ȡ���������Ķ����б�
		List<OrderInfo> orderinfos = orderInfoService.findOrderInfo(orderInfo, pager);
		// ����result���󣬱����ѯ�������
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("total", totalCount);
		result.put("rows", orderinfos);
		return result;
	}

	// ���涩��
	@ResponseBody
	@RequestMapping(value = "/commitOrder")
	public String commitOrder(String inserted, String orderinfo)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			// ����ObjectMapper����,ʵ��JavaBean��JSON��ת��
			ObjectMapper mapper = new ObjectMapper();
			// ��������ʱ������JSON�ַ����д��ڵ�Java����ʵ��û�е�����
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			// ��json�ַ���orderinfoת����JavaBean���󣨶�����Ϣ��
			OrderInfo oi = mapper.readValue(orderinfo, OrderInfo[].class)[0];
			// ���涩����Ϣ
			orderInfoService.addOrderInfo(oi);
			// ��json�ַ���ת����List<OrderDetail>���ϣ�������ϸ��Ϣ��
			List<OrderDetail> odList = mapper.readValue(inserted, new TypeReference<ArrayList<OrderDetail>>() {
			});
			// ��������ϸ������������Ը�ֵ
			for (OrderDetail od : odList) {
				od.setOid(oi.getId());
				// ���涩����ϸ
				orderInfoService.addOrderDetail(od);
			}
			return "success";
		} catch (Exception e) {
			return "failure";
		}
	}

	// ���ݶ���id�Ż�ȡҪ�鿴�Ķ�������, �ٷ��ض�����ϸҳ
	@RequestMapping("/getOrderInfo")
	public String getOrderInfo(String oid, Model model) {
		OrderInfo oi = orderInfoService.getOrderInfoById(Integer.parseInt(oid));
		model.addAttribute("oi", oi);
		return "orderdetail";
	}

	// ���ݶ���id�Ż�ȡ������ϸ�б�
	@RequestMapping("/getOrderDetails")
	@ResponseBody
	public List<OrderDetail> getOrderDetails(String oid) {
		List<OrderDetail> ods = orderInfoService.getOrderDetailByOid(Integer.parseInt(oid));
		for (OrderDetail od : ods) {
			// od.setPid(od.getPi().getId());
			od.setPrice(od.getPi().getPrice());
			od.setTotalprice(od.getPi().getPrice() * od.getNum());
		}
		return ods;
	}

	// ɾ������
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", produces = "text/html;charset=UTF-8")
	public String deleteOrder(String oids) {
		String str = "";
		try {
			oids = oids.substring(0, oids.length() - 1);
			String[] ids = oids.split(",");
			for (String id : ids) {
				orderInfoService.deleteOrder(Integer.parseInt(id));
			}
			str = "{\"success\":\"true\",\"message\":\"ɾ���ɹ���\"}";
		} catch (Exception e) {
			str = "{\"success\":\"false\",\"message\":\"ɾ��ʧ�ܣ�\"}";
		}
		return str;
	}

}
