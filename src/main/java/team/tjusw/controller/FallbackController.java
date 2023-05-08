package team.tjusw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import team.tjusw.po.CommonResult;

@RestController
public class FallbackController {
	@RequestMapping("/globalFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult globalFallback()
	{
		return new CommonResult(403,"Gateway触发了全局熔断降级方法.",null);
	}
	
	@RequestMapping("/userFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult userFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了用户微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/businessFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult businessFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了商家微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/cartFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult cartFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了购物车微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/foodFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult foodFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了食品微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/deliveryFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult deliveryFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了配送微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/orderFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult orderFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了订单微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/walletFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult walletFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了钱包微服务的熔断降级方法.",null);
	}
	
	@RequestMapping("/pointFallback")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult pointFallback(ServerWebExchange exchange)
	{
		return new CommonResult(403,"Gateway触发了积分微服务的熔断降级方法.",null);
	}
}
