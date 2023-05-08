<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<p>在线支付</p>
		</header>

		<!-- 订单信息部分 -->
		<h3>订单信息：</h3>
		<div class="order-info">
			<p>
				{{orders.business.businessName}}
				<i class="fa fa-caret-down" @click="detailetShow"></i>
			</p>
			<p>&#165;{{orders.orderTotal}}</p>
		</div>

		<!-- 订单明细部分 -->
		<ul class="order-detailet" v-show="isShowDetailet">
			<li v-for="item in orders.list">
				<p>{{item.food.foodName}} x {{item.quantity}}</p>
				<p>&#165;{{item.food.foodPrice*item.quantity}}</p>
			</li>
			<li>
				<p>配送费</p>
				<p>&#165;{{orders.business.deliveryPrice}}</p>
			</li>
		</ul>

		<!-- 支付方式部分 -->
		<ul class="payment-type">
			<li  @click="whichpay(0)">
				<img src="../assets/alipay.png">
				<p>
					支付宝
					<i class="fa fa-check-circle" v-show="pay_type == 0"></i>
				</p>
			</li>
			<li  @click="whichpay(1)">
				<img src="../assets/wechat.png">
				<p>
					微信支付
					<i class="fa fa-check-circle" v-show="pay_type == 1"></i>
				</p>
			</li>
			<li  @click="whichpay(2)">
				<img src="../assets/wallet.png">
				<p>
					钱包(余额{{balance}}&#165;)
					<i class="fa fa-check-circle" v-show="pay_type == 2"></i>
				</p>
			</li>
		</ul>
		<!-- 积分抵扣 -->
		<div class="credit-deduction">
			<p>剩余{{credit}}积分</p>
			<p>使用<input type="number" v-model.number="amount" placeholder="0">积分，抵扣<label class="orangered">{{amount/100}}</label>&#165;</p>
		</div>
		
		
		<!-- 支付按钮 -->
		<div class="payment-button">
			<button @click="pay()">确认支付</button>
		</div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';

	export default {
		name: 'Payment',
		data(){
			return{
				orderId:this.$route.query.orderId,
				orders:{
					business:{}
				},
				isShowDetailet:false,
				pay_type:0,
				balance: '',
				credit: '',
				amount: '',
			}
		},
		async created(){
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

			try{
				let response = await this.$axios.get(`orders/${this.orderId}`);
				let orders = response.data.result;
				response = await this.$axios.get(`businesses/${orders.businessId}`);
				orders["business"] = response.data.result;
				for(let item of orders.list)
				{
					response = await this.$axios.get(`foods/${item.foodId}`);
					item["food"] = response.data.result;
				}
				this.orders = orders;
			}catch (error) {
				console.error(error);
			}
			
			

			
		},
		mounted(){
			//这里的代码是实现：一旦路由到在线支付组件就不能回到订单确认组件
			//先将当前路由添加到history对象中
			history.pushState(null,null,document.URL);
			//popstate事件能够监听history对象的变化
			window.onpopstate = () => {
				this.$router.push({path:'/index'});
			}
		},
		destroyed(){
			window.onpopstate = null;
		},
		methods:{
			detailetShow(){
				this.isShowDetailet = !this.isShowDetailet;
			},
			whichpay(n){
				this.pay_type = n;
			},
			pay(){
				this.$axios.post(`orders/${this.orders.orderId}/pay/virtual-wallet`, this.$qs.stringify({
					pointAmount: this.amount,
				})).then(response => {
					let ret = response.data;
					if(ret.code == 200){
						alert('支付成功！');
						this.$router.push({path: '/transactionDetail'});
					}else if(ret.result == 1){
						alert('钱包余额不足！');
					}else if(ret.result == 2){
						alert('积分余额不足！');
					}else if(ret.code == 409){
						alert('支付失败，订单早已支付！');
					}else{
						alert('未知错误！');
					}
				}).catch(error => {
					console.error(error);
				});
			},
		},
		components: {
			Footer
		}
	}
</script>

<style scoped>
	/********** 总容器 **********/
	.wrapper {
		width: 100%;
		height: 100%;
	}

	/********** header **********/
	.wrapper header {
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

	/********** 订单信息部分 ***********/
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw 4vw 0;

		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.wrapper .order-info {
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-info p:last-child {
		color: orangered;
	}

	/********** 订单明细部分 **********/
	.wrapper .order-detailet {
		width: 100%;
	}

	.wrapper .order-detailet li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-detailet li p {
		font-size: 3vw;
		color: #666;
	}

	/********** 支付方式部分 **********/
	.wrapper .payment-type {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 0;
		border-bottom: solid 1px #CCCCCC;
	}

	.wrapper .payment-type li {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .payment-type li img {
		width: 8.1vw;
		height: 8.1vw;
	}

	.wrapper .payment-type li p {
		width: 100%;
		box-sizing: border-box;
		padding: 4.5vw;
		font-size: 4.5vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .payment-type li p .fa-check-circle {
		font-size: 5vw;
		color: #38CA73;
	}
	/********** 积分抵扣 **********/
	.wrapper .credit-deduction{
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
	}
	.wrapper .credit-deduction p{
		width: 100%;
		font-size: 4.5vw;
		box-sizing: border-box;
		padding: 1vw;
		color: #666666;
	}
	.wrapper .credit-deduction p input{
		border: none;
		width: 15vw;
		height: 6vw;
		font-size: 4.5vw;
		text-align: right;
	}
	.wrapper .credit-deduction p .orangered{
		color: orangered;
	}
	/********** 支付按钮 **********/
	.wrapper .payment-button {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;
	}

	.wrapper .payment-button button {
		width: 100%;
		height: 10vw;
		border: none;
		outline: none;
		border-radius: 4px;
		background-color: #38CA73;
		color: #FFFFFF;
	}
</style>
