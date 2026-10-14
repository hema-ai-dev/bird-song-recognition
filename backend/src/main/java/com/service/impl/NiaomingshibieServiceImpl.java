package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.NiaomingshibieDao;
import com.entity.NiaomingshibieEntity;
import com.service.NiaomingshibieService;
import com.entity.vo.NiaomingshibieVO;
import com.entity.view.NiaomingshibieView;

@Service("niaomingshibieService")
public class NiaomingshibieServiceImpl extends ServiceImpl<NiaomingshibieDao, NiaomingshibieEntity> implements NiaomingshibieService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<NiaomingshibieEntity> page = this.selectPage(
                new Query<NiaomingshibieEntity>(params).getPage(),
                new EntityWrapper<NiaomingshibieEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<NiaomingshibieEntity> wrapper) {
		  Page<NiaomingshibieView> page =new Query<NiaomingshibieView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<NiaomingshibieVO> selectListVO(Wrapper<NiaomingshibieEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public NiaomingshibieVO selectVO(Wrapper<NiaomingshibieEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<NiaomingshibieView> selectListView(Wrapper<NiaomingshibieEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public NiaomingshibieView selectView(Wrapper<NiaomingshibieEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
