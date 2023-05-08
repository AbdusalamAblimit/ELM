package team.tjusw.elm.service.domain;

import java.math.BigDecimal;


import team.tjusw.elm.controller.vo.VirtualWalletDetailsVo;
import team.tjusw.elm.mapper.VirtualWalletDetailsMapper;
import team.tjusw.elm.po.VirtualWalletDetailsPO;
import team.tjusw.elm.util.CommonUtil;

public class VirtualWalletDetails implements Comparable<Object> {
	private Integer detailId;
	private String time;
	private BigDecimal amount;
	private Integer type;
	private Integer fromWalletId;
	private Integer toWalletId;

	VirtualWalletDetailsMapper detailsMapper;
	
	public VirtualWalletDetails(Integer detailId, String time, BigDecimal amount, Integer type, Integer fromWalletId,
			Integer toWalletId,VirtualWalletDetailsMapper detailsMapper) {
		this.detailsMapper = detailsMapper;
		this.detailId = detailId;
		this.time = time;
		this.amount = amount;
		this.type = type;
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
	}

	public VirtualWalletDetails(VirtualWalletDetailsPO po) {
		this.amount=po.getAmount();
		this.detailId=po.getDetailId();
		this.time=po.getTime();
		this.type=po.getType();
		this.fromWalletId=po.getFromWelletId();
		this.toWalletId = po.getToWalletId();
	}

	public Integer saveToDB() {
		return detailsMapper.saveDetailsToDB(this.toPO());
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof VirtualWalletDetails)
			return CommonUtil.compareStringDate(((VirtualWalletDetails) obj).time, this.time);
		else
			throw new RuntimeException("VirtualWalletDetails只能和相同类型的对象比较.");
	}
	
	
	public VirtualWalletDetailsVo toVO()
	{
		return new VirtualWalletDetailsVo(detailId, time, amount, type, fromWalletId, toWalletId);
	}
	
	
	private VirtualWalletDetailsPO toPO()
	{
		VirtualWalletDetailsPO po  = new VirtualWalletDetailsPO();
		po.setAmount(this.amount);
		po.setDetailId(this.detailId);
		po.setFromWalletId(this.fromWalletId);
		po.setToWalletId(this.toWalletId);
		po.setTime(this.time);
		po.setType(this.type);
		return po;
	}

}
