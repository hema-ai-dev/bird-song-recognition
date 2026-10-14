<template>
<view class="content">
	<view :style='{"minHeight":"100vh","width":"100%","padding":"24rpx","position":"relative","background":"#eef7f1","height":"auto"}'>
		<form :style='{"width":"100%","padding":"48rpx 0 48rpx","background":"none","display":"block","height":"auto"}' class="app-update-pv">
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">名称</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.mingcheng" v-model="ruleForm.mingcheng" placeholder="名称"  type="text"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">科属</view>
				<picker :disabled="ro.keshu" :style='{"width":"100%","flex":"1","height":"auto"}' @change="keshuChange" :value="keshuIndex" :range="keshuOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"30rpx","color":"#2e8b57"}' class="uni-input">{{ruleForm.keshu?ruleForm.keshu:"请选择科属"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">国家等级</view>
				<picker :disabled="ro.guojiadengji" :style='{"width":"100%","flex":"1","height":"auto"}' @change="guojiadengjiChange" :value="guojiadengjiIndex" :range="guojiadengjiOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"30rpx","color":"#2e8b57"}' class="uni-input">{{ruleForm.guojiadengji?ruleForm.guojiadengji:"请选择国家等级"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="tupianTap">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">图片</view>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.tupian" :src="baseUrl+ruleForm.tupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">特征</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.tezheng" v-model="ruleForm.tezheng" placeholder="特征"  type="text"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">生活习性</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.shenghuoxixing" v-model="ruleForm.shenghuoxixing" placeholder="生活习性"  type="text"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" input" @tap="yinpinTap">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">音频</view>
				<view class="right-input" :style='{"padding":"0px 24rpx","margin":"0px","lineHeight":"80rpx","fontSize":"28rpx","color":"#000","flex":"1"}'>
					{{ruleForm.yinpin?ruleForm.yinpin:'点击上传音频'}}
				</view>
			</view>
            
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">简介</view>
				<textarea :style='{"border":"0","padding":"24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"28rpx","height":"240rpx"}' v-model="ruleForm.jianjie" placeholder="简介" :maxlength="-1"></textarea>
			</view>
			<view :style='{"padding":"10rpx 20rpx","margin":"0","borderColor":"#1ccb96","borderRadius":"20rpx","borderWidth":"0px 0","background":"none","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"30rpx","color":"#666","fontWeight":"500"}' class="title">详情</view>
				<xia-editor ref="xiangqing" :style='{"minHeight":"300rpx","border":"2rpx solid #dfdfdf","padding":"20rpx","borderRadius":"20rpx","width":"100%","fontSize":"30rpx","height":"auto"}' v-model="ruleForm.xiangqing" placeholder="详情" @editorChange="xiangqingChange"></xia-editor>
			</view>
			
			<view :style='{"width":"100%","margin":"40rpx 0","flexDirection":"column","justifyContent":"center","display":"flex","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0","margin":"0 0 40rpx 0","color":"rgb(255, 255, 255)","borderRadius":"16rpx","background":"#2e8b57","width":"100%","lineHeight":"96rpx","fontSize":"30rpx","height":"96rpx"}' @tap="onSubmitTap" class="bg-red">提交</button>
			</view>
		</form>

	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				mingcheng: '',
				keshu: '',
				guojiadengji: '',
				tupian: '',
				tezheng: '',
				shenghuoxixing: '',
				yinpin: '',
				jianjie: '',
				xiangqing: '',
				discussnum: '',
				storeupnum: '',
				},
				keshuOptions: [],
				keshuIndex: 0,
				guojiadengjiOptions: [],
				guojiadengjiIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   mingcheng : false,
				   keshu : false,
				   guojiadengji : false,
				   tupian : false,
				   tezheng : false,
				   shenghuoxixing : false,
				   yinpin : false,
				   jianjie : false,
				   xiangqing : false,
				   clicknum : false,
				   discussnum : false,
				   storeupnum : false,
				},
				virtualPay: false,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},



		},
		async onLoad(options) {
			if(options.virtualPay){
				this.virtualPay = true
			}
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取


			// 下拉框
			res = await this.$api.option(`keshu`,`keshu`,{});
			this.keshuOptions = res.data;
			this.keshuOptions.unshift("请选择科属");
			// 自定义下拉框值
			this.guojiadengjiOptions = "一级保护动物,二级保护动物".split(',')

			// 如果有登录，获取登录后保存的userid
			this.ruleForm.userid = uni.getStorageSync("appUserid")
			if (options.refid) {
				// 如果上一级页面传递了refid，获取改refid数据信息
				this.ruleForm.refid = Number(options.refid);
				this.ruleForm.nickname = uni.getStorageSync("nickname");
			}
			// 如果是更新操作
			if (options.id) {
				this.ruleForm.id = options.id;
				// 获取信息
				res = await this.$api.info(`niaoleixinxi`, this.ruleForm.id);
				this.ruleForm = res.data;
			}
			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='mingcheng'){
						this.ruleForm.mingcheng = obj[o];
						this.ro.mingcheng = true;
						continue;
					}
					if(o=='keshu'){
						this.ruleForm.keshu = obj[o];
						this.ro.keshu = true;
						continue;
					}
					if(o=='guojiadengji'){
						this.ruleForm.guojiadengji = obj[o];
						this.ro.guojiadengji = true;
						continue;
					}
					if(o=='tupian'){
						this.ruleForm.tupian = obj[o].split(",")[0];
						this.ro.tupian = true;
						continue;
					}
					if(o=='tezheng'){
						this.ruleForm.tezheng = obj[o];
						this.ro.tezheng = true;
						continue;
					}
					if(o=='shenghuoxixing'){
						this.ruleForm.shenghuoxixing = obj[o];
						this.ro.shenghuoxixing = true;
						continue;
					}
					if(o=='yinpin'){
						this.ruleForm.yinpin = obj[o];
						this.ro.yinpin = true;
						continue;
					}
					if(o=='jianjie'){
						this.ruleForm.jianjie = obj[o];
						this.ro.jianjie = true;
						continue;
					}
					if(o=='xiangqing'){
						this.ruleForm.xiangqing = obj[o];
						this.ro.xiangqing = true;
						continue;
					}
					if(o=='clicknum'){
						this.ruleForm.clicknum = obj[o];
						this.ro.clicknum = true;
						continue;
					}
					if(o=='discussnum'){
						this.ruleForm.discussnum = obj[o];
						this.ro.discussnum = true;
						continue;
					}
					if(o=='storeupnum'){
						this.ruleForm.storeupnum = obj[o];
						this.ro.storeupnum = true;
						continue;
					}
				}
			}
			this.styleChange()
			this.$forceUpdate()
			if (uni.getStorageSync('raffleType') && uni.getStorageSync('raffleType') != null) {
				uni.removeStorageSync('raffleType')
				setTimeout(() => {
					this.onSubmitTap()
				}, 300)
			}
		},
		methods: {
			xiangqingChange(e) {
				this.ruleForm.xiangqing = e
			},
			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('.app-update-pv . .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.addUpdateForm.input.content.backgroundColor
					// })
				})
			},

			// 多级联动参数



			// 下拉变化
			keshuChange(e) {
				this.keshuIndex = e.target.value
				this.ruleForm.keshu = this.keshuOptions[this.keshuIndex]
			},
			// 下拉变化
			guojiadengjiChange(e) {
				this.guojiadengjiIndex = e.target.value
				this.ruleForm.guojiadengji = this.guojiadengjiOptions[this.guojiadengjiIndex]
			},

			tupianTap() {
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.tupian = 'upload/' + res.file;
					_this.$forceUpdate();
					_this.$nextTick(()=>{
						_this.styleChange()
					})
				});
			},
			yinpinTap () {
				let _this = this;
				this.$api.uploadMedia(function(res) {
					_this.ruleForm.yinpin = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap() {
				let that = this
				//跨表计算判断
				var obj;
				if(this.ruleForm.clicknum&&(!this.$validate.isIntNumer(this.ruleForm.clicknum))){
					this.$utils.msg(`点击次数应输入整数`);
					return
				}
				if(this.ruleForm.discussnum&&(!this.$validate.isIntNumer(this.ruleForm.discussnum))){
					this.$utils.msg(`评论数应输入整数`);
					return
				}
				if(this.ruleForm.storeupnum&&(!this.$validate.isIntNumer(this.ruleForm.storeupnum))){
					this.$utils.msg(`收藏数应输入整数`);
					return
				}
				//更新跨表属性
				var crossuserid;
				var crossrefid;
				var crossoptnum;
				if(this.cross){
					uni.setStorageSync('crossCleanType',true);
					var statusColumnName = uni.getStorageSync('statusColumnName');
					var statusColumnValue = uni.getStorageSync('statusColumnValue');
					if(statusColumnName!='') {
						if(!obj) {
							obj = uni.getStorageSync('crossObj');
						}
						if(!statusColumnName.startsWith("[")) {
							for (var o in obj){
								if(o==statusColumnName){
									obj[o] = statusColumnValue;
								}

							}
							var table = uni.getStorageSync('crossTable');
							await this.$api.update(`${table}`, obj);
						} else {
							   crossuserid=Number(uni.getStorageSync('appUserid'));
							   crossrefid=obj['id'];
							   crossoptnum=uni.getStorageSync('statusColumnName');
							   crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
						}
					}
				}
				if(crossrefid && crossuserid) {
					this.ruleForm.crossuserid=crossuserid;
					this.ruleForm.crossrefid=crossrefid;
					let params = {
						page: 1,
						limit:10,
						crossuserid:crossuserid,
						crossrefid:crossrefid,
					}
					let res = await this.$api.list(`niaoleixinxi`, params);
					if (res.data.total >= crossoptnum) {
						this.$utils.msg(uni.getStorageSync('tips'));
						uni.removeStorageSync('crossCleanType');
						return false;
					} else {
				//跨表计算
						let oet = {}
						if(this.ruleForm.id){
							await this.$api.update(`niaoleixinxi`, this.ruleForm);
						}else{
							oet = await this.$api.add(`niaoleixinxi`, this.ruleForm);
						}

						this.$utils.msgBack('提交成功');
					}
				} else {
				//跨表计算
					let oet = {}
					if(this.ruleForm.id){
						await this.$api.update(`niaoleixinxi`, this.ruleForm);
					}else{
						oet = await this.$api.add(`niaoleixinxi`, this.ruleForm);
					}

					this.$utils.msgBack('提交成功');
				}
			},
			optionsChange(e) {
				this.index = e.target.value
			},
			bindDateChange(e) {
				this.date = e.target.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			toggleTab(str) {
				if(this.ro[str]){
					return false
				}
				this.$refs[str].show();
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
