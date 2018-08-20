/**
 * @description: 内容controller
 * @author:
 * @create: 2018-08-19 21:00
 **/
package com.taotao.controller;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 分页查询内容列表
     */
    @RequestMapping(value = "/content/query/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult listContent(Long categoryId, @RequestParam(value = "page") Integer pageNum, @RequestParam(value = "rows") Integer pageSize) {

        EasyUIDataGridResult easyUIDataGridResult = contentService.getList(categoryId, pageNum, pageSize);

        return easyUIDataGridResult;
    }
}