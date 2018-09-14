package com.taotao.solr;

import com.taotao.common.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 测试SearchService
 * @author:
 * @create: 2018-09-10 22:27
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestSearchService {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() {
        try {
            SearchResult result = searchService.search("积机", 1, 10);
            System.out.println(result);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

}