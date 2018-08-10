package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jah.common.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/getItemById")
	@ResponseBody
	public TbItem getItemById(@RequestParam(value = "id") Long id) {
		TbItem item = itemService.getItemById(id);
		System.out.println("item: " + item);

		return item;
	}

	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getAllItem(@RequestParam(value = "page") int pageNum,
			@RequestParam(value = "rows") int pageSize) {

		return itemService.getAllItem(pageNum, pageSize);
	}
}
