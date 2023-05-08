<template>
	<div class="wrapper">
		
		<!-- header -->
		<header>
			<i class="fa fa-chevron-left" @click="toUserInfo"></i>
			<p>交易明细</p>
		</header>
		
		<!-- 交易记录 -->
		<ul class="transactionrecord">
			<li v-for="item in detailArr">
				<h1>流水编号: {{item.detailId}}</h1>
				<p>时间: {{item.time}}</p>
				<p>金额: &#165;{{item.amount}}</p>
				<p>类型: {{item.type}}</p>
				<p>入账钱包账号: {{item.toWalletId}}</p>
				<p>出账钱包账号: {{item.fromWalletId}}</p>
			</li>
		</ul>
	</div>
</template>

<script>
	export default {
		name: 'TransactionDetail',
		data() {
			return {
				detailArr: [],
				user: {},
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
	
			this.$axios.get(`virtual-wallets/user/${this.user.userId}/details`).then(response => {
				this.detailArr = response.data.result;
			}).catch(error => {
				console.error(error);
			});
		},
		mounted(){
			//这里的代码是实现：一旦路由到交易明细组件就不能回到在线支付组件
			//先将当前路由添加到history对象中
			history.pushState(null,null,document.URL);
			//popstate事件能够监听history对象的变化
			window.onpopstate = () => {
				this.$router.push({path:'/userInfo'});
			}
		},
		destroyed(){
			window.onpopstate = null;
		},
		methods: {
			toUserInfo(){
				this.$router.push({path: '/userInfo'});
			}
		}
	}
</script>

<style scoped>
	/********** 总容器 **********/
	.wrapper{
		width: 100%;
		height: 100%;
		background-color: #CCCCCC;
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
	.wrapper i{
		position: fixed;
		left: 3.5vw;
		width: 8px;
		height: 8px;
	}
	
	/********** 积分记录 **********/
	.wrapper .transactionrecord{
		width: 100%;
		margin-top: 12vw;
		background-color: #CCCCCC
	}
	.wrapper .transactionrecord li{
		width: 100%;
		box-sizing: border-box;
		padding: 3.5vw;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		border: solid 4vw #CCCCCC;
		background-color: #FFFFFF;
		border-radius: 8vw;
	}
	.wrapper .transactionrecord li h1{
		display: flex;
		justify-content: center;
		box-sizing: border-box;
		padding: 0 1.5vw 1.5vw;
		font-size: 5vw;
		border-bottom: solid 1px #CCCCCC;
	}
	.wrapper .transactionrecord li p{
		font-size: 4vw;
	}
</style>
