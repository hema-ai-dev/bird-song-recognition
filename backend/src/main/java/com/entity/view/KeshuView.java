package com.entity.view;

import com.entity.KeshuEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 科属
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
@TableName("keshu")
public class KeshuView  extends KeshuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public KeshuView(){
	}
 
 	public KeshuView(KeshuEntity keshuEntity){
 	try {
			BeanUtils.copyProperties(this, keshuEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
