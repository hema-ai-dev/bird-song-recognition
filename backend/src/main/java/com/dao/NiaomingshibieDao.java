package com.dao;

import com.entity.NiaomingshibieEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.NiaomingshibieVO;
import com.entity.view.NiaomingshibieView;


/**
 * 鸟鸣识别
 * 
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public interface NiaomingshibieDao extends BaseMapper<NiaomingshibieEntity> {
	
	List<NiaomingshibieVO> selectListVO(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);
	
	NiaomingshibieVO selectVO(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);
	
	List<NiaomingshibieView> selectListView(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);

	List<NiaomingshibieView> selectListView(Pagination page,@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);

	
	NiaomingshibieView selectView(@Param("ew") Wrapper<NiaomingshibieEntity> wrapper);
	

}
