package com.imooc.common.enums;

public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");
    private final int type;
    private final String value;

    YesOrNo(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }
}
