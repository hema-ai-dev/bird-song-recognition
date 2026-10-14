<template>
	<view :style='{"minHeight":"100vh","padding":"20rpx","flexWrap":"wrap","background":"#eef7f1","display":"flex","width":"100%","position":"relative","height":"auto"}'>
		<view class="title" :style='{"padding":"0","margin":"0","color":"#000","borderRadius":"20rpx 20rpx 0 0","textAlign":"center","background":"#ffffff","width":"100%","lineHeight":"80rpx","fontSize":"36rpx"}'>{{detail.title}}</view>
		
		<view :style='{"borderColor":"#ddd","alignItems":"center","color":"#999","flexWrap":"wrap","background":"#ffffff","borderWidth":"0 0 2rpx","display":"flex","width":"100%","fontSize":"28rpx","borderStyle":"solid","justifyContent":"center"}'>
		  <view :style='{"width":"100%","padding":"0 20rpx","margin":"20rpx 0","textAlign":"right","order":"2"}'>
		    <text class="icon iconfont icon-shijian21" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"inherit","color":"inherit"}'></text>
		    <text :style='{"color":"inherit","lineHeight":"1.5","fontSize":"inherit"}'>发布时间：{{detail.addtime}}</text>
		  </view>
		  <view :style='{"padding":"0","margin":"0 20rpx 0 0"}'>
		    <text class="icon iconfont icon-geren16" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"inherit","color":"inherit"}'></text>
		    <text :style='{"color":"inherit","lineHeight":"1.5","fontSize":"inherit"}'>{{detail.name}}</text>
		  </view>
		  <view :style='{"padding":"0","margin":"0 20rpx 0 0"}'>
		    <text class="icon iconfont icon-zan10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"inherit","color":"inherit"}'></text>
		    <text :style='{"color":"inherit","lineHeight":"1.5","fontSize":"inherit"}'>{{detail.thumbsupnum}}</text>
		  </view>
		  <view :style='{"padding":"0","margin":"0 20rpx 0 0"}'>
		    <text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"inherit","color":"inherit"}'></text>
		    <text :style='{"color":"inherit","lineHeight":"1.5","fontSize":"inheritx"}'>{{detail.storeupnum}}</text>
		  </view>
		  <view :style='{"padding":"0"}'>
		    <text class="icon iconfont icon-chakan2" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"inherit","color":"inherit"}'></text>
		    <text :style='{"color":"inherit","lineHeight":"1.5","fontSize":"inherit"}'>{{detail.clicknum}}</text>
		  </view>
		</view>
		
		<view :style='{"padding":"20rpx","margin":"0px auto","background":"#fff","display":"flex","width":"100%","justifyContent":"space-between","order":"4"}'>
		  <view :style='{"width":"50%","padding":"20rpx 50rpx","borderRadius":"40rpx 0 0 40rpx","background":"#eef7f1","justifyContent":"center","display":"flex"}' @click="likeClick">
		    <text class="icon iconfont" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#666"}' :class="likeType?'icon-zan10':'icon-zan10'"></text>
		    <text :style='{"color":"#666","lineHeight":"1.5","fontSize":"28rpx"}'>{{likeType?'已':''}}赞</text>
		  </view>
		  <view :style='{"width":"50%","padding":"20rpx 50rpx","borderRadius":"0 40rpx 40rpx 0","background":"#f0f0f0","justifyContent":"center","display":"flex"}' @click="collectClick">
		    <text class="icon iconfont" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#666"}' :class="collectType?'icon-shoucang10':'icon-shoucang10'"></text>
		    <text :style='{"color":"#666","lineHeight":"1.5","fontSize":"28rpx"}'>{{collectType?'已':''}}收藏</text>
		  </view>
		</view>
		
		<view class="content" :style='{"border":"0px solid #ddd","padding":"20rpx","margin":"0","background":"#fff","width":"100%","lineHeight":"1.5","height":"auto"}'>
			<rich-text :nodes="detail.content"></rich-text>
		</view>
		
		<div :style='{"padding":"20rpx 24rpx 40rpx","margin":"0px auto","borderRadius":"0 0 20rpx 20rpx","background":"#fff","display":"flex","width":"100%","justifyContent":"space-between","height":"auto","order":"6"}'>
		  <div @click="prepDetailClick" :style='{"padding":"0 20rpx","margin":"0 0px 0 0","color":"#fff","borderRadius":"40rpx 0 0 40rpx","textAlign":"center","background":"#2e8b57","width":"50%","lineHeight":"80rpx","fontSize":"28rpx"}'>上一篇：prev</div>
		  <div @click="nextDetailClick" :style='{"padding":"0 20rpx","color":"#666","borderRadius":"0 40rpx 40rpx 0","textAlign":"center","background":"#f0f0f0","width":"50%","lineHeight":"80rpx","fontSize":"28rpx"}'>下一篇：next</div>
		</div>
		

		<!-- 热门 -->
		<view class="hot" :style='{"border":"2rpx solid #cfe8da","padding":"24rpx 24rpx","margin":"40rpx auto","borderRadius":"20rpx","background":"linear-gradient(180deg, #2E8B57 0%, #CFE8DA 100%)","width":"calc(100% - 0px)","order":"10"}'>
		  <view :style='{"padding":"0 0 0 0px","margin":"0 0 40rpx","borderColor":"#23baf7","color":"#2e8b57","textAlign":"left","background":"none","borderWidth":"0 0 0 0px","width":"100%","fontSize":"44rpx","position":"relative","borderStyle":"solid","fontWeight":"600"}'>热门信息</view>
		  <view :style='{"padding":"0","borderRadius":"20rpx","flexWrap":"wrap","background":"none","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
		    <view v-for="(item,index) in hotList" :key="index" :style='{"padding":"20rpx","margin":"0 0 40rpx","color":"#999","borderRadius":"20rpx","flexWrap":"wrap","background":"#fff","display":"block","width":"100%","fontSize":"28rpx","justifyContent":"space-between","height":"auto"}' @click="toDetail(item.id)">
			  <image :style='{"margin":"0 20rpx 0 -20rpx","borderColor":"#2e8b57","objectFit":"cover","borderRadius":"0 20rpx 20rpx 0","borderWidth":"4rpx 4rpx 4rpx 0","display":"block","width":"240rpx","borderStyle":"solid","float":"left","height":"200rpx"}' mode="aspectFill" :src="baseUrl + item.picture"></image>
		      <view :style='{"padding":"0 0px","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"calc(100% - 240rpx)","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","float":"right"}'>{{ item.title }}</view>
		      <view :style='{"padding":"0 0 0 44rpx","margin":"0 20rpx 0 0","color":"#999","textAlign":"left","background":"linear-gradient(180deg, #2E8B57 0%, #CFE8DA 100%)","width":"calc(100% - 260rpx)","lineHeight":"48rpx","fontSize":"28rpx","float":"right"}'>{{ item.addtime }}</view>
		    </view>
		  </view>
		</view>
		<!-- 推荐 -->
		<view class="recommend" :style='{"border":"2rpx solid #cfe8da","padding":"24rpx 24rpx","margin":"40rpx auto","borderRadius":"20rpx","background":"linear-gradient(180deg, #2E8B57 0%, #CFE8DA 100%)","width":"calc(100% - 0px)","order":"12"}'>
		  <view :style='{"padding":"0 0 0 0px","margin":"0 0 40rpx","borderColor":"#23baf7","color":"#2e8b57","textAlign":"left","background":"none","borderWidth":"0 0 0 0px","width":"100%","fontSize":"44rpx","position":"relative","borderStyle":"solid","fontWeight":"600"}'>最新信息</view>
		  <view :style='{"padding":"0","borderRadius":"20rpx","flexWrap":"wrap","background":"none","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
		    <view v-for="(item,index) in recommendList" :key="index" :style='{"border":"0px solid #174add","padding":"6rpx 0","margin":"0 0 40rpx","borderRadius":"12rpx","flexWrap":"wrap","background":"#fff","display":"flex","width":"48%","height":"auto"}' @click="toDetail(item.id)">
		      <image :style='{"margin":"0","borderColor":"#2e8b57","objectFit":"cover","borderRadius":"0px","borderWidth":"4rpx 0","display":"block","width":"100%","borderStyle":"solid","height":"180rpx"}' mode="aspectFill" :src="baseUrl + item.picture"></image>
			  <view :style='{"padding":"0 8rpx","overflow":"hidden","whiteSpace":"nowrap","color":"#333","maxHeight":"96rpx","width":"calc(100% - 0px)","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","order":"-1"}'>{{ item.title }}</view>
		      <view :style='{"width":"calc(100% - 0px)","padding":"0 8rpx","lineHeight":"48rpx","fontSize":"28rpx","color":"#999","textAlign":"center"}'>{{ item.addtime }}</view>
		    </view>
		  </view>
		</view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				detail: {},
				id: '',
				likeType: false,
				likeForm: {},
				collectType: false,
				collectForm: {},
				allList: [],
				currentIndex: 0,
				hotList: [],
				recommendList: [],
			}
		},
		async onLoad(options) {
			this.id = options.id;
			this.getInfo()
			
			this.getHotList()
			this.getRecommendList()
		},
		computed:{
			baseUrl() {
				return this.$base.url;
			},
			userid(){
				return uni.getStorageSync('appUserid')
			}
		},
		methods: {
			async getInfo(){
				let res = await this.$api.info('news', this.id)
				res.data.content = res.data.content.replace(/<img/g,'<img style="width: 100%;"');
				this.detail = res.data;
				uni.pageScrollTo({
					scrollTop: 0,
					duration: 100,
				})
				this.getLike()
				this.getCollect()
				res = await this.$api.list('news',{page:1,limit:100,sort:'addtime',order:'desc'})
				for(let x in res.data.list){
					if(res.data.list[x].id == this.id){
						this.currentIndex = Number(x)
						break
					}
				}
				this.allList = res.data.list
				
			},
			// 获取点赞状态
			async getLike() {
				let res = await this.$api.list('storeup', {
					page: 1,
					limit: 1,
					type: 21,
					userid: uni.getStorageSync('appUserid'),
					refid: this.id,
					tablename: 'news'
				})
				if (res.data.list.length) {
					this.likeType = true
					this.likeForm = res.data.list[0]
				} else {
					this.likeType = false
					this.likeForm = {}
				}
			},
			// 获取热门列表
			async getHotList(){
				let res = await this.$api.recommend('news',{page:1, limit: 4,sort:'addtime',order:'desc'})
				this.hotList = res.data.list;
			},
			// 获取推荐列表	
			async getRecommendList(){
				let res;
				if(uni.getStorageSync("appUserid")){
					res = await this.$api.recommend2('news',{page:1, limit: 4,sort:'addtime',order:'desc'})
				}else{
					res = await this.$api.recommend('news',{page:1, limit: 4,sort:'addtime',order:'desc'})
				}
				
				this.recommendList = res.data.list;
			},
			// 上一篇
			prepDetailClick(){
				if(this.currentIndex == 0){
					this.$message.error('已经是第一篇了')
					return false
				}
				this.currentIndex--
				this.id = this.allList[this.currentIndex].id
				this.getInfo()
			},
			// 下一篇
			nextDetailClick(){
				if(this.currentIndex == this.allList.length - 1){
					this.$message.error('已经是最后一篇了')
					return false
				}
				this.currentIndex++
				this.id = this.allList[this.currentIndex].id
				this.getInfo()
			},
			toDetail(id){
				this.id = id
				this.getInfo()
			},
			// 获取收藏状态
			async getCollect() {
				let res = await this.$api.list('storeup', {
					page: 1,
					limit: 1,
					type: 1,
					userid: uni.getStorageSync('appUserid'),
					refid: this.id,
					tablename: 'news'
				})
				if (res.data.list.length) {
					this.collectType = true
					this.collectForm = res.data.list[0]
				} else {
					this.collectType = false
					this.collectForm = {}
				}
			},
			// 点赞按钮
			async likeClick() {
				let that = this
				if (this.likeType) {
					uni.showModal({
						title: '提示',
						content: '是否取消点赞？',
						async success(res) {
							if (res.confirm) {
								await that.$api.del('storeup', JSON.stringify([that.likeForm.id]))
								that.$utils.msg('取消成功')
								that.detail.thumbsupnum--
								await that.$api.update('news', that.detail)
								that.getLike()
							}
						}
					})
				} else {
					await that.$api.add('storeup', {
						userid: uni.getStorageSync('appUserid'),
						refid: this.id,
						type: 21,
						name: this.detail.title,
						picture: this.detail.picture,
						tablename: 'news',
					})
					this.$utils.msg('点赞成功')
					that.detail.thumbsupnum++
					await that.$api.update('news', that.detail)
					this.getLike()
				}
			},
			// 收藏按钮
			async collectClick() {
				let that = this
				if (this.collectType) {
					uni.showModal({
						title: '提示',
						content: '是否取消收藏？',
						async success(res) {
							if (res.confirm) {
								await that.$api.del('storeup', JSON.stringify([that.collectForm.id]))
								that.$utils.msg('取消成功')
								that.detail.storeupnum--
								await that.$api.update('news', that.detail)
								that.getCollect()
							}
						}
					})
				} else {
					await that.$api.add('storeup', {
						userid: uni.getStorageSync('appUserid'),
						refid: this.id,
						type: 1,
						name: this.detail.title,
						picture: this.detail.picture,
						tablename: 'news',
					})
					this.$utils.msg('收藏成功')
					that.detail.storeupnum++
					await that.$api.update('news', that.detail)
					this.getCollect()
				}
			},
		}
	}
</script>

<style scoped lang="scss">
	page {
		background: #FFFFFF;
	}
</style>
