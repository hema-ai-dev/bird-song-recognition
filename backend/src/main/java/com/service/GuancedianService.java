package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.GuancedianEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.GuancedianVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.GuancedianView;


/**
 * 观测点
 *
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface GuancedianService extends IService<GuancedianEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<GuancedianVO> selectListVO(Wrapper<GuancedianEntity> wrapper);
   	
   	GuancedianVO selectVO(@Param("ew") Wrapper<GuancedianEntity> wrapper);
   	
   	List<GuancedianView> selectListView(Wrapper<GuancedianEntity> wrapper);
   	
   	GuancedianView selectView(@Param("ew") Wrapper<GuancedianEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<GuancedianEntity> wrapper);

   	

}

