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

import com.entity.NiaomingshibieEntity;
import com.entity.view.NiaomingshibieView;

import com.service.NiaomingshibieService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import java.io.File;
import org.json.JSONObject;
import com.utils.BaiduUtil;
import org.springframework.util.ResourceUtils;
import java.io.FileNotFoundException;

/**
 * 鸟鸣识别
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
@RestController
@RequestMapping("/niaomingshibie")
public class NiaomingshibieController {
    @Autowired
    private NiaomingshibieService niaomingshibieService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,NiaomingshibieEntity niaomingshibie,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			niaomingshibie.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<NiaomingshibieEntity> ew = new EntityWrapper<NiaomingshibieEntity>();


        //查询结果
		PageUtils page = niaomingshibieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, niaomingshibie), params), params));
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
    public R list(@RequestParam Map<String, Object> params,NiaomingshibieEntity niaomingshibie, 
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NiaomingshibieEntity> ew = new EntityWrapper<NiaomingshibieEntity>();

        //查询结果
		PageUtils page = niaomingshibieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, niaomingshibie), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( NiaomingshibieEntity niaomingshibie){
       	EntityWrapper<NiaomingshibieEntity> ew = new EntityWrapper<NiaomingshibieEntity>();
      	ew.allEq(MPUtil.allEQMapPre( niaomingshibie, "niaomingshibie")); 
        return R.ok().put("data", niaomingshibieService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(NiaomingshibieEntity niaomingshibie){
        EntityWrapper< NiaomingshibieEntity> ew = new EntityWrapper< NiaomingshibieEntity>();
 		ew.allEq(MPUtil.allEQMapPre( niaomingshibie, "niaomingshibie")); 
		NiaomingshibieView niaomingshibieView =  niaomingshibieService.selectView(ew);
		return R.ok("查询鸟鸣识别成功").put("data", niaomingshibieView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        NiaomingshibieEntity niaomingshibie = niaomingshibieService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(niaomingshibie,deSens);
        return R.ok().put("data", niaomingshibie);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        NiaomingshibieEntity niaomingshibie = niaomingshibieService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(niaomingshibie,deSens);
        return R.ok().put("data", niaomingshibie);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody NiaomingshibieEntity niaomingshibie, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaomingshibie);
        niaomingshibieService.insert(niaomingshibie);
        return R.ok().put("data",niaomingshibie.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody NiaomingshibieEntity niaomingshibie, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaomingshibie);
        niaomingshibieService.insert(niaomingshibie);
        return R.ok().put("data",niaomingshibie.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody NiaomingshibieEntity niaomingshibie, HttpServletRequest request){
        //ValidatorUtils.validateEntity(niaomingshibie);
        //全部更新
        niaomingshibieService.updateById(niaomingshibie);
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        niaomingshibieService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    








    /**
     * 通用物体和场景识别
     */
    @RequestMapping("/baidu/advancedGeneral")
    @IgnoreAuth
    public R baiduAdvancedGeneral(@RequestParam("fileName") String fileName,HttpServletRequest request) {
        JSONObject jb = null;
        try {
            File path = new File(ResourceUtils.getURL("classpath:static").getPath());
            if(!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(),"/upload/");
            if(!upload.exists()) {
                upload.mkdirs();
            }
            File file = new File(upload.getAbsolutePath()+"/"+fileName);
            if(file.exists()){
                jb = BaiduUtil.advancedGeneral(file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return R.ok().put("data", com.alibaba.fastjson.JSONObject.parseObject(jb.getJSONArray("result").get(0).toString()));
    }

}
