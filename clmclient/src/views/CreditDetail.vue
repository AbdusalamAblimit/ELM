<template>
	<div class="wrapper">
		
		<!-- header -->
		<header>
			<i class="fa fa-chevron-left" @click="toUserInfo"></i>
			<p>积分明细</p>
		</header>
		
		<!-- 积分记录 -->
		<ul class="creditrecord">
			<li v-for="item in detailArr">
				<h1 :style="{'color':(item.balance != null) ? '#FFD700': '#FF4500'}">{{item.cridit}}积分</h1>
				<p>积分流水编号: {{item.detailsId}}</p>
				<p v-show="item.balance != null">剩余: {{item.balance}}积分</p>
				<p>获取时间: {{item.createTime}}</p>
				<p v-show="item.expiredTime != null">过期时间: {{item.expiredTime}}</p>
				<p>渠道: {{item.chanel}}</p>
			</li>
		</ul>
	</div>
</template>

<script>
	export default {
		name: 'CreditDetail',
		data() {
			return {
				detailArr: [],
				user: {},
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
	
			this.$axios.get(`points/user/${this.user.userId}/details`).then(response => {
				this.detailArr = response.data.result;
			}).catch(error => {
				console.error(error);
			});
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
	.wrapper i{
		position: fixed;
		left: 3.5vw;
		width: 8px;
		height: 8px;
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
	
	/********** 积分记录 **********/
	.wrapper .creditrecord{
		width: 100%;
		margin-top: 12vw;
		background-color: #CCCCCC;
	}
	.wrapper .creditrecord li{
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
	.wrapper .creditrecord li h1{
		display: flex;
		justify-content: center;
		box-sizing: border-box;
		padding: 0 1.5vw 1.5vw;
		font-size: 5vw;
		border-bottom: solid 1px #CCCCCC;
	}
	.wrapper .creditrecord li p{
		font-size: 4vw;
	}
</style>
