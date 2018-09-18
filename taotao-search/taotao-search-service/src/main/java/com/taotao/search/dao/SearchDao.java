package com.taotao.search.dao;

import com.taotao.common.SearchItem;
import com.taotao.common.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 搜索商品dao
 * @author:
 * @create: 2018-09-08 21:39
 **/
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 查询索引库
     */
    public SearchResult search(SolrQuery query) throws SolrServerException {
        //根据query对象查询索引库
        QueryResponse response = solrServer.query(query);
        //取商品列表
        SolrDocumentList solrDocumentList = response.getResults();
        //商品列表
        List<SearchItem> searchItemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            //图片URL处理一下，取第一个
            String images = (String) solrDocument.get("item_image");
            if(StringUtils.isNotBlank(images)){
                images = images.split(",")[0];
            }
            searchItem.setImage(images);

            searchItem.setPrice(Long.parseLong(String.valueOf(solrDocument.get("item_price"))));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));

            //取高亮显示
            Map<String, Map<String, List<String>>> hightlighMap = response.getHighlighting();
            List<String> list = hightlighMap.get(solrDocument.get("id")).get("item_title");
            //有高亮显示的内容时。
            if (list != null && list.size() > 0) {
                searchItem.setTitle(list.get(0));
            } else {
                searchItem.setTitle((String) solrDocument.get("item_title"));
            }

            //添加到商品列表
            searchItemList.add(searchItem);
        }
        SearchResult result = new SearchResult();
        //商品列表
        result.setSearchItemList(searchItemList);
        //总记录数
        result.setRecordCount(solrDocumentList.getNumFound());

        return result;
    }

}