package team.tjusw.elm.feign;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import team.tjusw.elm.po.CommonResult;
@Component
public class VirtualWalletClientFallback implements VirtualWalletClient {

	@Override
	public CommonResult<BigDecimal> getBalanceByUserId(String userId) {
		return new CommonResult<>(505,"订单微服务请求虚拟钱包微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

	@Override
	public CommonResult<Integer> transfer(String fromUserId, String toUserId, BigDecimal amount) {
		return new CommonResult<>(505,"订单微服务请求虚拟钱包微服务时发生了错误，因此触发了熔断降级措施.",null);
	}

}
