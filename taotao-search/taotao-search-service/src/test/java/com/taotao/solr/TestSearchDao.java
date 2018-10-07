package com.taotao.solr;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 测试SearchItemDao
 * @author:
 * @create: 2018-09-08 22:03
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestSearchDao {
    @Autowired
    private SearchDao searchDao;

    @Test
    public void testSearch() throws SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("基表");
        //指定默认搜索域
        query.set("df", "item_keywords");
        //开启高亮显示
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        SearchResult result = searchDao.search(query);

        System.out.println(result);
    }

}