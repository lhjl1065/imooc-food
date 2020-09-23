package com.imooc.common.enums;

public enum  CommentLevelenum {

    good(1,"好评"),
    normal(2,"中评"),
    bad(3,"一般");
    private final Integer type;
    private final String value;

    CommentLevelenum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
