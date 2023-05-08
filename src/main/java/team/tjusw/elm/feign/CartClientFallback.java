//package team.tjusw.elm.feign;
//
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import team.tjusw.elm.po.CommonResult;
//
//
//public class CartClientFallback implements CartClient {
//
//	@Override
//	public CommonResult<List<?>> listCart(String userId, Integer businessId) {
//		
//		return new CommonResult<>(505,"订单微服务请求购物车微服务时发生了错误，因此触发了购物车微服务的熔断降级措施.",null);
//	}
//
//	@Override
//	public CommonResult<Integer> removeCart(String userId, Integer businessId) {
//		return new CommonResult<>(505,"订单微服务请求购物车微服务时发生了错误，因此触发了购物车微服务的熔断降级措施.",null);
//	}
//
//}
