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


import com.dao.DiscussniaoleixinxiDao;
import com.entity.DiscussniaoleixinxiEntity;
import com.service.DiscussniaoleixinxiService;
import com.entity.vo.DiscussniaoleixinxiVO;
import com.entity.view.DiscussniaoleixinxiView;

@Service("discussniaoleixinxiService")
public class DiscussniaoleixinxiServiceImpl extends ServiceImpl<DiscussniaoleixinxiDao, DiscussniaoleixinxiEntity> implements DiscussniaoleixinxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussniaoleixinxiEntity> page = this.selectPage(
                new Query<DiscussniaoleixinxiEntity>(params).getPage(),
                new EntityWrapper<DiscussniaoleixinxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussniaoleixinxiEntity> wrapper) {
		  Page<DiscussniaoleixinxiView> page =new Query<DiscussniaoleixinxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscussniaoleixinxiVO> selectListVO(Wrapper<DiscussniaoleixinxiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussniaoleixinxiVO selectVO(Wrapper<DiscussniaoleixinxiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussniaoleixinxiView> selectListView(Wrapper<DiscussniaoleixinxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussniaoleixinxiView selectView(Wrapper<DiscussniaoleixinxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
