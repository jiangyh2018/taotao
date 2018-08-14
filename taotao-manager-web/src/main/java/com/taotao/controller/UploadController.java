package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.pojo.UploadResult;
import com.taotao.utils.FastDFSClient;

/**
 * @program: taotao
 * @description: 上传文件Controller
 * @author:
 * @create: 2018-08-11 22:37
 **/
@Controller
public class UploadController {

	@RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
	@ResponseBody
	public UploadResult upload(MultipartFile uploadFile) {
		String originalFilename = null;
		String extName = null;
		FastDFSClient fastDFSClient = null;
		byte[] content =null;
		String path=null;
		UploadResult uploadResult=null;
		try {
			originalFilename = uploadFile.getOriginalFilename();
			extName = originalFilename.substring(originalFilename.indexOf(".") + 1);
			
			fastDFSClient = new FastDFSClient("classpath:resource/FastDFSClient.conf");
			content = uploadFile.getBytes();
			path=fastDFSClient.uploadFile(content, extName);
			System.out.println("path: "+path);
			uploadResult=UploadResult.success(path);
		} catch (Exception e) {
			uploadResult=UploadResult.error(e.getMessage());
			e.printStackTrace();
		}
		return uploadResult;
	}

}
