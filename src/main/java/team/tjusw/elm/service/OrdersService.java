package team.tjusw.elm.service;

import java.util.List;

import team.tjusw.elm.po.Orders;

public interface OrdersService {
	public int createOrders(Orders orders);

	public Orders getOrdersById(Integer orderId);

	public List<Orders> listOrdersByUserId(String userId);
	
	public Integer payByVirtualWallet(String userId, Integer orderId,int pointAmount);
}
