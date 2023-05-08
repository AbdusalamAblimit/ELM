package team.tjusw.elm.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.tjusw.elm.po.CommonResult;

@FeignClient(name = "elm-wallet-server")
public interface VirtualWalletClient {
	
	@GetMapping("/virtual-wallets/user/{userId}/balance")
	public CommonResult<BigDecimal> getBalanceByUserId(@PathVariable("userId") String userId);
	
	@PostMapping("/virtual-wallets/transfer")
	public CommonResult<Integer> transfer(@RequestParam("fromUserId") String fromUserId, @RequestParam("toUserId") String toUserId, @RequestParam("amount") BigDecimal amount);
	
}
