package com.dao;

import com.entity.KeshuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.KeshuVO;
import com.entity.view.KeshuView;


/**
 * 科属
 * 
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface KeshuDao extends BaseMapper<KeshuEntity> {
	
	List<KeshuVO> selectListVO(@Param("ew") Wrapper<KeshuEntity> wrapper);
	
	KeshuVO selectVO(@Param("ew") Wrapper<KeshuEntity> wrapper);
	
	List<KeshuView> selectListView(@Param("ew") Wrapper<KeshuEntity> wrapper);

	List<KeshuView> selectListView(Pagination page,@Param("ew") Wrapper<KeshuEntity> wrapper);

	
	KeshuView selectView(@Param("ew") Wrapper<KeshuEntity> wrapper);
	

}
