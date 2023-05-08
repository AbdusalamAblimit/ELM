package team.tjusw.elm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.po.DeliveryAddress;
import team.tjusw.elm.service.DeliveryAddressService;

//@CrossOrigin("*")
@RestController
@RequestMapping("/delivery-addresses")
public class DeliveryAddressController {

	@Autowired
	private DeliveryAddressService deliveryAddressService;

	@GetMapping("/user/{userId}")
	public CommonResult<List<DeliveryAddress>> listDeliveryAddressByUserId(
				@PathVariable("userId") String userId
			)throws Exception {
		
		List<DeliveryAddress> ret = deliveryAddressService.listDeliveryAddressByUserId(userId);
		if (ret == null)
			return new CommonResult<List<DeliveryAddress>>(505, "配送地址服务内部错误.", ret);
		else if (ret.size() == 0)
			return new CommonResult<List<DeliveryAddress>>(404, "没有属于此用户的任何地址.", ret);
		else
			return new CommonResult<List<DeliveryAddress>>(200, "成功获取用户地址列表.", ret);
	}
	
	

	@GetMapping("{daId}")
	public CommonResult<DeliveryAddress> getDeliveryAddressById(
			@PathVariable("daId") Integer daId
			) throws Exception {
		
		DeliveryAddress ret =  deliveryAddressService.getDeliveryAddressById(daId);
		if(ret==null)
			return new CommonResult<DeliveryAddress>(404,"没有此地址.",null); 
		else
			return new CommonResult<DeliveryAddress>(200,"成功查询地址.",ret);
	}
	
	

	@PostMapping
	public CommonResult<Integer> saveDeliveryAddress(DeliveryAddress deliveryAddress) throws Exception {
		
		int ret = deliveryAddressService.saveDeliveryAddress(deliveryAddress);
		if(ret==0)
			return new CommonResult<Integer>(505,"未知错误，未能新增地址.",ret); 
		else
			return new CommonResult<Integer>(201,"成功新增地址.",ret);
	}

	
	@PutMapping("/{daId}")
	public CommonResult<Integer> updateDeliveryAddress(@PathVariable("daId") Integer daId,DeliveryAddress deliveryAddress) throws Exception {
		deliveryAddress.setDaId(daId);
		int ret = deliveryAddressService.updateDeliveryAddress(deliveryAddress);
		if(ret==0)
			return new CommonResult<Integer>(505,"未知错误，未能更新地址.",ret); 
		else
			return new CommonResult<Integer>(200,"成功更新地址.",ret);
	}

	
	@DeleteMapping("/{daId}")
	public CommonResult<Integer> removeDeliveryAddress(
			@PathVariable("daId") Integer daId
			) throws Exception {
		int ret = deliveryAddressService.removeDeliveryAddress(daId);
		if(ret==0)
			return new CommonResult<Integer>(505,"未知错误，未能删除地址.",ret); 
		else
			return new CommonResult<Integer>(200,"成功删除地址.",ret);
	}
}
