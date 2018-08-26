package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.common.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TbContentMapper tbContentMapper;

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

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCategoryId(Long id) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        List<TbContent> contentList = tbContentMapper.selectByExample(example);

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