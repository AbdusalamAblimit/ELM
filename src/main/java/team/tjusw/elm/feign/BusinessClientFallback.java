package team.tjusw.elm.feign;

import org.springframework.stereotype.Component;

import team.tjusw.elm.po.CommonResult;

public class BusinessClientFallback implements BusinessClient {

	@Override
	public CommonResult<?> getBusinessById(Integer businessId) {
		return new CommonResult<>(505,"订单微服务请求商家微服务时发生了错误，因此触发了购物车微服务的熔断降级措施.",null);
	}

}
