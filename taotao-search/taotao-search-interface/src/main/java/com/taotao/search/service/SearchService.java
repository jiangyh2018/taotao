package com.taotao.search.service;

import com.taotao.common.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * @description: 搜索solr
 * @author:
 * @create: 2018-09-10 20:26
 **/
public interface SearchService {

    SearchResult search(String queuryString, int pageNumber, int rows) throws SolrServerException;

}