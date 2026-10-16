package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Autowired
    private ConfigService configService;
	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
    @IgnoreAuth
	public R upload(@RequestParam("file") MultipartFile file,String type) throws Exception {
		if (file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		// 扩展名白名单校验，防止上传可执行文件
		if (!fileExt.matches("[A-Za-z0-9]{1,10}")) {
			throw new EIException("非法文件类型");
		}
		// 上传目录统一为 {user.dir}/upload，jar与源码模式一致，并由yml的 file:upload/ 映射对外访问
		File upload = new File(System.getProperty("user.dir") + File.separator + "upload");
		if(!upload.exists()) {
		    upload.mkdirs();
		}
		String fileName = new Date().getTime()+"."+fileExt;
        if(StringUtils.isNotBlank(type) && type.contains("_template")) {
            // 防路径注入：type 仅允许字母数字下划线
            if (!type.matches("[A-Za-z0-9_]+")) {
                throw new EIException("非法文件类型");
            }
            fileName = type + "."+fileExt;
            new File(upload.getAbsolutePath()+"/"+fileName).deleteOnExit();
        }
		File dest = new File(upload.getAbsolutePath()+"/"+fileName);
		file.transferTo(dest);
		/**
  		 * 如果使用idea或者eclipse重启项目，发现之前上传的图片或者文件丢失，将下面一行代码注释打开
   		 * 请将以下的"D:\\springbootq33sd\\src\\main\\resources\\static\\upload"替换成你本地项目的upload路径，
 		 * 并且项目路径不能存在中文、空格等特殊字符
 		 */
//		FileUtils.copyFile(dest, new File("D:\\biyesheji\\springboot05qmpxx3\\src\\main\\resources\\static\\upload"+"/"+fileName)); /**修改了路径以后请将该行最前面的//注释去掉**/
		if(StringUtils.isNotBlank(type) && type.equals("1")) {
			ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
			if(configEntity==null) {
				configEntity = new ConfigEntity();
				configEntity.setName("faceFile");
				configEntity.setValue(fileName);
			} else {
				configEntity.setValue(fileName);
			}
			configService.insertOrUpdate(configEntity);
		}
		return R.ok().put("file", fileName);
	}

	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		try {
			// 路径穿越防护：文件名必须为纯文件名
			if (fileName == null || !fileName.matches("[A-Za-z0-9._\\-]{1,255}")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			File upload = new File(System.getProperty("user.dir") + File.separator + "upload");
			File file = new File(upload, fileName);
			if(file.exists() && file.getCanonicalPath().startsWith(upload.getCanonicalPath() + File.separator)){
				/*if(!fileService.canRead(file, SessionManager.getSessionUser())){
					getResponse().sendError(403);
				}*/
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
			    headers.setContentDispositionFormData("attachment", fileName);    
			    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
