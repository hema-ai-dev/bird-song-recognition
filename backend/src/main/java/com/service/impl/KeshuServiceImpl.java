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


import com.dao.KeshuDao;
import com.entity.KeshuEntity;
import com.service.KeshuService;
import com.entity.vo.KeshuVO;
import com.entity.view.KeshuView;

@Service("keshuService")
public class KeshuServiceImpl extends ServiceImpl<KeshuDao, KeshuEntity> implements KeshuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<KeshuEntity> page = this.selectPage(
                new Query<KeshuEntity>(params).getPage(),
                new EntityWrapper<KeshuEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<KeshuEntity> wrapper) {
		  Page<KeshuView> page =new Query<KeshuView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<KeshuVO> selectListVO(Wrapper<KeshuEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public KeshuVO selectVO(Wrapper<KeshuEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<KeshuView> selectListView(Wrapper<KeshuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public KeshuView selectView(Wrapper<KeshuEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
