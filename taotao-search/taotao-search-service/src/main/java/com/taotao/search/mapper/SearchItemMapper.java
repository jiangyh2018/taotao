package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * @description: SearchItemMapper接口
 * @author:
 * @create: 2018-09-05 22:27
 **/
public interface SearchItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);

}