package com.taotao.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;


@Service
public class PictureServiceImpl implements PictureService{

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	
	
	public Map uploadPicture(MultipartFile file) {
		Map resultMap = new HashMap<String,Object>();
		//生成一个新的文件名
		String oldName = file.getOriginalFilename();
//		UUID randomUUID = UUID.randomUUID();
		String newName = IDUtils.genImageName();
		newName=newName+oldName.substring(oldName.lastIndexOf("."));
				try {
					String imagePath = new DateTime().toString("/yyyy/MM/dd"); 
					boolean uploadFile = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
							new DateTime().toString("/yyyy/MM/dd"), newName, file.getInputStream());
					if(!uploadFile){
						resultMap.put("error", 1);
						resultMap.put("message", "文件上传失败");
					}
					resultMap.put("error", 0);
					resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
					return resultMap;
				} catch (IOException e) {
					resultMap.put("error", 1);
					resultMap.put("message", "文件上传发生异常");
				}
				
		return null;
	}

}
