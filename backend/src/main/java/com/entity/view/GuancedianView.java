package com.entity.view;

import com.entity.GuancedianEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 观测点
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
@TableName("guancedian")
public class GuancedianView  extends GuancedianEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public GuancedianView(){
	}
 
 	public GuancedianView(GuancedianEntity guancedianEntity){
 	try {
			BeanUtils.copyProperties(this, guancedianEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
