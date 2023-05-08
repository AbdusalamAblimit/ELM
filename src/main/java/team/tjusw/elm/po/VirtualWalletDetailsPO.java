package team.tjusw.elm.po;

import java.math.BigDecimal;

public class VirtualWalletDetailsPO {
	private Integer detailsId;
	private String time;
	private BigDecimal amount;
	private Integer type;
	private Integer fromWalletId;
	private Integer toWalletId;
	public Integer getDetailId() {
		return detailsId;
	}
	public void setDetailId(Integer detailId) {
		this.detailsId = detailId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFromWelletId() {
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
