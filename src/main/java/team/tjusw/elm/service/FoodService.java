package team.tjusw.elm.service;

import java.util.List;

import team.tjusw.elm.po.Food;

public interface FoodService {
	public List<Food> listFoodByBusinessId(Integer businessId);
	public Food getFoodById(Integer foodId);
}
