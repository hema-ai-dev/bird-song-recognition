package com.dao;

import com.entity.NiaoleixinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.NiaoleixinxiVO;
import com.entity.view.NiaoleixinxiView;


/**
 * 鸟类信息
 * 
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface NiaoleixinxiDao extends BaseMapper<NiaoleixinxiEntity> {
	
	List<NiaoleixinxiVO> selectListVO(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);
	
	NiaoleixinxiVO selectVO(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);
	
	List<NiaoleixinxiView> selectListView(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);

	List<NiaoleixinxiView> selectListView(Pagination page,@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);

	
	NiaoleixinxiView selectView(@Param("ew") Wrapper<NiaoleixinxiEntity> wrapper);
	

}
