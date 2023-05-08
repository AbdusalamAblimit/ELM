package team.tjusw.elm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.tjusw.elm.feign.BusinessClient;
import team.tjusw.elm.feign.CartClient;
import team.tjusw.elm.feign.PointClient;
import team.tjusw.elm.feign.VirtualWalletClient;
import team.tjusw.elm.lock.RedissonConfig;
import team.tjusw.elm.mapper.OrderDetailetMapper;
import team.tjusw.elm.mapper.OrdersMapper;
import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.po.OrderDetailet;
import team.tjusw.elm.po.Orders;
import team.tjusw.elm.service.OrdersService;
import team.tjusw.elm.util.CommonUtil;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderDetailetMapper orderDetailetMapper;

	@Autowired
	CartClient cartClient;

	@Override
	@Transactional
	public int createOrders(Orders orders) {
		// 1、查询当前用户购物车中当前商家的所有食品
		CommonResult ret = cartClient.listCart(orders.getUserId(), orders.getBusinessId());
		List<LinkedHashMap<?, ?>> cartList = (List<LinkedHashMap<?, ?>>) cartClient
				.listCart(orders.getUserId(), orders.getBusinessId()).getResult();

		// 2、创建订单（返回生成的订单编号）
		orders.setOrderDate(CommonUtil.getCurrentDate());
		ordersMapper.saveOrders(orders);
		int orderId = orders.getOrderId();

		// 3、批量添加订单明细
		List<OrderDetailet> list = new ArrayList<>();
		for (LinkedHashMap<?, ?> c : cartList) {
			OrderDetailet od = new OrderDetailet();
			od.setOrderId(orderId);
			od.setFoodId((Integer) c.get("foodId"));
			od.setQuantity((Integer) c.get("quantity"));
			list.add(od);
		}
		orderDetailetMapper.saveOrderDetailetBatch(list);

		// 4、从购物车表中删除相关食品信息
		cartClient.removeCart(orders.getUserId(), orders.getBusinessId());

		return orderId;
	}

	@Override
	public List<Orders> listOrdersByUserId(String userId) {
		List<Orders> orders = ordersMapper.listOrdersByUserId(userId);
		for (Orders order : orders) {
			order.setList(orderDetailetMapper.listOrderDetailetByOrderId(order.getOrderId()));
		}
		return orders;
	}

	@Override
	public Orders getOrdersById(Integer orderId) {
		Orders order = ordersMapper.getOrdersById(orderId);
		if (order == null)
			return null;
		order.setList(orderDetailetMapper.listOrderDetailetByOrderId(order.getOrderId()));
		return order;
	}

	@Autowired
	VirtualWalletClient virtualWalletClient;

	@Autowired
	PointClient pointClient;

	@Autowired
	BusinessClient businessClient;

	private final ConcurrentHashMap<Integer, Object> orderLocks = new ConcurrentHashMap<>();
	
	 @Autowired
	 private RedissonConfig  redissonConfig;
	 
	 public Integer payByVirtualWallet(String userId, Integer orderId, int pointAmount) {
		    // 使用Redis分布式锁
		    RLock orderLock = redissonConfig.redissonClient.getLock("order-" + orderId);
		    orderLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS); // 设置锁的过期时间为10秒
		    try {
		        Orders order = ordersMapper.getOrdersById(orderId);
		        if (order.getOrderState() == 1)
		            return 3;
		        String businessUserId = (String) ((LinkedHashMap<?, ?>) businessClient
		                .getBusinessById(order.getBusinessId()).getResult()).get("userId");

		        BigDecimal fromBalance = virtualWalletClient.getBalanceByUserId(userId).getResult();
		        Integer pointBalance = pointClient.getBalanceByUserId(userId).getResult();

		        if (fromBalance.compareTo(order.getOrderTotal()) < 0)
		            return 1;
		        if (pointBalance < pointAmount)
		            return 2;

		        int ret = 1;

		        // test
		        BigDecimal deductedAmount = pointClient.getDeductedAmount(orderId, pointAmount).getResult();

		        BigDecimal amount = order.getOrderTotal().subtract(deductedAmount);

		        if (virtualWalletClient.transfer(userId, businessUserId, amount).getCode() != 200)
		            return 0;
		        pointClient.earnPointByOrder(orderId);
		        ordersMapper.finishOrder(orderId);
		        pointClient.deductOrder(orderId, pointAmount);
		        return ret > 0 ? 200 : 0;
		    } finally {
		        orderLock.unlock();
		    }
		}
}
