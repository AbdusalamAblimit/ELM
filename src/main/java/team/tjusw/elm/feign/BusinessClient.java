package team.tjusw.elm.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import team.tjusw.elm.po.CommonResult;

@FeignClient(name = "elm-business-server")
public interface BusinessClient {
	@GetMapping("/businesses/{businessId}")
	public CommonResult<?> getBusinessById(@PathVariable("businessId") Integer businessId);
}
