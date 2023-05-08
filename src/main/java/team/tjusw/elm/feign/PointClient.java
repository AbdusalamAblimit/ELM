package team.tjusw.elm.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.tjusw.elm.po.CommonResult;

@FeignClient(name = "elm-point-server")
public interface PointClient {
	@GetMapping("/points/user/{userId}/balance")
	public CommonResult<Integer> getBalanceByUserId(@PathVariable("userId") @RequestParam("userId")String userId);
	
	@GetMapping("/points/deducted-amount")
	public CommonResult<BigDecimal> getDeductedAmount(@RequestParam("orderId")Integer orderId,@RequestParam("pointAmount")Integer pointAmount);
	
	@PostMapping("points/earn/order/{orderId}")
	public CommonResult<Integer>  earnPointByOrder(@PathVariable("orderId")Integer orderId);
	
	@PostMapping("/points/deduct/order/{orderId}")
	CommonResult<Integer> deductOrder(@PathVariable("orderId")Integer orderId,@RequestParam("pointAmount")int pointAmount);
	
}
