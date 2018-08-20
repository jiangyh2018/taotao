/**
 * @description:
 * @author:
 * @create: 2018-08-19 18:30
 **/
package com.taotao.content.service.impl;

import com.taotao.common.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /** 根据id获取商品分类列表
     * @param id
     * @return
     */
    @Override
    public List<TbContentCategory> getList(Long id) {
        TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);

        return tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }

    @Override
    public TaotaoResult add(Long parentId, String name) {
        TbContentCategory record=new TbContentCategory();
        record.setName(name);
        record.setIsParent(false);
        record.setParentId(parentId);
        //状态。可选值:1(正常),2(删除)
        record.setStatus(1);
        record.setCreated(new Date());
        record.setSortOrder(0);
        record.setUpdated(new Date());
        tbContentCategoryMapper.insertSelective(record);

        //改变父节点的is_parent属性
        TbContentCategory parRecord=tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parRecord.getIsParent()){
            parRecord.setIsParent(true);
            parRecord.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(parRecord);
        }

        return TaotaoResult.ok(record);
    }
}