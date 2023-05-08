package team.tjusw.elm.service.domain;

import java.math.BigDecimal;


import team.tjusw.elm.mapper.VirtualWalletDetailsMapper;
import team.tjusw.elm.mapper.VirtualWalletMapper;

import team.tjusw.elm.po.VirtualWalletPO;
import team.tjusw.elm.util.CommonUtil;


public class VirtualWallet {
	private Integer walletId;
	private String userId;
	private BigDecimal balance;
	private String createTime;
	
	

	VirtualWalletMapper walletMapper;	
	VirtualWalletDetailsMapper detailsMapper;
	
	public VirtualWallet(VirtualWalletPO virtualWalletPO,VirtualWalletMapper walletMapper,VirtualWalletDetailsMapper detailsMapper)
	{
		this.walletMapper = walletMapper;
		this.detailsMapper = detailsMapper;
		this.walletId = virtualWalletPO.getWalletId();
		this.userId = virtualWalletPO.getUserId();
		this.balance = virtualWalletPO.getBalance();
		this.createTime = virtualWalletPO.getCreateTime();
	}
	
	public Integer credit(BigDecimal amount)
	{
		balance = balance.add(amount);
		int ret = walletMapper.updateBalance(this.toPO());
		VirtualWalletDetails details = new VirtualWalletDetails(null, CommonUtil.getCurrentDate(), amount, 0, null, this.walletId,this.detailsMapper);
		ret&=details.saveToDB();
		return ret;
	}
	
	public Integer debit(BigDecimal amount)
	{
		balance = balance.subtract(amount);
		int ret = walletMapper.updateBalance(this.toPO());
		VirtualWalletDetails details = new VirtualWalletDetails(null, CommonUtil.getCurrentDate(), amount, 1, this.walletId, null,this.detailsMapper);
		ret&=details.saveToDB();
		return ret;
	}
	
	public BigDecimal getBalance()
	{
		return this.balance;
	}
	
	public Integer transfer(VirtualWallet toWallet,BigDecimal amount)
	{
		this.balance = this.balance.subtract(amount);
		toWallet.balance = toWallet.balance.add(amount);
		int ret = walletMapper.updateBalance(this.toPO()); 
		ret&= walletMapper.updateBalance(toWallet.toPO());
		VirtualWalletDetails details = new VirtualWalletDetails(null, CommonUtil.getCurrentDate(), amount, 2, this.walletId, toWallet.walletId,this.detailsMapper);
		ret&=details.saveToDB();
		return ret;
	}
	

	
	
	public VirtualWalletPO toPO()
	{
		VirtualWalletPO po = new VirtualWalletPO();
		po.setBalance(balance);
		po.setCreateTime(createTime);
		po.setUserId(userId);
		po.setWalletId(walletId);
		return po;
	}
	
	
	public static VirtualWallet getVirtualWalletByUserId(String userId, VirtualWalletMapper walletMapper,VirtualWalletDetailsMapper detailsMapper)
	{
		VirtualWalletPO po = walletMapper.getVirtualWalletByUserId(userId);
		if(po==null)
		{
			po = new VirtualWalletPO();
			po.setCreateTime(CommonUtil.getCurrentDate());
			po.setUserId(userId);
			walletMapper.createVirtualWallet(po);
		}
		return new VirtualWallet(walletMapper.getVirtualWalletByUserId(userId),walletMapper,detailsMapper);
		
	}
}
