package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.NiaomingshibieEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.NiaomingshibieVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.NiaomingshibieView;


/**
 * 鸟鸣识别
 *
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface NiaomingshibieService extends IService<NiaomingshibieEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<NiaomingshibieVO> selectListVO(Wrapper<NiaomingshibieEntity> wrapper);
   	
   	NiaomingshibieVO selectVO(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);
   	
   	List<NiaomingshibieView> selectListView(Wrapper<NiaomingshibieEntity> wrapper);
   	
   	NiaomingshibieView selectView(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<NiaomingshibieEntity> wrapper);

   	

}

