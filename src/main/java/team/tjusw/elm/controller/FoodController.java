package team.tjusw.elm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.tjusw.elm.service.FoodService;
import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.po.Food;


//@CrossOrigin("*") 
@RestController
@RequestMapping("/foods")
public class FoodController {
	@Autowired
	private FoodService foodService;
	
	@GetMapping("/business/{businessId}")
	public CommonResult<List<Food>> listFoodByBusinessId(
			@PathVariable("businessId") Integer businessId
			) throws Exception {
		List<Food> ret = foodService.listFoodByBusinessId(businessId);
		if(ret == null)
			return  new CommonResult<List<Food>>(505,"食品服务内部错误.",null);
		else
			return  new CommonResult<List<Food>>(200,"成功获取食品列表.",ret);
	}
	
	@GetMapping("/{foodId}")
	public CommonResult<Food> getFoodById(@PathVariable("foodId") Integer foodId)
	{
		Food ret = foodService.getFoodById(foodId);
		if(ret==null)
			return new CommonResult<Food>(404,"没有id为" + foodId + "的食品.",ret);
		else
			return new CommonResult<Food>(200,"成功获取食品.",ret);
	}
	
}
