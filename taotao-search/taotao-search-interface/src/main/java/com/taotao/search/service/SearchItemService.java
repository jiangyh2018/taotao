package com.taotao.search.service;

import com.taotao.common.SearchItem;
import com.taotao.common.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * @description:
 * @author:
 * @create: 2018-09-06 21:47
 **/
public interface SearchItemService {

    TaotaoResult importAllItem() throws Exception;

    TaotaoResult addItem(SearchItem searchItem) throws IOException, SolrServerException;
}
