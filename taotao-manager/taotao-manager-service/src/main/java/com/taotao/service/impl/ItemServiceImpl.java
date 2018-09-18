package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    //    @Autowired
//    private SearchItemService searchItemServiceService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "topicDestination")
    private ActiveMQTopic topic;

    @Override
    public TbItem getItemById(long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询全部TbItem
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public EasyUIDataGridResult getAllItem(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
//        System.out.println("list: " + list.size());
        PageInfo pageInfo = new PageInfo<>(list);
//        System.out.println("list: " + list.size());
//        System.out.println("pageInfo: " + pageInfo);
        return new EasyUIDataGridResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增商品信息
     *
     * @param tbItem
     */
    @Override
    public TaotaoResult save(TbItem tbItem, String desc) {
        tbItem.setCreated(new Date());
        tbItem.setUpdated(tbItem.getCreated());
        itemMapper.insertSelective(tbItem);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(tbItem.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(tbItem.getCreated());
        itemDesc.setUpdated(tbItem.getUpdated());
        itemDescMapper.insertSelective(itemDesc);

        //新增商品，同时添加到solr索引库中
//        SearchItem searchItem = new SearchItem();
//        searchItem.setId(String.valueOf(tbItem.getId()));
//        searchItem.setTitle(tbItem.getTitle());
//        searchItem.setSell_point(tbItem.getSellPoint());
//        searchItem.setPrice(tbItem.getPrice());
//        searchItem.setImage(tbItem.getImage());
//        //由cid获取categryName
//        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
//        searchItem.setCategory_name(tbItemCat.getName());
//        searchItem.setItem_desc(desc);

//            searchItemServiceService.addItem(searchItem);
        //将商品ID发送到消息队列中
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(String.valueOf(tbItem.getId()));
                return textMessage;
            }
        });

        return TaotaoResult.ok();
    }
}
