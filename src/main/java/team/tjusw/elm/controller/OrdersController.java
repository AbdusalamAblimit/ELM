package team.tjusw.elm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.po.Orders;
import team.tjusw.elm.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;

	@GetMapping("/user/{userId}")
	public CommonResult<List<Orders>> listOrdersByUserId(@PathVariable("userId") String userId) throws Exception {
		List<Orders> ret = ordersService.listOrdersByUserId(userId);
		if(ret==null)
			return new CommonResult<List<Orders>>(505,"获取用户订单列表失败.",null);
		else
			return new CommonResult<List<Orders>>(200,"成功获取用户订单列表.",ret);
	}
	

	@PostMapping(value = {"","/"})
	public CommonResult<Integer> createOrders(Orders orders) throws Exception {
		int ret = ordersService.createOrders(orders);
		if(ret==0)
			return new CommonResult<Integer>(505,"创建订单时出现错误.",null);
		else
			return new CommonResult<Integer>(201,"成功创建订单.",ret);
	}

	@GetMapping("/{orderId}")
	public CommonResult<Orders> getOrdersById(@PathVariable("orderId") Integer orderId) throws Exception {
		Orders order = ordersService.getOrdersById(orderId);
		if(order == null)
			return new CommonResult<Orders>(404,"没有找到orderId为"+orderId+"的订单.",null);
		else
			return new CommonResult<Orders>(200,"成功获取订单.",order);
	}
	
	@PostMapping("/{orderId}/pay/virtual-wallet")
	public CommonResult<Integer> payByVirtualWallet(@PathVariable("orderId") Integer orderId,Integer pointAmount)
	{
		if(pointAmount==null)
			pointAmount = 0;
		Orders order =  ordersService.getOrdersById(orderId);
		Integer ret = ordersService.payByVirtualWallet(order.getUserId(), orderId,pointAmount);
		if(ret==1)
			return new CommonResult<Integer>(402,"用户虚拟钱包余额不足.",ret);
		else if(ret==2)
			return new CommonResult<Integer>(402,"用户积分不足.",ret);
		else if(ret==3)
			return new CommonResult<Integer>(409,"订单早已是完成状态，无法付款.",ret);
		else if(ret==200)
			return new CommonResult<Integer>(200,"付款成功.",ret);
		else
			return new CommonResult<Integer>(505,"未知错误，无法确定订单是否完成.",ret);
		
	}
}
