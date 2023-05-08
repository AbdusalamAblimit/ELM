package team.tjusw.elm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import team.tjusw.elm.po.Cart;
import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.service.CartService;

//@CrossOrigin("*") 
@RestController
@RequestMapping("/carts")
public class CartController {
	
	
	@Autowired
	private CartService cartService;

	@GetMapping(value = {"/user/{userId}",
						 "/user/{userId}/business/{businessId}"})
	public CommonResult<List<Cart>> listCart(Cart cart) throws Exception {
		
		List<Cart> ret = cartService.listCart(cart);
		if(ret == null)
			return  new CommonResult<List<Cart>>(505,"购物车服务内部错误.",null);
		else
			return  new CommonResult<List<Cart>>(200,"成功获取购物车列表.",ret);
	}

	
	
	@DeleteMapping(value = {	"/user/{userId}/business/{businessId}",
	 						"/user/{userId}/business/{businessId}/food/{foodId}"})
	public CommonResult<Integer> removeCart(Cart cart) throws Exception {
		int ret = cartService.removeCart(cart);
		if(ret==0)
			return new CommonResult<Integer>(404,"没有符合的购物车.",ret); 
		else
			return new CommonResult<Integer>(200,"成功删除.",ret);
		
	}
	
	@PostMapping
	public CommonResult<Integer> saveCart(Cart cart) throws Exception {
		int ret =  cartService.saveCart(cart);
		if(ret==0)
			return new CommonResult<Integer>(505,"未知错误，未能新增购物车.",ret); 
		else
			return new CommonResult<Integer>(201,"成功新增购物车.",ret);
	}

	
	@PutMapping("/user/{userId}/business/{businessId}/food/{foodId}")
	public CommonResult<Integer> updateCart(Cart cart) throws Exception {
		int ret =  cartService.updateCart(cart);
		if(ret==0)
			return new CommonResult<Integer>(404,"没有符合的购物车.",ret); 
		else
			return new CommonResult<Integer>(200,"成功更改商品数量.",ret);
	}

}
