/*
 * @title: Test.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-6-12 下午02:13:55
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.core;


/**
 * @class Test
 * @description 校验抽象
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public interface Test {
    
    /** 
     * 合法性测�?
     */
    public void check(Object obj) throws IllegalArgumentException;
    
}
