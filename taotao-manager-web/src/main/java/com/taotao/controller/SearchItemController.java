package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 商品信息导入solr索引库controller
 * @author:
 * @create: 2018-09-06 22:22
 **/
@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/searchItem/importall")
    @ResponseBody
    public TaotaoResult importAllItems() {
        try {
            TaotaoResult result = searchItemService.importAllItem();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "导入数据失败");
        }
    }
}