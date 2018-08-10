package com.taotao.service;

import com.jah.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long id);

	EasyUIDataGridResult getAllItem(int pageNum,int pageSize); 
}
