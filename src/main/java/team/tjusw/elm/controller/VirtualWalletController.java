package team.tjusw.elm.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.tjusw.elm.service.VirtualWalletService;
import team.tjusw.elm.controller.vo.VirtualWalletDetailsVo;
import team.tjusw.elm.po.CommonResult;

//@CrossOrigin("*")
@RestController
@RequestMapping("/virtual-wallets")
public class VirtualWalletController {
	@Autowired
	private VirtualWalletService virtualWalletService;

	@GetMapping("/user/{userId}/balance")
	public CommonResult<BigDecimal> getBalanceByUserId(@PathVariable("userId") String userId) {
		BigDecimal ret = virtualWalletService.getBalanceByUserId(userId);
		if(ret==null)
			return new CommonResult<BigDecimal>(505,"虚拟钱包服务发生未知错误.",ret);
		else 
			return new CommonResult<BigDecimal>(200,"成功获取用户虚拟钱包余额.",ret);
			
	}

	@GetMapping("/user/{userId}/details")
	public CommonResult<List<VirtualWalletDetailsVo>> getDetailsByUserId(@PathVariable("userId") String userId) {
		List<VirtualWalletDetailsVo> ret = virtualWalletService.getDetailsByUserId(userId);
		if(ret==null)
			return new CommonResult<List<VirtualWalletDetailsVo>>(505,"虚拟钱包服务发生未知错误.",ret);
		else 
			return new CommonResult<List<VirtualWalletDetailsVo>>(200,"成功获取用户虚拟钱包交易记录.",ret);
		
	}

	
	@PostMapping("/user/{userId}/credit")
	public CommonResult<Integer> credit(@PathVariable("userId") String userId, BigDecimal amount) {
	    try {
	        int ret = virtualWalletService.credit(userId, amount);
	        if (ret == 0)
	            return new CommonResult<Integer>(422, "充值失败.", ret);
	        else
	            return new CommonResult<Integer>(200, "充值成功.", ret);
	    } catch (Exception e) {
	        return new CommonResult<Integer>(500, "充值过程中出现异常: " + e.getMessage(), null);
	    }
	}

	@PostMapping("/user/{userId}/debit")
	public CommonResult<Integer> debit(@PathVariable("userId") String userId, BigDecimal amount) {
	    try {
	        int ret = virtualWalletService.debit(userId, amount);
	        if (ret == 0)
	            return new CommonResult<Integer>(422, "消费失败.", ret);
	        else
	            return new CommonResult<Integer>(200, "消费成功.", ret);
	    } catch (Exception e) {
	        return new CommonResult<Integer>(500, "消费过程中出现异常: " + e.getMessage(), null);
	    }
	}

	@PostMapping("/transfer")
	public CommonResult<Integer> transfer(String fromUserId, String toUserId, BigDecimal amount) {
	    try {
	        int ret = virtualWalletService.transfer(fromUserId, toUserId, amount);
	        if (ret == 0)
	            return new CommonResult<Integer>(422, "转账失败.", ret);
	        else
	            return new CommonResult<Integer>(200, "转账成功.", ret);
	    } catch (Exception e) {
	        return new CommonResult<Integer>(500, "转账过程中出现异常: " + e.getMessage(), null);
	    }
	}

	
	
	
}
