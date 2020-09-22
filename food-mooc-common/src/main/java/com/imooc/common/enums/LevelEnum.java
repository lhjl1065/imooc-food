package com.imooc.common.enums;

public enum LevelEnum {
    Root(1,"一级分类"),
    secondLevel(2,"二级分类");
    private final int type;
    private final String value;

    LevelEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }
}
