/**
 * @description: 内容controller
 * @author:
 * @create: 2018-08-19 21:00
 **/
package com.taotao.controller;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.common.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContent(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "ids", required = false) List<Long> idList) {
        if (id != null) {
            idList.add(id);
        }

        return contentService.delete(idList);
    }

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveContent(TbContent tbContent) {

        return contentService.save(tbContent);
    }

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