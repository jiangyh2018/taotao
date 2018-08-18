package com.taotao.controller;

import com.taotao.pojo.UploadResult;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: taotao
 * @description: 上传文件Controller
 * @author:
 * @create: 2018-08-11 22:37
 **/
@Controller
public class UploadController {
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map upload(MultipartFile uploadFile) {
        String originalFilename = null;
        String extName = null;
        FastDFSClient fastDFSClient = null;
        byte[] content = null;
        String path = null;
        UploadResult uploadResult = null;
        Map result =null;
        try {
            originalFilename = uploadFile.getOriginalFilename();
            extName = originalFilename.substring(originalFilename.indexOf(".") + 1);

            fastDFSClient = new FastDFSClient("classpath:resource/FastDFSClient.conf");
            content = uploadFile.getBytes();
            path = IMAGE_SERVER_URL + "/" + fastDFSClient.uploadFile(content, extName);
//            System.out.println("path: " + path);
//			uploadResult=UploadResult.success(path);

            //5、返回map
            result = new HashMap<>();
            result.put("error", 0);
            result.put("url", path);
            return result;
        } catch (Exception e) {
//			uploadResult=UploadResult.error(e.getMessage());
            e.printStackTrace();

            //5、返回map
            result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }
//		return uploadResult;
    }

}
