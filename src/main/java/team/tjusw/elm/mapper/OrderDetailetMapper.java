package team.tjusw.elm.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import team.tjusw.elm.po.OrderDetailet;

@Mapper
public interface OrderDetailetMapper {
	
	
	public int saveOrderDetailetBatch(List<OrderDetailet> list);
	
	@Select("select * from orderDetailet where orderId=#{orderId}")
	public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderOd);
}
