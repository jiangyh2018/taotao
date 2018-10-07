/**
 * @description: 商品内容管理controller
 * @author:
 * @create: 2018-08-19 18:23
 **/
package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
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

    /** 删除所选的类目及子类目
     * @param id
     */
    @RequestMapping(value = "/content/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public void deleteCategory(@RequestParam(value = "id") Long id){
        contentCategoryService.deleteCategory(id);
    }

    /** 修改内容类目名称
     * @param id
     * @param name
     */
    @RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateCategory(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {
        contentCategoryService.update(id,name);
    }

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

        return result;
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