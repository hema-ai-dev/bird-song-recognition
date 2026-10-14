package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.GuancedianEntity;
import com.entity.view.GuancedianView;

import com.service.GuancedianService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 观测点
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
@RestController
@RequestMapping("/guancedian")
public class GuancedianController {
    @Autowired
    private GuancedianService guancedianService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,GuancedianEntity guancedian,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			guancedian.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<GuancedianEntity> ew = new EntityWrapper<GuancedianEntity>();


        //查询结果
		PageUtils page = guancedianService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, guancedian), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,GuancedianEntity guancedian, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<GuancedianEntity> ew = new EntityWrapper<GuancedianEntity>();

        //查询结果
		PageUtils page = guancedianService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, guancedian), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( GuancedianEntity guancedian){
       	EntityWrapper<GuancedianEntity> ew = new EntityWrapper<GuancedianEntity>();
      	ew.allEq(MPUtil.allEQMapPre( guancedian, "guancedian")); 
        return R.ok().put("data", guancedianService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(GuancedianEntity guancedian){
        EntityWrapper< GuancedianEntity> ew = new EntityWrapper< GuancedianEntity>();
 		ew.allEq(MPUtil.allEQMapPre( guancedian, "guancedian")); 
		GuancedianView guancedianView =  guancedianService.selectView(ew);
		return R.ok("查询观测点成功").put("data", guancedianView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        GuancedianEntity guancedian = guancedianService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(guancedian,deSens);
        return R.ok().put("data", guancedian);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        GuancedianEntity guancedian = guancedianService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(guancedian,deSens);
        return R.ok().put("data", guancedian);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GuancedianEntity guancedian, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guancedian);
        guancedianService.insert(guancedian);
        return R.ok().put("data",guancedian.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody GuancedianEntity guancedian, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guancedian);
        guancedianService.insert(guancedian);
        return R.ok().put("data",guancedian.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody GuancedianEntity guancedian, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guancedian);
        //全部更新
        guancedianService.updateById(guancedian);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        guancedianService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
