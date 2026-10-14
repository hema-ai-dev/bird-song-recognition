package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.NiaoleixinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.NiaoleixinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.NiaoleixinxiView;


/**
 * 鸟类信息
 *
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface NiaoleixinxiService extends IService<NiaoleixinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<NiaoleixinxiVO> selectListVO(Wrapper<NiaoleixinxiEntity> wrapper);
   	
   	NiaoleixinxiVO selectVO(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);
   	
   	List<NiaoleixinxiView> selectListView(Wrapper<NiaoleixinxiEntity> wrapper);
   	
   	NiaoleixinxiView selectView(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<NiaoleixinxiEntity> wrapper);

   	

}

