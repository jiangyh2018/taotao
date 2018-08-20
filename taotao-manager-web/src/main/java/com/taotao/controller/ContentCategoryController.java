/**
 * @description: 商品内容管理controller
 * @author:
 * @create: 2018-08-19 18:23
 **/
package com.taotao.controller;

import com.taotao.common.EasyUITreeNode;
import com.taotao.common.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 添加内容类目
     *
     * @param
     * @return 生成的主键id
     */
    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addCategory(Long parentId, String name) {

        TaotaoResult result = contentCategoryService.add(parentId, name);
        System.out.println("插入后返回的值：" + result);

        return  result;
    }

    /**
     * 根据id获取商品分类列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/list", method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> listCategory(@RequestParam(defaultValue = "0", required = false) Long id) {

        List<TbContentCategory> list = contentCategoryService.getList(id);

        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        EasyUITreeNode easyUITreeNode = null;
        for (TbContentCategory node : list) {
            easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(node.getId());
            easyUITreeNode.setText(node.getName());
            easyUITreeNode.setState(node.getIsParent() == true ? "closed" : "open");

            easyUITreeNodes.add(easyUITreeNode);
        }

        return easyUITreeNodes;
    }
}