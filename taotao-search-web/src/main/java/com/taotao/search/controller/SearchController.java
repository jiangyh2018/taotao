package com.taotao.search.controller;

import com.taotao.common.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 跳转搜索首页
 * @author:
 * @create: 2018-09-06 22:55
 **/
@Controller
public class SearchController {
    @Value("${SEARCH_ROWS}")
    private Integer rows;

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search.html", method = RequestMethod.GET)
    public String toIndex(@RequestParam(value = "q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception{
        queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
        SearchResult result = searchService.search(queryString, page, rows);

        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getSearchItemList());
        model.addAttribute("page", page);

        return "search";
    }
}