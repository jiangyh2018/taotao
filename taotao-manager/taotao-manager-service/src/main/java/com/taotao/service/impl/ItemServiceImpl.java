package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;

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
        System.out.println("list: " + list.size());
        PageInfo pageInfo = new PageInfo<>(list);
        System.out.println("list: " + list.size());
        System.out.println("pageInfo: " + pageInfo);
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

        TbItemDesc itemDesc=new TbItemDesc();
        itemDesc.setItemId(tbItem.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(tbItem.getCreated());
        itemDesc.setUpdated(tbItem.getUpdated());
        itemDescMapper.insertSelective(itemDesc);

        return TaotaoResult.ok();
    }
}
