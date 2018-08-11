package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: taotao
 * @description: 上传文件Controller
 * @author:
 * @create: 2018-08-11 22:37
 **/
@Controller
public class UploadController {


    @RequestMapping(value = "/pic/upload",method = RequestMethod.POST)
    public String upload(){

        return null;
    }

}
