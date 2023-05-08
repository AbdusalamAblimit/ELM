package team.tjusw.elm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.tjusw.elm.po.VirtualWalletDetailsPO;

@Mapper
public interface VirtualWalletDetailsMapper {
	
	
	 @Select("select * from virtualWalletDetails where fromWalletId = #{walletId} or toWalletId = #{wakketId}")
	 public List<VirtualWalletDetailsPO>getDetailsByWalletId(Integer walletId);
	
	 @Insert("insert into VirtualWalletDetails(time,amount,type,fromWalletId,toWalletId) values(#{time},#{amount},"
	 		+ "#{type},#{fromWalletId},#{toWalletId})")
	 public Integer saveDetailsToDB(VirtualWalletDetailsPO po);
	 
}
