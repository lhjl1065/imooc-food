package com.imooc.pojo.vo;

/**
 * 二级分类
 */
public class SubCategoryVo {
    private int subId;
    private String subName;
    private int subType;
    private int subFatherId;

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(int subFatherId) {
        this.subFatherId = subFatherId;
    }

    public SubCategoryVo(int subId, String subName, int subType, int subFatherId) {
        this.subId = subId;
        this.subName = subName;
        this.subType = subType;
        this.subFatherId = subFatherId;
    }

    public SubCategoryVo() {
    }
}
