package team.tjusw.elm.po;

import java.math.BigDecimal;
import java.util.List;

public class Orders {
	private Integer orderId;
	private String userId;
	private Integer businessId;
	private String orderDate;
	private BigDecimal orderTotal;
	private Integer daId; 
	private Integer orderState; 
	private List<OrderDetailet> list;

	public Integer getOrderId() {
		return orderId;
	}

	
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Integer getDaId() {
		return daId;
	}

	

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBusinessId() {
		return businessId;
	}


	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	
	public void setDaId(Integer daId) {
		this.daId = daId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}


	public List<OrderDetailet> getList() {
		return list;
	}

	public void setList(List<OrderDetailet> list) {
		this.list = list;
	}
	
}
