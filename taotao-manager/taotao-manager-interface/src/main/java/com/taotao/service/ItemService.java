package com.taotao.service;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
    TbItem getItemById(long id);

    TbItemDesc getItemDescById(long id);

    EasyUIDataGridResult getAllItem(Integer pageNum, Integer pageSize);

    TaotaoResult save(TbItem tbItem, String desc);
}
