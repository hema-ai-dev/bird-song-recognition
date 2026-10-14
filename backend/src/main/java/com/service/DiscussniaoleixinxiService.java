package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussniaoleixinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussniaoleixinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussniaoleixinxiView;


/**
 * 鸟类信息评论表
 *
 * @author 
 * @email 
 * @date 2025-02-16 12:23:47
 */
public interface DiscussniaoleixinxiService extends IService<DiscussniaoleixinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussniaoleixinxiVO> selectListVO(Wrapper<DiscussniaoleixinxiEntity> wrapper);
   	
   	DiscussniaoleixinxiVO selectVO(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);
   	
   	List<DiscussniaoleixinxiView> selectListView(Wrapper<DiscussniaoleixinxiEntity> wrapper);
   	
   	DiscussniaoleixinxiView selectView(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussniaoleixinxiEntity> wrapper);

   	

}

