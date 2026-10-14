import Vue from 'vue';
//配置路由
import VueRouter from 'vue-router'
Vue.use(VueRouter);
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import register from '@/views/register'
import center from '@/views/center'
	import news from '@/views/modules/news/list'
	import keshu from '@/views/modules/keshu/list'
	import yonghu from '@/views/modules/yonghu/list'
	import discussniaoleixinxi from '@/views/modules/discussniaoleixinxi/list'
	import niaoleixinxi from '@/views/modules/niaoleixinxi/list'
	import storeup from '@/views/modules/storeup/list'
	import niaomingshibie from '@/views/modules/niaomingshibie/list'
	import config from '@/views/modules/config/list'
	import guancedian from '@/views/modules/guancedian/list'
	import newstype from '@/views/modules/newstype/list'


//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		// 这里不设置值，是把main作为默认页面
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}
	,{
		path: '/news',
		name: '新闻资讯',
		component: news
	}
	,{
		path: '/keshu',
		name: '科属',
		component: keshu
	}
	,{
		path: '/yonghu',
		name: '用户',
		component: yonghu
	}
	,{
		path: '/discussniaoleixinxi',
		name: '鸟类信息评论',
		component: discussniaoleixinxi
	}
	,{
		path: '/niaoleixinxi',
		name: '鸟类信息',
		component: niaoleixinxi
	}
	,{
		path: '/storeup',
		name: '我的收藏',
		component: storeup
	}
	,{
		path: '/niaomingshibie',
		name: '鸟鸣识别',
		component: niaomingshibie
	}
	,{
		path: '/config',
		name: '轮播图管理',
		component: config
	}
	,{
		path: '/guancedian',
		name: '观测点',
		component: guancedian
	}
	,{
		path: '/newstype',
		name: '资讯分类',
		component: newstype
	}
	]
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	/*hash模式改为history*/
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
