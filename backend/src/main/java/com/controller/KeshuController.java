package com.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import cn.hutool.core.bean.BeanUtil;
import com.entity.NiaoleixinxiEntity;
import com.entity.view.NiaoleixinxiView;
import com.service.NiaoleixinxiService;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.KeshuEntity;
import com.entity.view.KeshuView;

import com.service.KeshuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 科属
 * 后端接口
 *
 * @author
 * @email
 * @date 2025-02-16 12:23:46
 */
@RestController
@RequestMapping("/keshu")
public class KeshuController {
    @Autowired
    private KeshuService keshuService;

    @Autowired
    private NiaoleixinxiService niaoleixinxiService;


    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, KeshuEntity keshu,
                  HttpServletRequest request) {
        //设置查询条件
        EntityWrapper<KeshuEntity> ew = new EntityWrapper<KeshuEntity>();


        //查询结果
        PageUtils page = keshuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, keshu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page, deSens);
        return R.ok().put("data", page);
    }

    /**
     * 前台列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, KeshuEntity keshu,
                  HttpServletRequest request) {
        //设置查询条件
        EntityWrapper<KeshuEntity> ew = new EntityWrapper<KeshuEntity>();

        //查询结果
        PageUtils page = keshuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, keshu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page, deSens);
        return R.ok().put("data", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(KeshuEntity keshu) {
        EntityWrapper<KeshuEntity> ew = new EntityWrapper<KeshuEntity>();
        ew.allEq(MPUtil.allEQMapPre(keshu, "keshu"));
        return R.ok().put("data", keshuService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(KeshuEntity keshu) {
        EntityWrapper<KeshuEntity> ew = new EntityWrapper<KeshuEntity>();
        ew.allEq(MPUtil.allEQMapPre(keshu, "keshu"));
        KeshuView keshuView = keshuService.selectView(ew);
        return R.ok("查询科属成功").put("data", keshuView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        KeshuEntity keshu = keshuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(keshu, deSens);
        return R.ok().put("data", keshu);
    }

    /**
     * 前台详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        KeshuEntity keshu = keshuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(keshu, deSens);
        return R.ok().put("data", keshu);
    }


    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody KeshuEntity keshu, HttpServletRequest request) {
        //验证字段唯一性，否则返回错误信息
        if (keshuService.selectCount(new EntityWrapper<KeshuEntity>().eq("keshu", keshu.getKeshu())) > 0) {
            return R.error("科属已存在");
        }
        //ValidatorUtils.validateEntity(keshu);
        keshuService.insert(keshu);
        return R.ok().put("data", keshu.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody KeshuEntity keshu, HttpServletRequest request) {
        //验证字段唯一性，否则返回错误信息
        if (keshuService.selectCount(new EntityWrapper<KeshuEntity>().eq("keshu", keshu.getKeshu())) > 0) {
        // if (keshuService.selectCount(new EntityWrapper<KeshuEntity>().like("mingcheng", niaoleixinxi.getMingcheng())) > 0) {
            return R.error("科属已存在");
        }
        //ValidatorUtils.validateEntity(keshu);
        keshuService.insert(keshu);
        return R.ok().put("data", keshu.getId());
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody KeshuEntity keshu, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(keshu);
        //验证字段唯一性，否则返回错误信息
        if (keshuService.selectCount(new EntityWrapper<KeshuEntity>().ne("id", keshu.getId()).eq("keshu", keshu.getKeshu())) > 0) {
            return R.error("科属已存在");
        }
        //全部更新
        keshuService.updateById(keshu);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        keshuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    @RequestMapping("/bird_recognition")
    public R birdRecognition(HttpServletRequest request) throws IOException, InterruptedException {
        String wavFileName = request.getParameter("data");
        // 路径穿越防护：文件名必须为纯文件名，且规范化后必须位于 upload 目录内
        if (wavFileName == null || wavFileName.isEmpty()
                || !wavFileName.matches("[A-Za-z0-9._\\-]{1,255}")) {
            return R.error("音频文件名不合法");
        }
        File uploadDir = new File(System.getProperty("user.dir") + File.separator + "upload");
        File wavFile = new File(uploadDir, wavFileName);
        if (!wavFile.getCanonicalPath().startsWith(uploadDir.getCanonicalPath() + File.separator)) {
            return R.error("音频文件名不合法");
        }
        if (!wavFile.exists()) {
            return R.error("音频文件不存在");
        }

        // AI 环境路径优先从环境变量读取；未配置时兼容从仓库根目录或 backend/ 目录启动。
        String aiDir = System.getenv("BIRD_AI_DIR");
        if (aiDir == null || aiDir.isEmpty()) {
            File workDir = new File(System.getProperty("user.dir"));
            File aiHome = new File(workDir, "ai");
            if (!aiHome.isDirectory() && workDir.getParentFile() != null) {
                aiHome = new File(workDir.getParentFile(), "ai");
            }
            aiDir = aiHome.getCanonicalPath();
        }

        String python = System.getenv("BIRD_PYTHON");
        if (python == null || python.isEmpty()) {
            python = new File(new File(aiDir, "python"), "python.exe").getPath();
        }

        // 构建命令和参数
        ProcessBuilder builder = new ProcessBuilder(python,
                aiDir + File.separator + "handle_wav.py", wavFile.getPath()); // 确保替换为你的Python脚本路径和名称
        builder.redirectErrorStream(true); // 将错误输出和标准输出合并，便于读取
        Process process = builder.start();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor(); // 等待第一个命令完成

        ProcessBuilder builder_predict = new ProcessBuilder(python,
                aiDir + File.separator + "predict_with_torch.py"); // 确保替换为你的Python脚本路径和名称
        builder_predict.redirectErrorStream(true); // 将错误输出和标准输出合并，便于读取
        Process process_predict = builder_predict.start();

        // 读取输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process_predict.getInputStream()));
        String line;
        String type_code = "B000";
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("B0")) {
                type_code = line;
            }
        }

        String typeName = "旅鸫";
        if (type_code.equals("B000"))
            typeName = "旅鸫";
        else if (type_code.equals("B001"))
            typeName = "比氏苇鹪鹩";
        else if (type_code.equals("B002"))
            typeName = "北美红雀";
        else if (type_code.equals("B003"))
            typeName = "小嘲鸫";
        else if (type_code.equals("B004"))
            typeName = "歌带鹀";
        else if (type_code.equals("B005"))
            typeName = "苍鹭";
        else if (type_code.equals("B006"))
            typeName = "大天鹅";
        else if (type_code.equals("B007"))
            typeName = "凤头麦鸡";
        else if (type_code.equals("B008"))
            typeName = "黑翅长脚鹬";
        else if (type_code.equals("B009"))
            typeName = "红脚鹬";
        else if (type_code.equals("B010"))
            typeName = "林鹬";
        else if (type_code.equals("B011"))
            typeName = "绿头鸭";
        else if (type_code.equals("B012"))
            typeName = "麻雀";
        else if (type_code.equals("B013"))
            typeName = "普通鸬鹚";
        else if (type_code.equals("B014"))
            typeName = "雉鸡";

        // 等待进程结束
//        int exitCode = process.waitFor();
//        System.out.println("Exited with code : " + exitCode);


        EntityWrapper<NiaoleixinxiEntity> ew = new EntityWrapper<NiaoleixinxiEntity>();
        NiaoleixinxiEntity niaoleixinxiEntity = new NiaoleixinxiEntity();
        niaoleixinxiEntity.setMingcheng(typeName);
        Map result = BeanUtil.beanToMap(niaoleixinxiEntity, true, true);
        Wrapper wrapper = MPUtil.genLike(ew, result);
        List<NiaoleixinxiView> niaoleixinxiViews = niaoleixinxiService.selectListView(wrapper);

        return R.ok().put("data", niaoleixinxiViews);
    }

}
