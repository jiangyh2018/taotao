package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Value("${ITEM_INFO_PRE}")
    private String ITEM_INFO;
    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "topicDestination")
    private ActiveMQTopic topic;
    @Autowired
    private JedisClient jedisClient;
    @Resource(name = "topicDestination-item-web")
    private ActiveMQTopic topicDestinationItemWeb;

    /**
     * 根据商品id查询商品信息
     *
     * @param id
     * @return
     */
    @Override
    public TbItem getItemById(long id) {
        //先查询redis缓存
        try {
            String itemStr = jedisClient.get("ITEM_INFO:" + id + ":BASE");
            if (StringUtils.isNotBlank(itemStr)) {
                return JsonUtils.jsonToPojo(itemStr, TbItem.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有则查询数据库
        TbItem tbItem = itemMapper.selectByPrimaryKey(id);

        try {
            //将数据库查询出来的数据放入缓存
            jedisClient.set("ITEM_INFO:" + id + ":BASE", JsonUtils.objectToJson(tbItem));
            //设置缓存的有效期
            jedisClient.expire("ITEM_INFO:" + id + ":BASE", ITEM_INFO_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItem;
    }

    /**
     * 根据商品id查询商品描述
     *
     * @param id
     * @return
     */
    @Override
    public TbItemDesc getItemDescById(long id) {
        //先查询缓存
        try {
            String itemDescStr = jedisClient.get("ITEM_INFO:" + id + ":DESC");
            if (StringUtils.isNotBlank(itemDescStr)) {
                return JsonUtils.jsonToPojo(itemDescStr, TbItemDesc.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //缓存中没有在查询数据库
        TbItemDescExample example = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemDesc> tbItemDescList = itemDescMapper.selectByExampleWithBLOBs(example);
        TbItemDesc tbItemDesc = null;
        if (tbItemDescList != null && tbItemDescList.size() > 0) {
            tbItemDesc = tbItemDescList.get(0);
        }

        try {
            //将数据库查询的数据放入缓存中
            jedisClient.set("ITEM_INFO:" + id + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            //设置缓存的有效期限
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItemDesc;
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

        //将商品ID发送到topic消息队列中
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
