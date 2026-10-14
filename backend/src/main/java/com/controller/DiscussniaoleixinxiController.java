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

import com.entity.DiscussniaoleixinxiEntity;
import com.entity.view.DiscussniaoleixinxiView;

import com.service.DiscussniaoleixinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 鸟类信息评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 12:23:47
 */
@RestController
@RequestMapping("/discussniaoleixinxi")
public class DiscussniaoleixinxiController {
    @Autowired
    private DiscussniaoleixinxiService discussniaoleixinxiService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscussniaoleixinxiEntity discussniaoleixinxi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussniaoleixinxiEntity> ew = new EntityWrapper<DiscussniaoleixinxiEntity>();


        //查询结果
		PageUtils page = discussniaoleixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussniaoleixinxi), params), params));
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
    public R list(@RequestParam Map<String, Object> params,DiscussniaoleixinxiEntity discussniaoleixinxi, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussniaoleixinxiEntity> ew = new EntityWrapper<DiscussniaoleixinxiEntity>();

        //查询结果
		PageUtils page = discussniaoleixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussniaoleixinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussniaoleixinxiEntity discussniaoleixinxi){
       	EntityWrapper<DiscussniaoleixinxiEntity> ew = new EntityWrapper<DiscussniaoleixinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussniaoleixinxi, "discussniaoleixinxi")); 
        return R.ok().put("data", discussniaoleixinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussniaoleixinxiEntity discussniaoleixinxi){
        EntityWrapper< DiscussniaoleixinxiEntity> ew = new EntityWrapper< DiscussniaoleixinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussniaoleixinxi, "discussniaoleixinxi")); 
		DiscussniaoleixinxiView discussniaoleixinxiView =  discussniaoleixinxiService.selectView(ew);
		return R.ok("查询鸟类信息评论表成功").put("data", discussniaoleixinxiView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussniaoleixinxiEntity discussniaoleixinxi = discussniaoleixinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussniaoleixinxi,deSens);
        return R.ok().put("data", discussniaoleixinxi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussniaoleixinxiEntity discussniaoleixinxi = discussniaoleixinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussniaoleixinxi,deSens);
        return R.ok().put("data", discussniaoleixinxi);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussniaoleixinxiEntity discussniaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussniaoleixinxi);
        discussniaoleixinxiService.insert(discussniaoleixinxi);
        return R.ok().put("data",discussniaoleixinxi.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussniaoleixinxiEntity discussniaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussniaoleixinxi);
        discussniaoleixinxiService.insert(discussniaoleixinxi);
        return R.ok().put("data",discussniaoleixinxi.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussniaoleixinxiEntity discussniaoleixinxi = discussniaoleixinxiService.selectOne(new EntityWrapper<DiscussniaoleixinxiEntity>().eq("", username));
        return R.ok().put("data", discussniaoleixinxi);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussniaoleixinxiEntity discussniaoleixinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussniaoleixinxi);
        //全部更新
        discussniaoleixinxiService.updateById(discussniaoleixinxi);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discussniaoleixinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscussniaoleixinxiEntity discussniaoleixinxi, HttpServletRequest request,String pre){
        EntityWrapper<DiscussniaoleixinxiEntity> ew = new EntityWrapper<DiscussniaoleixinxiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = discussniaoleixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussniaoleixinxi), params), params));
        return R.ok().put("data", page);
    }








}
