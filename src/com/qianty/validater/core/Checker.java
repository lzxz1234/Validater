/*
 * @title: Checker.java
 * @package com.qianty.validater.core
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午02:14:22
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.core;

import java.util.ArrayList;
import java.util.List;

import com.qianty.validater.util.InvokeUtils;

/**
 * @class Checker
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class Checker {
    
    private String field;
    private Test ognl;
    private Test regex;
    private List<Checker> subCheckers = new ArrayList<Checker>();
    
    public void check(Object obj) throws Exception {
        
        Object target = obj;
        if(field != null && field.length() > 0) {
            target = InvokeUtils.getFieldValue(obj, field);
        }
        regex.check(target);
        ognl.check(target);
        for(Checker checker : subCheckers) {
            checker.check(target);
        }
    }
    
    Checker(String field, String ognl, String regex, String msg, 
            List<Checker> subList) throws Exception {
        
        this.field = field;
        this.ognl = TestFactory.parseOgnl(ognl, msg);
        this.regex = TestFactory.parseRegex(regex, msg);
        this.subCheckers = subList;
    }
}
