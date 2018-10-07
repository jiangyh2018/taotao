package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容service的实现类
 *
 * @description:
 * @author:
 * @create: 2018-08-19 20:52
 **/
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult delete(List<Long> idList) {
        for (Long id : idList) {
            tbContentMapper.deleteByPrimaryKey(id);
        }

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult save(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());

        tbContentMapper.insertSelective(tbContent);

        //同步缓存.即:删除缓存中原来的数据.
        jedisClient.hdel(CONTENT_KEY,tbContent.getCategoryId()+"");

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCategoryId(Long id) {
        //先redis查缓存，缓存中没有才去查数据库
        List<TbContent> contentList= null;
        try {
            String content=jedisClient.hget(CONTENT_KEY,id+"");
            contentList = JsonUtils.jsonToList(content, TbContent.class);
            if(StringUtils.isNotBlank(content)){
                return contentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        contentList = tbContentMapper.selectByExample(example);

        //将数据库查询数据放入redis缓存
        try {
            jedisClient.hset(CONTENT_KEY,id+"", JsonUtils.objectToJson(contentList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }

    @Override
    public EasyUIDataGridResult getList(Long categoryId, Integer page, Integer rows) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        PageHelper.startPage(page, rows);
        List<TbContent> contentList = tbContentMapper.selectByExample(example);
//        System.out.println(contentList.size());

        PageInfo pageInfo = new PageInfo(contentList);

        return new EasyUIDataGridResult(pageInfo.getTotal(), pageInfo.getList());
    }

}