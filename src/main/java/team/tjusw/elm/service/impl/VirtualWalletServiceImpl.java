package team.tjusw.elm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tjusw.elm.controller.vo.VirtualWalletDetailsVo;
import team.tjusw.elm.lock.RedissonConfig;
import team.tjusw.elm.mapper.VirtualWalletDetailsMapper;
import team.tjusw.elm.mapper.VirtualWalletMapper;
import team.tjusw.elm.po.VirtualWalletDetailsPO;

import team.tjusw.elm.service.VirtualWalletService;
import team.tjusw.elm.service.domain.VirtualWallet;
import team.tjusw.elm.service.domain.VirtualWalletDetails;


@Service
public class VirtualWalletServiceImpl implements VirtualWalletService {

	@Autowired
	VirtualWalletMapper walletMapper;
	
	@Autowired
	VirtualWalletDetailsMapper detailsMapper;
	
	@Override
	public BigDecimal getBalanceByUserId(String userId) {
		VirtualWallet wallet = this.getVirtualWalletByUserId(userId);
		return wallet.getBalance();
	}

	@Override
	public List<VirtualWalletDetailsVo> getDetailsByUserId(String userId) {
		VirtualWallet wallet = this.getVirtualWalletByUserId(userId);
		List<VirtualWalletDetailsPO> pos =  detailsMapper.getDetailsByWalletId(wallet.toPO().getWalletId());
		List<VirtualWalletDetails> domains = new ArrayList<VirtualWalletDetails>(pos.size());
		for(VirtualWalletDetailsPO po:pos)
			domains.add(new VirtualWalletDetails(po));
		domains.sort(null);
		List<VirtualWalletDetailsVo>  vos = new ArrayList<VirtualWalletDetailsVo>(pos.size());
		for(VirtualWalletDetails domain:domains)
			vos.add(domain.toVO());
		return vos;
	}



	 @Autowired
	 private RedissonConfig  redissonConfig;
	 
	 @Override
	 public Integer credit(String userId, BigDecimal amount) {
	     RLock userLock = redissonConfig.redissonClient.getLock("wallet-" + userId);
	     userLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	     try {
	         VirtualWallet wallet = this.getVirtualWalletByUserId(userId);
	         return wallet.credit(amount) > 0 ? 200 : 0;
	     } finally {
	         userLock.unlock();
	     }
	 }
	 @Override
	 public Integer debit(String userId, BigDecimal amount) {
	     RLock userLock = redissonConfig.redissonClient.getLock("wallet-" + userId);
	     userLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	     try {
	         VirtualWallet wallet = this.getVirtualWalletByUserId(userId);
	         if (wallet.getBalance().compareTo(amount) < 0)
	             return 0;
	         return wallet.debit(amount) > 0 ? 200 : 0;
	     } finally {
	         userLock.unlock();
	     }
	 }

	 @Override
	 public Integer transfer(String fromUserId, String toUserId, BigDecimal amount) {
	     RLock fromUserLock = redissonConfig.redissonClient.getLock("wallet-" + fromUserId);
	     RLock toUserLock = redissonConfig.redissonClient.getLock("wallet-" + toUserId);

	     if (fromUserId.compareTo(toUserId) < 0) {
	         fromUserLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	         toUserLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	     } else {
	         toUserLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	         fromUserLock.lock(redissonConfig.timeout, TimeUnit.MILLISECONDS);
	     }

	     try {
	         VirtualWallet fromWallet = this.getVirtualWalletByUserId(fromUserId);
	         if (fromWallet.getBalance().compareTo(amount) < 0)
	             return 0;
	         VirtualWallet toWallet = this.getVirtualWalletByUserId(toUserId);
	         return fromWallet.transfer(toWallet, amount) > 0 ? 200 : 0;
	     } finally {
	         toUserLock.unlock();
	         fromUserLock.unlock();
	     }
	 }
	
	private VirtualWallet getVirtualWalletByUserId(String userId)
	{
		return VirtualWallet.getVirtualWalletByUserId(userId,this.walletMapper,this.detailsMapper);
	}
}
