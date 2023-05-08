package team.tjusw.elm.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.tjusw.elm.po.Food;

@Mapper
public interface FoodMapper {
	@Select("select * from food where businessId=#{businessId} and valid = 1 order by foodId")
	public List<Food> listFoodByBusinessId(Integer businessId);
	

	@Select("select * from food where foodId=#{foodId}")
	public Food getFoodById(Integer foodId);
}
