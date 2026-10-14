import http from './http'
import base from './base'

/**
 * 登陆
 */
export const login = (tableName, data) => {
	return http.request({
		url: `${tableName}/login`,
		method: 'GET',
		data
	})
}
/**
 *  * 注册
 *   */
export const register = (tableName, data) => {
	let url = `${tableName}/register`;
	return http.request({
		url: url,
		method: 'POST',
		data
	})
}
/**
 * 获取登陆用户信息
 */
export const session = (tableName) => {
	if(!tableName){
		return false
	}
	return http.request({
		url: `${tableName}/session`,
		method: 'GET'
	})
}
/**
 * 查询
 */
export const list = (tableName, data) => {
	return http.request({
		url: `${tableName}/list`,
		method: 'GET',
		data
	});
}
/**
 * 查询
 */
export const lists = (tableName, data) => {
	return http.request({
		url: `${tableName}/lists`,
		method: 'GET',
		data
	});
}
/**
 * 查询 page
 */
export const page = (tableName, data) => {
	return http.request({
		url: `${tableName}/page`,
		method: 'GET',
		data
	});
}
/**
 * 保存
 */
export const add = (tableName, data) => {
	return http.request({
		url: `${tableName}/add`,
		method: 'POST',
		data
	});
}
// 保存
export const save = (tableName, data) => {
	return http.request({
		url: `${tableName}/save`,
		method: 'POST',
		data
	});
}
/**
 * 更新
 */
export const update = (tableName, data) => {
	return http.request({
		url: `${tableName}/update`,
		method: 'POST',
		data
	});
}
/**
 * 删除
 */
export const del = (tableName, data) => {
	return http.request({
		url: `${tableName}/delete`,
		method: 'POST',
		data
	});
}
/**
 * 单条信息查询
 */
export const info = (tableName, id) => {
	return http.request({
		url: `${tableName}/detail/${id}`,
		method: 'GET'
	});
}
/**
 * 智能推荐
 */
export const recommend = (tableName,data) => {
	return http.request({
		url: `${tableName}/autoSort`,
		method: 'get',
		data
	});
}
/**
 * 智能推荐(按购买类型推荐)
 */
export const recommend2 = (tableName,data) => {
	return http.request({
		url: `${tableName}/autoSort2`,
		method: 'get',
		data
	});
}
/**
 * 鸟鸣识别
 */
export const birdRecognition = (data) => {
	return http.request({
		url: `keshu/bird_recognition`,
		method: 'get',
		data:{data:data}
	});
}
/**
 * 上传
 */
export const upload = (callback) => {
	uni.chooseImage({
		count: 4, //默认9
		sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
		//sourceType: ['album'], //从相册选择
		success: (res) => {
			uni.uploadFile({
				url: `${base.url}file/upload`, //仅为示例，非真实的接口地址
				filePath: res.tempFilePaths[0],
				name: 'file',
				header: {
					'Token': uni.getStorageSync("appToken")
				},
				success: (uploadFileRes) => {
					// console.log(uploadFileRes)
					let result = JSON.parse(uploadFileRes.data);
					if (result.code == 0) {
						callback(result);
					} else {
						uni.showToast({
							title: result.msg,
							icon: 'none',
							duration: 2000
						});
					}
				}
			});
		}
	});
}

/**
 * 上传文件（所有类型）
 */
export const uploadFile = (callback) => {
	uni.chooseFile({
		count: 1, //默认9
		type:'all',
		success: (res) => {
			uni.uploadFile({
				url: `${base.url}file/upload`, //仅为示例，非真实的接口地址
				filePath: res.tempFilePaths[0],
				name: 'file',
				header: {
					'Token': uni.getStorageSync("appToken")
				},
				success: (uploadFileRes) => {
					// console.log(uploadFileRes)
					let result = JSON.parse(uploadFileRes.data);
					if (result.code == 0) {
						callback(result);
					} else {
						uni.showToast({
							title: result.msg,
							icon: 'none',
							duration: 2000
						});
					}
				}
			});
		}
	});
}
/**
 * 百度识别（文字，动物，植物，菜品）
 */
export const baiduIdentify = (tableName, type, fileName) => {
	return http.request({
		url: `${tableName}/baidu/${type}?fileName=`+fileName,
		method: 'GET'
	});
}

export const uploadMedia = (callback) => {
	uni.chooseVideo({
		count: 1,
		sourceType: ['camera', 'album'],
		success: function(res) {
			uni.uploadFile({
				url: `${base.url}file/upload`, //仅为示例，非真实的接口地址
				filePath: res.tempFilePath,
				name: 'file',
				header: {
					'Token': uni.getStorageSync("appToken")
				},
				success: (uploadFileRes) => {
					// console.log(uploadFileRes)
					let result = JSON.parse(uploadFileRes.data);
					if (result.code == 0) {
						callback(result);
					} else {
						uni.showToast({
							title: result.msg,
							icon: 'none',
							duration: 2000
						});
					}
				}
			});
		}
	});
}

/**
 * 联动查询
 * @param {*} tableName 
 * @param {*} columnName 
 * @param {*} data 
 */
export const option = (tableName, columnName, data) => {
	return http.request({
		url: `option/${tableName}/${columnName}`,
		method: 'GET',
		data
	});
}
export const security = (tableName, data) => {
	return http.request({
		url: `${tableName}/security`,
		method: 'GET',
		data
	});
}
export default {
	login, // 登陆
	register, //注册
	session, // 个人信息
	list, // 查询
	lists,
	page, // 查询 page
	add, // 添加
	update, // 修改
	del, // 删除
	info, // 单条信息查询,
	recommend, // 智能推荐
	recommend2, // 智能推荐(按购买类型推荐)
	save, // 保存
	upload, // 上传
	uploadFile,//上传文件（所有类型）
	baiduIdentify,
	option, // 联动查询
	uploadMedia,
	security,
	birdRecognition
}
