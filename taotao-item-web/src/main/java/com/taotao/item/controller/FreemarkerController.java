package com.taotao.item.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 利用freemarker生成静态html文件
 * @author:
 * @create: 2018-10-01 20:51
 **/
public class FreemarkerController {

    /**
     * 生成静态html方法
     * @return
     */
    @RequestMapping(value = "genHtml")
    @ResponseBody
    public String genHtml() {
        


        return "";
    }


}