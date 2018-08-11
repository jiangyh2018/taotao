package com.taotao.controller;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jiangah
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据id查询TbItem
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getItemById", method = RequestMethod.GET)
    @ResponseBody
    public TbItem getItemById(@RequestParam(value = "id") Long id) {
        TbItem item = itemService.getItemById(id);
        System.out.println("item: " + item);

        return item;
    }

    /**
     * 查询所有TbItem
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getAllItem(@RequestParam(value = "page") int pageNum,
                                           @RequestParam(value = "rows") int pageSize) {

        return itemService.getAllItem(pageNum, pageSize);
    }
}
