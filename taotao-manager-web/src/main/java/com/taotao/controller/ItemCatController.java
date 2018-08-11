package com.taotao.controller;

import com.taotao.common.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangah
 */
@Controller
@RequestMapping("/item")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatServiceService;

    @RequestMapping(value = "/cat/list", method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUITreeNode> getContentTree(@RequestParam(value = "id", required = false, defaultValue = "0") Long parentId) {
        List<TbItemCat> itemCats = itemCatServiceService.getItemCatByParentId(parentId);
        EasyUITreeNode easyUITreeNode;
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();

        for (TbItemCat itemCat : itemCats) {
            easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(itemCat.getId());
            easyUITreeNode.setText(itemCat.getName());
            easyUITreeNode.setState(itemCat.getIsParent() == true ? "closed" : "open");

            easyUITreeNodes.add(easyUITreeNode);
        }

        System.out.println("easyUItreeNodes: " + easyUITreeNodes);

        return easyUITreeNodes;
    }
}
