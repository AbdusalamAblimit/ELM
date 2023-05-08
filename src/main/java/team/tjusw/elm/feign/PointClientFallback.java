package team.tjusw.elm.feign;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import team.tjusw.elm.po.CommonResult;

public class PointClientFallback implements PointClient {

	@Override
	public CommonResult<Integer> getBalanceByUserId(String userId) {
		return new CommonResult<>(505,"订单微服务请求积分微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

	@Override
	public CommonResult<BigDecimal> getDeductedAmount(Integer orderId, Integer pointAmount) {
		return new CommonResult<>(505,"订单微服务请求积分微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

	@Override
	public CommonResult<Integer> earnPointByOrder(Integer orderId) {
		return new CommonResult<>(505,"订单微服务请求积分微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

	@Override
	public CommonResult<Integer> deductOrder(Integer orderId, int pointAmount) {
		return new CommonResult<>(505,"订单微服务请求积分微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

}
