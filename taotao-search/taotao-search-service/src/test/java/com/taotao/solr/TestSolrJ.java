package com.taotao.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @description: 测试SolrJ, 向索引库中增删改文档
 * @author:
 * @create: 2018-09-05 21:38
 **/
public class TestSolrJ {

    /**
     * 查询的结果带高亮显示
     */
    @Test
    public void queryDocumentWithHighLighting() throws SolrServerException {
        // 第一步：创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
        // 第二步：创建一个SolrQuery对象。
        SolrQuery query = new SolrQuery();
        // 第三步：向SolrQuery中添加查询条件、过滤条件。。。
        query.setQuery("表机");
        //指定默认搜索域
        query.set("df", "item_keywords");
        //开启高亮显示
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        // 第四步：执行查询。得到一个Response对象。
        QueryResponse response = solrServer.query(query);
        // 第五步：取查询结果。
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
        // 第六步：遍历结果并打印。
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = null;
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_price"));
        }
    }

    @Test
    public void queryDocument() throws SolrServerException {
        // 第一步：创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");

        // 第二步：创建一个SolrQuery对象。
        SolrQuery solrQuery = new SolrQuery();

        // 第三步：向SolrQuery中添加查询条件、过滤条件。。。
//        solrQuery.setQuery("*:*");
        solrQuery.setQuery("表机");
        solrQuery.set("df", "item_keywords");   //指定默认搜索域

        // 第四步：执行查询。得到一个Response对象。
        QueryResponse queryResponse = solrServer.query(solrQuery);

        // 第五步：取查询结果。
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
        // 第六步：遍历结果并打印。
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_price"));
        }

    }

    @Test
    public void addDocument() throws Exception {
        // 第一步：把solrJ的jar包添加到工程中。
        // 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");

        // 第三步：创建一个文档对象SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();

        // 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
        /*document.addField("id", "001");
        document.addField("item_title","手表");
        document.addField("item_image","http://1111111");
        document.addField("item_desc","无");*/

        document.addField("id", "001");
//        document.addField("xxxx_name", "手包");     //不能添加业务域中没有的字段
//        document.addField("item_title", "手包");
//        document.addField("item_image", "http://333333");
//        document.addField("item_desc", "无");

        // 第五步：把文档添加到索引库中。
        solrServer.add(document);

        // 第六步：提交。
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");

//        solrServer.deleteById("null");
        solrServer.deleteById("006");

//        List<String> list = new ArrayList<>();
//        list.add("002");
//        list.add("003");
//        solrServer.deleteById(list);

        solrServer.commit();
    }
}