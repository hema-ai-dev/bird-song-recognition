
package com.controller;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.TokenEntity;
import com.entity.UsersEntity;
import com.service.TokenService;
import com.service.UsersService;
import com.utils.CommonUtil;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.PasswordUtil;
import com.utils.R;
import com.utils.ValidatorUtils;

/**
 * 登录相关
 */
@RequestMapping("users")
@RestController
public class UsersController{
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		UsersEntity user = userService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
		if(user==null || !PasswordUtil.matches(password, user.getPassword())) {
			return R.error("账号或密码不正确");
		}
		// 旧明文密码验证通过后自动升级为哈希存储
		if(!PasswordUtil.isHashed(user.getPassword())) {
			user.setPassword(PasswordUtil.hash(password));
			userService.updateById(user);
		}
		String token = tokenService.generateToken(user.getId(),username, "users", user.getRole());
		return R.ok().put("token", token);
	}
	
	/**
	 * 注册：密码哈希存储，角色由服务端固定，防止越权提权
	 */
	@IgnoreAuth
	@PostMapping(value = "/register")
	public R register(@RequestBody UsersEntity user){
//    	ValidatorUtils.validateEntity(user);
    	if(userService.selectOne(new EntityWrapper<UsersEntity>().eq("username", user.getUsername())) !=null) {
    		return R.error("用户已存在");
    	}
    	if(user.getPassword()==null || user.getPassword().isEmpty()) {
    		return R.error("密码不能为空");
    	}
    	user.setPassword(PasswordUtil.hash(user.getPassword()));
    	// 注册用户强制普通角色，忽略请求体传入的 role 字段
    	user.setRole("用户");
        userService.insert(user);
        return R.ok();
    }

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 密码重置：仅管理员可调用（拦截器已要求登录），重置为随机密码
     */
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
		// 仅管理员角色允许重置
		Object role = request.getSession().getAttribute("role");
		if(role==null || !"管理员".equals(role.toString())) {
			return R.error(403, "无权限操作");
		}
    	UsersEntity user = userService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
    	if(user==null) {
    		return R.error("账号不存在");
    	}
    	String newPassword = PasswordUtil.randomPassword();
    	user.setPassword(PasswordUtil.hash(newPassword));
        userService.update(user,null);
        return R.ok("密码已重置为：" + newPassword);
    }
	
	/**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,UsersEntity user){
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
    	PageUtils page = userService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.allLike(ew, user), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/list")
    public R list( UsersEntity user){
       	EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
      	ew.allEq(MPUtil.allEQMapPre( user, "user")); 
        return R.ok().put("data", userService.selectListView(ew));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        UsersEntity user = userService.selectById(id);
        return R.ok().put("data", user);
    }
    
    /**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        UsersEntity user = userService.selectById(id);
        return R.ok().put("data", user);
    }

    /**
     * 保存：密码哈希存储
     */
    @PostMapping("/save")
    public R save(@RequestBody UsersEntity user){
//    	ValidatorUtils.validateEntity(user);
    	if(userService.selectOne(new EntityWrapper<UsersEntity>().eq("username", user.getUsername())) !=null) {
    		return R.error("用户已存在");
    	}
    	if(user.getPassword()!=null && !user.getPassword().isEmpty()) {
    		user.setPassword(PasswordUtil.hash(user.getPassword()));
    	}
        userService.insert(user);
        return R.ok();
    }

    /**
     * 修改：密码字段若传入明文则哈希存储；已哈希的保持不变
     */
    @RequestMapping("/update")
    public R update(@RequestBody UsersEntity user){
//        ValidatorUtils.validateEntity(user);
    	UsersEntity u = userService.selectOne(new EntityWrapper<UsersEntity>().eq("username", user.getUsername()));
    	if(u!=null && u.getId()!=user.getId() && u.getUsername().equals(user.getUsername())) {
    		return R.error("用户名已存在。");
    	}
    	if(user.getPassword()!=null && !user.getPassword().isEmpty() && !PasswordUtil.isHashed(user.getPassword())) {
    		user.setPassword(PasswordUtil.hash(user.getPassword()));
    	}
        userService.updateById(user);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
