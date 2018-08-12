package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @program: taotao
 * @description: 上传文件Controller
 * @author:
 * @create: 2018-08-11 22:37
 **/
@Controller
public class UploadController {


    @RequestMapping(value = "/pic/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestBody(required = false) String name, @ModelAttribute String str){

        System.out.println("name = [" + name + "]");
        System.out.println("str = [" + str + "]");

        return null;
    }

}
