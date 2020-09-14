package com.imooc.common.enums;

public enum SexEnum {
    man(0,"男"),
    woman(1,"女"),
    secret(2,"保密");
    private int type_;
    private String value;

    public int getType_() {
        return type_;
    }

    public String getValue() {
        return value;
    }

    SexEnum(int type_, String value) {
        this.type_ = type_;
        this.value = value;

    }
}
