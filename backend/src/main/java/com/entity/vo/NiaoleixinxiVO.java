package com.entity.vo;

import com.entity.NiaoleixinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 鸟类信息
 * @author 
 * @email 
 * @date 2025-02-16 12:23:46
 */
public class NiaoleixinxiVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 科属
	 */
	
	private String keshu;
		
	/**
	 * 国家等级
	 */
	
	private String guojiadengji;
		
	/**
	 * 图片
	 */
	
	private String tupian;
		
	/**
	 * 特征
	 */
	
	private String tezheng;
		
	/**
	 * 生活习性
	 */
	
	private String shenghuoxixing;
		
	/**
	 * 音频
	 */
	
	private String yinpin;
		
	/**
	 * 简介
	 */
	
	private String jianjie;
		
	/**
	 * 详情
	 */
	
	private String xiangqing;
		
	/**
	 * 点击次数
	 */
	
	private Integer clicknum;
		
	/**
	 * 评论数
	 */
	
	private Integer discussnum;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：科属
	 */
	 
	public void setKeshu(String keshu) {
		this.keshu = keshu;
	}
	
	/**
	 * 获取：科属
	 */
	public String getKeshu() {
		return keshu;
	}
				
	
	/**
	 * 设置：国家等级
	 */
	 
	public void setGuojiadengji(String guojiadengji) {
		this.guojiadengji = guojiadengji;
	}
	
	/**
	 * 获取：国家等级
	 */
	public String getGuojiadengji() {
		return guojiadengji;
	}
				
	
	/**
	 * 设置：图片
	 */
	 
	public void setTupian(String tupian) {
		this.tupian = tupian;
	}
	
	/**
	 * 获取：图片
	 */
	public String getTupian() {
		return tupian;
	}
				
	
	/**
	 * 设置：特征
	 */
	 
	public void setTezheng(String tezheng) {
		this.tezheng = tezheng;
	}
	
	/**
	 * 获取：特征
	 */
	public String getTezheng() {
		return tezheng;
	}
				
	
	/**
	 * 设置：生活习性
	 */
	 
	public void setShenghuoxixing(String shenghuoxixing) {
		this.shenghuoxixing = shenghuoxixing;
	}
	
	/**
	 * 获取：生活习性
	 */
	public String getShenghuoxixing() {
		return shenghuoxixing;
	}
				
	
	/**
	 * 设置：音频
	 */
	 
	public void setYinpin(String yinpin) {
		this.yinpin = yinpin;
	}
	
	/**
	 * 获取：音频
	 */
	public String getYinpin() {
		return yinpin;
	}
				
	
	/**
	 * 设置：简介
	 */
	 
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	
	/**
	 * 获取：简介
	 */
	public String getJianjie() {
		return jianjie;
	}
				
	
	/**
	 * 设置：详情
	 */
	 
	public void setXiangqing(String xiangqing) {
		this.xiangqing = xiangqing;
	}
	
	/**
	 * 获取：详情
	 */
	public String getXiangqing() {
		return xiangqing;
	}
				
	
	/**
	 * 设置：点击次数
	 */
	 
	public void setClicknum(Integer clicknum) {
		this.clicknum = clicknum;
	}
	
	/**
	 * 获取：点击次数
	 */
	public Integer getClicknum() {
		return clicknum;
	}
				
	
	/**
	 * 设置：评论数
	 */
	 
	public void setDiscussnum(Integer discussnum) {
		this.discussnum = discussnum;
	}
	
	/**
	 * 获取：评论数
	 */
	public Integer getDiscussnum() {
		return discussnum;
	}
				
	
	/**
	 * 设置：收藏数
	 */
	 
	public void setStoreupnum(Integer storeupnum) {
		this.storeupnum = storeupnum;
	}
	
	/**
	 * 获取：收藏数
	 */
	public Integer getStoreupnum() {
		return storeupnum;
	}
			
}
