package com.ecpbm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;
import com.ecpbm.service.ProductInfoService;

@Controller
@RequestMapping("/productinfo")
public class ProductInfoController {
	@Autowired
	ProductInfoService productInfoService;

	// ��̨��Ʒ�б��ҳ��ʾ
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(Integer page, Integer rows, ProductInfo productInfo) {
		// ��ʼ����ҳ�����pager
		Pager pager = new Pager();
		System.out.println(page);
		System.out.println(rows);
		pager.setCurPage(page);
		pager.setPerPageRows(rows);
		// ����params���󣬷�װ��ѯ����
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productInfo", productInfo);
		// ��ȡ������������Ʒ����
		int totalCount = productInfoService.count(params);
		// ��ȡ������������Ʒ�б�
		List<ProductInfo> productinfos = productInfoService.findProductInfo(productInfo, pager);
		// ����result���󣬱����ѯ�������
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("total", totalCount);
		result.put("rows", productinfos);
		// �������JSON��ʽ���͵�ǰ�˿�����
		return result;
	}

	// �����Ʒ
	@RequestMapping(value = "/addProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		// ��������upload�ļ�������·��
		String path = request.getSession().getServletContext().getRealPath("product_images");
		// ��ȡ�ļ���
		String fileName = file.getOriginalFilename();
		// ʵ����һ��File���󣬱�ʾĿ���ļ���������·����
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			// ���ϴ��ļ�д����������ָ�����ļ�
			file.transferTo(targetFile);
			pi.setPic(fileName);
			productInfoService.addProductInfo(pi);
			return "{\"success\":\"true\",\"message\":\"��Ʒ��ӳɹ�\"}";
		} catch (Exception e) {
			return "{\"success\":\"false\",\"message\":\"��Ʒ���ʧ��\"}";
		}
	}

	// �޸���Ʒ
	@RequestMapping(value = "/updateProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		// ��������upload�ļ�������·��
		String path = request.getSession().getServletContext().getRealPath("product_images");
		// ��ȡ�ļ���
		String fileName = file.getOriginalFilename();
		// ʵ����һ��File���󣬱�ʾĿ���ļ���������·����
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			// ���ϴ��ļ�д����������ָ�����ļ�
			file.transferTo(targetFile);
			pi.setPic(fileName);
			productInfoService.modifyProductInfo(pi);
			return "{\"success\":\"true\",\"message\":\"��Ʒ�޸ĳɹ�\"}";
		} catch (Exception e) {
			return "{\"success\":\"false\",\"message\":\"��Ʒ�޸�ʧ��\"}";
		}
	}

	// ��Ʒ�¼�(ɾ����Ʒ)
	@RequestMapping(value = "/deleteProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteProduct(@RequestParam(value = "id") String id, @RequestParam(value = "flag") String flag) {
		String str = "";
		try {
			productInfoService.modifyStatus(id.substring(0, id.length() - 1), Integer.parseInt(flag));
			str = "{\"success\":\"true\",\"message\":\"ɾ���ɹ�\"}";
		} catch (Exception e) {
			str = "{\"success\":\"false\",\"message\":\"ɾ��ʧ��\"}";
		}
		return str;
	}
	
	// ��ȡ������Ʒ�б�
	@ResponseBody
	@RequestMapping("/getOnSaleProduct")
	public List<ProductInfo> getOnSaleProduct() {
		List<ProductInfo> piList = productInfoService.getOnSaleProduct();
		return piList;
	}
	
	// ������Ʒid��ȡ��Ʒ����
	@ResponseBody
	@RequestMapping("/getPriceById")
	public String getPriceById(@RequestParam(value = "pid") String pid) {
		if (pid != null && !"".equals(pid)) {
			ProductInfo pi = productInfoService.getProductInfoById(Integer.parseInt(pid));
			return pi.getPrice() + "";
		}else{
			return "";
		}
	}

}
