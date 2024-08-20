package com.twigcodes.cms.models.enumeration;

public enum TargetPage {
    Home("首页"),
    Category("分类页"),
    About("关于页"),;

    private final String value;

    TargetPage(String value) {
        this.value = value;
    }
}

