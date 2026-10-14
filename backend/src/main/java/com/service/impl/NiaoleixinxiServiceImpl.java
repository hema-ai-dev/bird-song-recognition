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


import com.dao.NiaoleixinxiDao;
import com.entity.NiaoleixinxiEntity;
import com.service.NiaoleixinxiService;
import com.entity.vo.NiaoleixinxiVO;
import com.entity.view.NiaoleixinxiView;

@Service("niaoleixinxiService")
public class NiaoleixinxiServiceImpl extends ServiceImpl<NiaoleixinxiDao, NiaoleixinxiEntity> implements NiaoleixinxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<NiaoleixinxiEntity> page = this.selectPage(
                new Query<NiaoleixinxiEntity>(params).getPage(),
                new EntityWrapper<NiaoleixinxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<NiaoleixinxiEntity> wrapper) {
		  Page<NiaoleixinxiView> page =new Query<NiaoleixinxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<NiaoleixinxiVO> selectListVO(Wrapper<NiaoleixinxiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public NiaoleixinxiVO selectVO(Wrapper<NiaoleixinxiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<NiaoleixinxiView> selectListView(Wrapper<NiaoleixinxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public NiaoleixinxiView selectView(Wrapper<NiaoleixinxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
