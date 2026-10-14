package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.KeshuEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.KeshuVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.KeshuView;


/**
 * 科属
 *
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface KeshuService extends IService<KeshuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<KeshuVO> selectListVO(Wrapper<KeshuEntity> wrapper);
   	
   	KeshuVO selectVO(@Param("ew") Wrapper<KeshuEntity> wrapper);
   	
   	List<KeshuView> selectListView(Wrapper<KeshuEntity> wrapper);
   	
   	KeshuView selectView(@Param("ew") Wrapper<KeshuEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<KeshuEntity> wrapper);

   	

}

