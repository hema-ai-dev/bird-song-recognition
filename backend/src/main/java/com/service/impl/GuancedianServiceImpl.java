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


import com.dao.GuancedianDao;
import com.entity.GuancedianEntity;
import com.service.GuancedianService;
import com.entity.vo.GuancedianVO;
import com.entity.view.GuancedianView;

@Service("guancedianService")
public class GuancedianServiceImpl extends ServiceImpl<GuancedianDao, GuancedianEntity> implements GuancedianService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GuancedianEntity> page = this.selectPage(
                new Query<GuancedianEntity>(params).getPage(),
                new EntityWrapper<GuancedianEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<GuancedianEntity> wrapper) {
		  Page<GuancedianView> page =new Query<GuancedianView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<GuancedianVO> selectListVO(Wrapper<GuancedianEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public GuancedianVO selectVO(Wrapper<GuancedianEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<GuancedianView> selectListView(Wrapper<GuancedianEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public GuancedianView selectView(Wrapper<GuancedianEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
