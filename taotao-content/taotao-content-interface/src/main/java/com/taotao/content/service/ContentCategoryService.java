package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * @description: 商品分类service
 * @author:
 * @create: 2018-08-19 18:29
 **/
public interface ContentCategoryService {

    List<TbContentCategory> getList(Long id);

    TaotaoResult add(Long parentId, String name);

    void update(Long id, String name);

    void deleteCategory(Long id);
}