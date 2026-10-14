package com.dao;

import com.entity.DiscussniaoleixinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussniaoleixinxiVO;
import com.entity.view.DiscussniaoleixinxiView;


/**
 * 鸟类信息评论表
 * 
 * @author 
 * @email 
 * @date 2025-02-16 12:23:47
 */
public interface DiscussniaoleixinxiDao extends BaseMapper<DiscussniaoleixinxiEntity> {
	
	List<DiscussniaoleixinxiVO> selectListVO(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);
	
	DiscussniaoleixinxiVO selectVO(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);
	
	List<DiscussniaoleixinxiView> selectListView(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);

	List<DiscussniaoleixinxiView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);

	
	DiscussniaoleixinxiView selectView(@Param("ew") Wrapper<DiscussniaoleixinxiEntity> wrapper);
	

}
