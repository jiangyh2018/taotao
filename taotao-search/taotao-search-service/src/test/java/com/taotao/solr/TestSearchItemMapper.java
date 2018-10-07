package com.taotao.solr;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description: 测试SearchItemMapper接口的方法
 * @author:
 * @create: 2018-09-05 22:33
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestSearchItemMapper {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Test
    public void testGetItemList() {
        System.out.println(solrServer);
        List<SearchItem> searchItemList = searchItemMapper.getItemList();

        for (SearchItem s : searchItemList) {
            System.out.println(s);
        }
    }

}