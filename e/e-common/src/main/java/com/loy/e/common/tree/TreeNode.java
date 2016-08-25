package com.loy.e.common.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class TreeNode<T> implements Tree<T>, Serializable {

    private static final long serialVersionUID = -1653478000503676779L;
    private String id;
    private String text;
    private String parentId;
    private T data;
    private String type;
    boolean selected = false;

    List<TreeNode<T>> children;

    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getParentId() {
        return this.parentId;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}