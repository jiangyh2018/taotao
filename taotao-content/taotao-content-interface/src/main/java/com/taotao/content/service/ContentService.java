package com.taotao.content.service;


import com.taotao.common.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * 内容service
 */
public interface ContentService {

    /** 分页查询内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getList(Long categoryId, Integer page, Integer rows);

    /** 通过内容类目id获取内容列表
     * @param id
     * @return List<TbContent>
     */
    List<TbContent> getContentByCategoryId(Long id);

}
