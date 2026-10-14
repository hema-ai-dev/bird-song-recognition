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

import com.entity.NiaoleixinxiEntity;
import com.entity.view.NiaoleixinxiView;

import com.service.NiaoleixinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 鸟类信息
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
@RestController
@RequestMapping("/niaoleixinxi")
public class NiaoleixinxiController {
    @Autowired
    private NiaoleixinxiService niaoleixinxiService;

    @Autowired
    private StoreupService storeupService;





    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,NiaoleixinxiEntity niaoleixinxi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NiaoleixinxiEntity> ew = new EntityWrapper<NiaoleixinxiEntity>();


        //查询结果
		PageUtils page = niaoleixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, niaoleixinxi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,NiaoleixinxiEntity niaoleixinxi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NiaoleixinxiEntity> ew = new EntityWrapper<NiaoleixinxiEntity>();

        //查询结果
		PageUtils page = niaoleixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, niaoleixinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( NiaoleixinxiEntity niaoleixinxi){
       	EntityWrapper<NiaoleixinxiEntity> ew = new EntityWrapper<NiaoleixinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( niaoleixinxi, "niaoleixinxi")); 
        return R.ok().put("data", niaoleixinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(NiaoleixinxiEntity niaoleixinxi){
        EntityWrapper< NiaoleixinxiEntity> ew = new EntityWrapper< NiaoleixinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( niaoleixinxi, "niaoleixinxi")); 
		NiaoleixinxiView niaoleixinxiView =  niaoleixinxiService.selectView(ew);
		return R.ok("查询鸟类信息成功").put("data", niaoleixinxiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        NiaoleixinxiEntity niaoleixinxi = niaoleixinxiService.selectById(id);
		niaoleixinxi.setClicknum(niaoleixinxi.getClicknum()+1);
		niaoleixinxiService.updateById(niaoleixinxi);
        niaoleixinxi = niaoleixinxiService.selectView(new EntityWrapper<NiaoleixinxiEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(niaoleixinxi,deSens);
        return R.ok().put("data", niaoleixinxi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        NiaoleixinxiEntity niaoleixinxi = niaoleixinxiService.selectById(id);
		niaoleixinxi.setClicknum(niaoleixinxi.getClicknum()+1);
		niaoleixinxiService.updateById(niaoleixinxi);
        niaoleixinxi = niaoleixinxiService.selectView(new EntityWrapper<NiaoleixinxiEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(niaoleixinxi,deSens);
        return R.ok().put("data", niaoleixinxi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody NiaoleixinxiEntity niaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaoleixinxi);
        niaoleixinxiService.insert(niaoleixinxi);
        return R.ok().put("data",niaoleixinxi.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody NiaoleixinxiEntity niaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaoleixinxi);
        niaoleixinxiService.insert(niaoleixinxi);
        return R.ok().put("data",niaoleixinxi.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody NiaoleixinxiEntity niaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaoleixinxi);
        //全部更新
        niaoleixinxiService.updateById(niaoleixinxi);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        niaoleixinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








}
