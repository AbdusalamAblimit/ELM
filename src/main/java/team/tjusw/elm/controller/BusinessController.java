package team.tjusw.elm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.tjusw.elm.po.Business;
import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.service.BusinessService;

//@CrossOrigin("*") 
@RestController
@RequestMapping("/businesses")
public class BusinessController {
	@Autowired
	private BusinessService businessService;

	@GetMapping("/order-type/{orderTypeId}")
	public CommonResult<List<Business>> listBusinessByOrderTypeId(@PathVariable("orderTypeId") Integer orderTypeId) throws Exception {
		
		List<Business> ret = businessService.listBusinessByOrderTypeId(orderTypeId);
		
		if(ret==null)
			return new CommonResult<List<Business>>(505,"商家服务内部错误.",ret); 
		else if(ret.size()==0)
			return new CommonResult<List<Business>>(404,"没有此类商家.",ret); 
		else
			return new CommonResult<List<Business>>(200,"成功筛选商家.",ret);
	}

	@GetMapping("/{businessId}")
	public CommonResult<Business> getBusinessById(@PathVariable("businessId") Integer businessId) throws Exception {
		
		Business ret = businessService.getBusinessById(businessId);
		if(ret==null)
			return new CommonResult<Business>(404,"没有此商家",null); 
		else
			return new CommonResult<Business>(200,"成功查询商家.",ret);
	}

}
