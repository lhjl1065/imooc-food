package com.imooc.pojo.vo;

import java.util.List;

/**
 * 一级分类
 */
public class CategoryVo {
    private int id;
    private String name;
    private int type;
    private int fatherId;
    private List<SubCategoryVo> SubCatList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVo> getSubCatList() {
        return SubCatList;
    }

    public void setSubCatList(List<SubCategoryVo> SubCatList) {
        this.SubCatList = SubCatList;
    }

    public CategoryVo(int id, String name, int type, int fatherId,
        List<SubCategoryVo> SubCatList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fatherId = fatherId;
        this.SubCatList = SubCatList;
    }

    public CategoryVo() {
    }
}
