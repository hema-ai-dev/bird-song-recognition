<template>
<view class="content">
	<view :style='{"minHeight":"100vh","width":"100%","padding":"24rpx","position":"relative","background":"#eef7f1","height":"auto"}'>
		<form :style='{"width":"100%","padding":"48rpx 0 48rpx","background":"none","display":"block","height":"auto"}' class="app-update-pv">
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">标题</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.biaoti" v-model="ruleForm.biaoti" placeholder="标题"  type="text"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">时间</view>
				<picker  :disabled="ro.shijian" :style='{"width":"100%","flex":"1","height":"auto"}' mode="date" :value="ruleForm.shijian" @change="shijianChange">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"30rpx","color":"#2e8b57"}' class="uni-input">{{ruleForm.shijian?ruleForm.shijian:"请选择时间"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">位置</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.weizhi" v-model="ruleForm.weizhi" placeholder="位置"  type="text"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="tupianTap">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">图片</view>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.tupian" :src="baseUrl+ruleForm.tupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"100%","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">记录时间</view>
				<input :disabled="ro.jilushijian" :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' v-model="ruleForm.jilushijian" placeholder="记录时间" @tap="toggleTab('jilushijian')"></input>
			</view>
			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">账号</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' :disabled="ro.zhanghao" v-model="ruleForm.zhanghao" placeholder="账号"  type="text"></input>
			</view>
          			<view :style='{"padding":"4rpx 40rpx","margin":"0 0 40rpx 0","borderColor":"#dfdfdf","alignItems":"center","borderRadius":"0px","borderWidth":"0px 0 2rpx 0","background":"none","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 0","color":"#666","textAlign":"right","width":"160rpx","lineHeight":"80rpx","fontSize":"30rpx","fontWeight":"500"}' class="title">地址</view>
				<input :style='{"border":"0","padding":"0px 24rpx","margin":"0px","color":"rgb(0, 0, 0)","borderRadius":"8rpx","flex":"1","background":"rgba(255, 255, 255, 0)","fontSize":"30rpx","height":"80rpx"}' readonly @tap="fulladdressTap('')" v-model="ruleForm.fulladdress" placeholder="请选择地址"></input>
			</view>

			<view :style='{"padding":"10rpx 20rpx","margin":"0","borderColor":"#1ccb96","borderRadius":"20rpx","borderWidth":"0px 0","background":"none","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"30rpx","color":"#666","fontWeight":"500"}' class="title">记录详情</view>
				<xia-editor ref="jiluxiangqing" :style='{"minHeight":"300rpx","border":"2rpx solid #dfdfdf","padding":"20rpx","borderRadius":"20rpx","width":"100%","fontSize":"30rpx","height":"auto"}' v-model="ruleForm.jiluxiangqing" placeholder="记录详情" @editorChange="jiluxiangqingChange"></xia-editor>
			</view>
			
			<view :style='{"width":"100%","margin":"40rpx 0","flexDirection":"column","justifyContent":"center","display":"flex","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0","margin":"0 0 40rpx 0","color":"rgb(255, 255, 255)","borderRadius":"16rpx","background":"#2e8b57","width":"100%","lineHeight":"96rpx","fontSize":"30rpx","height":"96rpx"}' @tap="onSubmitTap" class="bg-red">提交</button>
			</view>
		</form>

		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="jilushijianConfirm" ref="jilushijian" themeColor="#333333"></w-picker>
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
				biaoti: '',
				shijian: '',
				weizhi: '',
				tupian: '',
				jiluxiangqing: '',
				jilushijian: '',
				zhanghao: '',
				longitude: '',
				latitude: '',
				fulladdress: '',
				},
				// 登录用户信息
				user: {},
				ro:{
				   biaoti : false,
				   shijian : false,
				   weizhi : false,
				   tupian : false,
				   jiluxiangqing : false,
				   jilushijian : false,
				   zhanghao : false,
				   longitude : false,
				   latitude : false,
				   fulladdress : false,
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
			this.ruleForm.shijian = this.$utils.getCurDate();
			this.ruleForm.jilushijian =  this.$utils.getCurDateTime();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取
			this.ruleForm.zhanghao = this.user.zhanghao
			this.ro.zhanghao = true;



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
				res = await this.$api.info(`guancedian`, this.ruleForm.id);
				this.ruleForm = res.data;
			}
			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='biaoti'){
						this.ruleForm.biaoti = obj[o];
						this.ro.biaoti = true;
						continue;
					}
					if(o=='shijian'){
						this.ruleForm.shijian = obj[o];
						this.ro.shijian = true;
						continue;
					}
					if(o=='weizhi'){
						this.ruleForm.weizhi = obj[o];
						this.ro.weizhi = true;
						continue;
					}
					if(o=='tupian'){
						this.ruleForm.tupian = obj[o].split(",")[0];
						this.ro.tupian = true;
						continue;
					}
					if(o=='jiluxiangqing'){
						this.ruleForm.jiluxiangqing = obj[o];
						this.ro.jiluxiangqing = true;
						continue;
					}
					if(o=='jilushijian'){
						this.ruleForm.jilushijian = obj[o];
						this.ro.jilushijian = true;
						continue;
					}
					if(o=='zhanghao'){
						this.ruleForm.zhanghao = obj[o];
						this.ro.zhanghao = true;
						continue;
					}
					if(o=='longitude'){
						this.ruleForm.longitude = obj[o];
						this.ro.longitude = true;
						continue;
					}
					if(o=='latitude'){
						this.ruleForm.latitude = obj[o];
						this.ro.latitude = true;
						continue;
					}
					if(o=='fulladdress'){
						this.ruleForm.fulladdress = obj[o];
						this.ro.fulladdress = true;
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
			jiluxiangqingChange(e) {
				this.ruleForm.jiluxiangqing = e
			},
			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('.app-update-pv . .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.addUpdateForm.input.content.backgroundColor
					// })
				})
			},
			fulladdressTap (name='') {
				uni.chooseLocation({
					success: (data) => {
						if(name){
							this.ruleForm[name] = data.longitude + ';' + data.latitude + ';' + data.address
							this.ruleForm[name + 2] = data.address
						}else {
							this.ruleForm.fulladdress = data.address;
							this.ruleForm.longitude = data.longitude;
							this.ruleForm.latitude = data.latitude;
						}
						this.$forceUpdate()
					}
				})
			},

			// 多级联动参数

			shijianChange(e) {
				this.ruleForm.shijian = e.target.value;
				this.$forceUpdate();
			},

			// 日长控件选择日期时间
			jilushijianConfirm(val) {
				console.log(val)
				this.ruleForm.jilushijian = val.result;
				this.$forceUpdate();
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

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap() {
				let that = this
				//跨表计算判断
				var obj;
				if((!this.ruleForm.biaoti)){
					this.$utils.msg(`标题不能为空`);
					return
				}
				if((!this.ruleForm.weizhi)){
					this.$utils.msg(`位置不能为空`);
					return
				}
				if(this.ruleForm.longitude&&(!this.$validate.isNumber(this.ruleForm.longitude))){
					this.$utils.msg(`经度应输入数字`);
					return
				}
				if(this.ruleForm.latitude&&(!this.$validate.isNumber(this.ruleForm.latitude))){
					this.$utils.msg(`纬度应输入数字`);
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
					let res = await this.$api.list(`guancedian`, params);
					if (res.data.total >= crossoptnum) {
						this.$utils.msg(uni.getStorageSync('tips'));
						uni.removeStorageSync('crossCleanType');
						return false;
					} else {
				//跨表计算
						let oet = {}
						if(this.ruleForm.id){
							await this.$api.update(`guancedian`, this.ruleForm);
						}else{
							oet = await this.$api.add(`guancedian`, this.ruleForm);
						}

						this.$utils.msgBack('提交成功');
					}
				} else {
				//跨表计算
					let oet = {}
					if(this.ruleForm.id){
						await this.$api.update(`guancedian`, this.ruleForm);
					}else{
						oet = await this.$api.add(`guancedian`, this.ruleForm);
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
