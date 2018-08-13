package com.taotao.common;

import java.io.Serializable;

/**
 * @program: taotao
 * @description:树形节点的POJO
 * @author:
 * @create: 2018-08-11 21:12
 **/
public class EasyUITreeNode implements Serializable{
    private Long id;

    private String text;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
