package com.dao;

import com.entity.GuancedianEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.GuancedianVO;
import com.entity.view.GuancedianView;


/**
 * 观测点
 * 
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface GuancedianDao extends BaseMapper<GuancedianEntity> {
	
	List<GuancedianVO> selectListVO(@Param("ew") Wrapper<GuancedianEntity> wrapper);
	
	GuancedianVO selectVO(@Param("ew") Wrapper<GuancedianEntity> wrapper);
	
	List<GuancedianView> selectListView(@Param("ew") Wrapper<GuancedianEntity> wrapper);

	List<GuancedianView> selectListView(Pagination page,@Param("ew") Wrapper<GuancedianEntity> wrapper);

	
	GuancedianView selectView(@Param("ew") Wrapper<GuancedianEntity> wrapper);
	

}
