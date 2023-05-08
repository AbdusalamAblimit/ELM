package team.tjusw.elm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import team.tjusw.elm.po.VirtualWalletPO;


@Mapper
public interface VirtualWalletMapper {
	
	 @Select("select * from VirtualWallet where userId=#{userId}")
	 public VirtualWalletPO getVirtualWalletByUserId(String userId);
	 
	 @Insert("insert into VirtualWallet(createTime,userId) values(#{createTime},#{userId})")
	 public int createVirtualWallet(VirtualWalletPO po);
	 
	 @Update("update VirtualWallet set balance = #{balance} where walletId = #{walletId}")
	 public int updateBalance(VirtualWalletPO po);
	 
	 @Select("select count(*) from user where userId=#{userId}")
	 public int getUserById(String userId);
	 
	 
}
