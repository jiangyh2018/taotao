package com.taotao.search.service.impl;

import com.taotao.common.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.common.SearchItem;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @description: 将mysql数据导入solr索引库
 * @author:
 * @create: 2018-09-06 21:44
 **/
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private HttpSolrServer httpSolrServer;

    @Autowired
    private SearchItemMapper searchItemMapper;

    /**
     * 将数据库商品信息全部导入solr索引库
     */
    @Override
    public TaotaoResult importAllItem() throws SolrServerException, IOException {
        //先查出SearchItem
        List<SearchItem> searchItemList = searchItemMapper.getItemList();

        //在插入到solr索引库中
//        SolrServer solrServer=new HttpSolrServer(solrURL);

        // 为每个商品创建一个SolrInputDocument对象。
        for (SearchItem searchItem : searchItemList) {
            SolrInputDocument document = new SolrInputDocument();
            // 为文档添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            // 5、向索引库中添加文档。
            httpSolrServer.add(document);
        }
        //提交修改
        httpSolrServer.commit();

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addItem(SearchItem searchItem) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        // 为文档添加域
        document.addField("id", searchItem.getId());
        document.addField("item_title", searchItem.getTitle());
        document.addField("item_sell_point", searchItem.getSell_point());
        document.addField("item_price", searchItem.getPrice());
        document.addField("item_image", searchItem.getImage());
        document.addField("item_category_name", searchItem.getCategory_name());
        document.addField("item_desc", searchItem.getItem_desc());

        httpSolrServer.add(document);

        httpSolrServer.commit();

        return TaotaoResult.ok();
    }
}