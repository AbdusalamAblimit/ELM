<template>
	<div class="wrapper">
		
		<!-- header -->
		<header>
			<p>用户信息</p>
		</header>
		
		<!-- 用户信息 -->
		<div class="userinfo">
			<div class="userinfo-img">
				<img :src="user.userImg">
			</div>
			<div class="userinfo-id">
				<p>{{user.userId}}</p>
			</div>
		</div>
		
		<!-- 钱包 -->
		<div class="wallet">
			<div class="wallet-balance">
				<p>钱包余额 : &#165;{{balance}}</p>
			</div>
			<ul class="wallet-operation">
				<li>
					<p @click="recharge_start">充值</p>
				</li>
				<li>
					<p @click="withdrawal_start">提现</p>
				</li>
				<li>
					<p @click="transfer_start">转账</p>
				</li>
			</ul>
			<div class="transaction-details">
				<p @click="toTransactionDetail">交易明细》</p>
			</div>
		</div>
		
		<!-- 积分 -->
		<div class="credit">
			<p>积分 : {{credit}}</p>
			<p @click="toCreditDetail" style="text-decoration:underline;">积分明细》</p>
		</div>
		
		<!-- 充值 -->
		<div class="recharge" v-if="recharge_popup" @click="recharge_cancle">
			<div class="recharge-box" @click.stop="">
				<div class="content">
					<input type="number" v-model="amount" placeholder="0">
					<p>&#165;</p>
				</div>
				<div>
					<button  @click="recharge()">充值</button>
				</div>
			</div>
		</div>
		
		<!-- 提现 -->
		<div class="withdrawal" v-if="withdrawal_popup" @click="withdrawal_cancle">
			<div class="withdrawal-box" @click.stop="">
				<div class="content">
					<input type="number" v-model="amount" placeholder="0">
					<p>&#165;</p>
				</div>
				<div>
					<button  @click="withdrawal()">提现</button>
				</div>
			</div>
		</div>
		
		<!-- 转账 -->
		<div class="transfer" v-if="transfer_popup" @click="transfer_cancle">
			<div class="transfer-box" @click.stop="">
				<div class="content">
					<div class="toUserId">
						<input type="text" v-model="toUserId" placeholder="转账Id">
					</div>
					<div class="amount">
						<input type="number" v-model="amount" placeholder="0">
						<p>&#165;</p>
					</div>
				</div>
				<div>
					<button  @click="transfer()">转账</button>
				</div>
			</div>
		</div>
		
		<!-- 底部菜单 -->
		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	
	export default {
		name: 'UserInfo',
		data() {
			return {
				balance: '',
				credit: '',
				user: {},
				amount: '',
				toUserId: '',
				recharge_popup: false,
				withdrawal_popup: false,
				transfer_popup: false,
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
	
			this.$axios.get(`virtual-wallets/user/${this.user.userId}/balance`).then(response => {
				this.balance = response.data.result;
			}).catch(error => {
				console.error(error);
			});
			
			this.$axios.get(`points/user/${this.user.userId}/balance`).then(response => {
				this.credit = response.data.result;
			}).catch(error => {
				console.error(error);
			});
		},
		components: {
			Footer
		},
		methods: {
			toTransactionDetail() {
				this.$router.push({path: '/transactionDetail'});
			},
			toCreditDetail() {
				this.$router.push({path: '/creditDetail'});
			},
			
			recharge_start(){
				this.recharge_popup = true;
			},
			recharge_cancle(){
				this.recharge_popup = false;
			},
			recharge() {
				this.$axios.post(`virtual-wallets/user/${this.user.userId}/credit`, this.$qs.stringify({
					amount: this.amount,
				})).then(response => {
					let ret = response.data;
					
					if(ret.code == 200) {
						alert('充值成功！');
						this.amount = '';
						this.recharge_popup = false;
					    this.$axios.get(`virtual-wallets/user/${this.user.userId}/balance`).then(response => {
							this.balance = response.data.result;
						}).catch(error => {
							console.error(error);
						});
					} 
					else {
						alert('充值失败！');
						this.amount = '';
					}
				}).catch(error => {
					console.error(error);
				});
			},
			
			withdrawal_start(){
				this.withdrawal_popup = true;
			},
			withdrawal_cancle(){
				this.withdrawal_popup = false;
			},
			withdrawal() {
				this.$axios.post(`virtual-wallets/user/${this.user.userId}/debit`, this.$qs.stringify({
					amount: this.amount,
				})).then(response => {
					let ret = response.data;
					 if(ret.code == 200) {
						alert('提现成功！');
						this.amount = '';
						this.withdrawal_popup = false;
						this.$axios.get(`virtual-wallets/user/${this.user.userId}/balance`).then(response => {
							this.balance = response.data.result;
						}).catch(error => {
							console.error(error);
						});
					} else {
						alert('提现失败！');
						this.amount = '';
					} 
				}).catch(error => {
					console.error(error);
				});
			},
			
			transfer_start(){
				this.transfer_popup = true;
			},
			transfer_cancle(){
				this.transfer_popup = false;
			},
			transfer() {
				this.$axios.get(`users/exists/${this.toUserId}`).then(response => {
					let ret = response.data.code;
					if(ret == 200){
						this.$axios.post(`virtual-wallets/transfer`, this.$qs.stringify({
							fromUserId: this.user.userId,
							toUserId: this.toUserId,
							amount: this.amount,
						})).then(response => {
							let ret = response.data;
							 if(ret.code == 200) {
								alert('转账成功！');
								this.toUserId = '';
								this.amount = '';
								this.transfer_popup = false;
								
								this.$axios.get(`virtual-wallets/user/${this.user.userId}/balance`).then(response => {
								this.balance = response.data.result;
								}).catch(error => {
									console.error(error);
								});
							} else {
								alert('转账失败！');
								this.amount = '';
							}
						}).catch(error => {
							console.error(error);
						});
					}else{
						alert('没有该用户！');
						this.toUserId = '';
						this.amount = '';
					}
				}).catch(error => {
					console.error(error);
				});
			},
			
		},
	}
</script>

<style scoped>
	/********** 总容器 **********/
	.wrapper{
		width: 100%;
		height: 100%;
	}
	
	/********** header **********/
	.wrapper header{
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	/********** 用户信息 **********/
	.wrapper .userinfo{
		width: 100%;
		margin-top: 12vw;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		box-sizing: border-box;
		padding: 2.5vw;
	}
	.wrapper .userinfo .userinfo-img{
		width: 100%;
		height: 35vw;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper .userinfo .userinfo-img img{
		width: 30vw;
		height: 30vw;
		border-radius: 15vw;
	}
	.wrapper .userinfo .userinfo-id{
		font-size: 6vw;
		margin-top: 1vw;
	}
	/********** 钱包 **********/
	.wrapper .wallet{
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		box-sizing: border-box;
		padding: 2.5vw;
		font-size: 5vw;
		background-color: #DDDDDD;
		color: #FFFFFF;
	}
	.wrapper .wallet .wallet-balance{
		width: 100%;
		box-sizing: border-box;
		padding: 1.5vw;
	}
	.wrapper .wallet .wallet-balance p{
		box-sizing: border-box;
		padding: 1.5vw;
		background-color: #00BBFF;
		border-radius: 1.5vw;
		text-align: start;
	}
	.wrapper .wallet .wallet-operation{
		width: 100%;
		box-sizing: border-box;
		padding: 1.5vw;
		display: flex;
		justify-content: space-around;
		align-items: center;
	}
	.wrapper .wallet .wallet-operation li{
		background-color: #00BBFF;
		box-sizing: border-box;
		padding: 1.5vw 6vw;
		border-radius: 1.5vw;
		text-decoration:underline;
	}
	.wrapper .wallet .transaction-details{
		width: 100%;
		box-sizing: border-box;
		padding: 1.5vw;
		justify-content: space-around;
		align-items: center;
	}
	.wrapper .wallet .transaction-details p{
		box-sizing: border-box;
		padding: 1.5vw;
		border-radius: 1.5vw;
		background-color: #00BBFF;
		text-decoration:underline;
	}
	/********** 积分 **********/
	.wrapper .credit{
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		box-sizing: border-box;
		padding: 2.5vw;
		background-color: #0097FF;
		font-size: 5vw;
		color: #DDDDDD;
	}
	/********** 充值 **********/
	.wrapper .recharge{
		width: 100%;
		height: 100%;
		position: fixed;
		left: 0;
		top: 0;
		background: rgba(0,0,0,0.6);
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper .recharge .recharge-box{
		background-color: #FFFFFF;
		padding: 5vw 15vw 5vw;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-radius: 8px;
	}
	.wrapper .recharge .recharge-box .content{
		display: flex;
		flex-direction: row;
	}
	.wrapper .recharge .recharge-box .content input{
		border: none;
		width: 30vw;
		height: 7vw;
		font-size: 4vw;
		margin-bottom: 3vw;
		text-align: right;
	}
	.wrapper .recharge .recharge-box button{
		width: 15vw;
		height: 8vw;
		font-size: 4vw;
		font-weight: 700;
		color: #fff;
		background-color: #38CA73;
		border-radius: 4px;
		border: none;
		outline: none;
	}
	
	/********** 提现 **********/
	.wrapper .withdrawal{
		width: 100%;
		height: 100%;
		position: fixed;
		left: 0;
		top: 0;
		background: rgba(0,0,0,0.6);
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper .withdrawal .withdrawal-box{
		background-color: #FFFFFF;
		padding: 5vw 15vw 5vw;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-radius: 8px;
	}
	.wrapper .withdrawal .withdrawal-box .content{
		display: flex;
		flex-direction: row;
	}
	.wrapper .withdrawal .withdrawal-box .content input{
		border: none;
		width: 30vw;
		height: 7vw;
		font-size: 4vw;
		margin-bottom: 3vw;
		text-align: right;
	}
	.wrapper .withdrawal .withdrawal-box button{
		width: 15vw;
		height: 8vw;
		font-size: 4vw;
		font-weight: 700;
		color: #fff;
		background-color: #38CA73;
		border-radius: 4px;
		border: none;
		outline: none;
	}
	
	/********** 转账 **********/
	.wrapper .transfer{
		width: 100%;
		height: 100%;
		position: fixed;
		left: 0;
		top: 0;
		background: rgba(0,0,0,0.6);
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.wrapper .transfer .transfer-box{
		background-color: #FFFFFF;
		padding: 5vw 15vw 5vw;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-radius: 8px;
	}
	.wrapper .transfer .transfer-box .content{
		display: flex;
		flex-direction: column;
	}
	.wrapper .transfer .transfer-box .content .toUserId{
		margin-bottom: 3vw;
	}
	.wrapper .transfer .transfer-box .content .toUserId input{
		border: none;
		width: 30vw;
		height: 7vw;
		font-size: 4vw;
		margin-bottom: 3vw;
		text-align: right;
	}
	.wrapper .transfer .transfer-box .content .amount{
		display: flex;
		flex-direction: row;
		margin-bottom: 3vw;
	}
	.wrapper .transfer .transfer-box .content .amount input{
		border: none;
		width: 30vw;
		height: 7vw;
		font-size: 4vw;
		text-align: right;
	}
	.wrapper .tramsfer .transfer-box button{
		width: 15vw;
		height: 8vw;
		font-size: 4vw;
		font-weight: 700;
		color: #fff;
		background-color: #38CA73;
		border-radius: 4px;
		border: none;
		outline: none;
	}
</style>
