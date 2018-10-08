package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 **/
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 展示商品详情：查询的redis，如没有就查询mysql数据库
     *
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/item/{itemId}")
    public String getItemById(@PathVariable(value = "itemId") long itemId, Model model) {
        //跟据商品id查询商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        //把TbItem转换成Item对象
        Item item = new Item(tbItem);

        //根据商品id查询商品描述
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);

        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);

        return "item";
    }
    /**
     * 展示商品详情：访问freemarker生成的静态页面
     */
//    @RequestMapping(value = "/item/{itemId}")
//    public String getItemById(@PathVariable(value = "itemId") long itemId) {
//
//        return ""+itemId;
//    }

}