package team.tjusw.elm.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import team.tjusw.elm.po.Orders;
@Mapper
public interface OrdersMapper {
	
	@Insert("insert into orders(userId,businessId,orderDate,orderTotal,daId) "
			+ " values(#{userId},#{businessId},#{orderDate},#{orderTotal},#{daId})")
	@Options(useGeneratedKeys = true,keyProperty = "orderId",keyColumn="orderId")
	public int saveOrders(Orders orders);
	
	@Select("select * from Orders where orderId=#{orderId}")
	public Orders getOrdersById(Integer orderId);
	

	@Select("select * from orders where userId=#{userId} order by orderId")
	public List<Orders> listOrdersByUserId(String userId);
	
	
	@Update("update Orders set orderState = 1 where orderId = #{orderId}")
	public Integer finishOrder(Integer orderId);
	
}
