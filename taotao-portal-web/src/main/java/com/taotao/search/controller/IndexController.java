package com.taotao.search.controller;

import com.taotao.common.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.search.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 首页跳转
 * @author:
 * @create: 2018-08-19 10:41
 */
@Controller
public class IndexController {
    @Value("${AD1_CID}")
    private Long AD1_CID;
    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;
    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;
    @Value("${AD1_HEIGHT_B}")
    private String AD1_HEIGHT_B;
    @Value("${AD1_WIDTH_B}")
    private String AD1_WIDTH_B;

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "index")
    public String toIndex(Model model) {
        List<TbContent> contentList = contentService.getContentByCategoryId(AD1_CID);
        List<Ad1Node> nodeList=new ArrayList<>();
        Ad1Node node=null;
        for (TbContent tbContent: contentList) {
            node=new Ad1Node();
            node.setSrc(tbContent.getPic());
            node.setWidth(AD1_WIDTH);
            node.setHeight(AD1_HEIGHT);
            node.setSrcB(tbContent.getPic2());
            node.setWidthB(AD1_WIDTH_B);
            node.setHeightB(AD1_HEIGHT_B);
            node.setAlt(tbContent.getTitle());

            nodeList.add(node);
        }

        model.addAttribute("ad1", JsonUtils.objectToJson(nodeList));

        return "index";
    }

}