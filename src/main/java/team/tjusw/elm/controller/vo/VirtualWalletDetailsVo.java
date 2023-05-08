package team.tjusw.elm.controller.vo;

import java.math.BigDecimal;


public class VirtualWalletDetailsVo {
	public enum TransactionType{    
		CREDIT,
		DEBIT,	
		TRANSFER 
	};
	
	private Integer detailId;
	private String time;    
	private BigDecimal amount; 
	private TransactionType type; 
	private Integer fromWalletId;  
	private Integer toWalletId;   
	
	
	public VirtualWalletDetailsVo(Integer detailId, String time, BigDecimal amount, Integer type,
			Integer fromWalletId, Integer toWalletId) {
		super();
		this.detailId = detailId;
		this.time = time;
		this.amount = amount;
		this.type = TransactionType.values()[type];
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
	}
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public Integer getFromWalletId() {
		return fromWalletId;
	}
	public void setFromWalletId(Integer fromWalletId) {
		this.fromWalletId = fromWalletId;
	}
	public Integer getToWalletId() {
		return toWalletId;
	}
	public void setToWalletId(Integer toWalletId) {
		this.toWalletId = toWalletId;
	}
	
	
	
}
