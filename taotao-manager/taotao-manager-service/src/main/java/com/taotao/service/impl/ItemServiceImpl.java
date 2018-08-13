package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public EasyUIDataGridResult getAllItem(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbItem> list=itemMapper.selectByExample(new TbItemExample());
		System.out.println("list: "+list.size());
		PageInfo pageInfo=new PageInfo<>(list);
		System.out.println("list: "+list.size());
		System.out.println("pageInfo: "+pageInfo);
		return new EasyUIDataGridResult(pageInfo.getTotal(),pageInfo.getList());
	}

}
