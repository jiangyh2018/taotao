package com.taotao.service;

import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    TbItem getItemById(long id);

    EasyUIDataGridResult getAllItem(Integer pageNum, Integer pageSize);

}
