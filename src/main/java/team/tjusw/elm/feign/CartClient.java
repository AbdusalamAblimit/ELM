package team.tjusw.elm.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import team.tjusw.elm.po.CommonResult;

@FeignClient(name = "elm-cart-server")
public interface CartClient {

	@GetMapping("carts/user/{userId}/business/{businessId}")
	public CommonResult<List<?>> listCart(@PathVariable("userId") String userId,@PathVariable("businessId") Integer businessId);
	
	@DeleteMapping(value = "/carts/user/{userId}/business/{businessId}")
	public CommonResult<Integer> removeCart(@PathVariable("userId") String userId,@PathVariable("businessId") Integer businessId);
}
