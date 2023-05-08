package team.tjusw.elm.service;

import java.math.BigDecimal;
import java.util.List;

import team.tjusw.elm.controller.vo.VirtualWalletDetailsVo;

public interface VirtualWalletService {
	public BigDecimal getBalanceByUserId(String userId);
	public List<VirtualWalletDetailsVo> getDetailsByUserId(String userId);
	public Integer credit(String userId,BigDecimal amount);
	public Integer debit(String userId,BigDecimal amount);
	public Integer transfer(String fromUserId,String toUserId,BigDecimal amount);
}
