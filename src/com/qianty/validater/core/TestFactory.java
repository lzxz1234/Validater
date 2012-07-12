/*
 * @title: TestFactory.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-6-12 下午03:00:08
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.core;

import java.util.regex.Pattern;

import ognl.Ognl;
import ognl.OgnlException;


/**
 * @class TestFactory
 * @description Test实例工厂
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class TestFactory {
    
    /** 
     * 解析Ognl表达式，并返回Test
     * @param express 例：username != null && username != ''
     * @param msg
     * @throws OgnlException 
     */
    public static Test parseOgnl(String express, String msg) throws OgnlException {
        if(express == null) {
            return new DoNotionTest();
        }
        return new OgnlTest(express, msg);
    }
    
    /** 
     * 解析正则表达式，并返回Test
     * @param regex
     * @param msg
     */
    public static Test parseRegex(String regex, String msg) {
        if(regex == null) {
            return new DoNotionTest();
        }
        return new RegexTest(regex, msg);
    }
    
    private static class DoNotionTest implements Test {
        @Override
        public void check(Object obj) throws IllegalArgumentException {
            
        }
    }
    /**
     * @class RegexTest
     * @author lzxz1234<314946925@qq.com>
     * @version v1.0
     */
    private static class RegexTest implements Test {
        private Pattern pattern;
        private String msg;
        public RegexTest(String regex, String msg) {
            this.pattern = Pattern.compile(regex);
            this.msg = msg;
        }
        @Override
        public void check(Object obj) throws IllegalArgumentException {
            String str = obj instanceof String ? (String)obj : obj.toString();
            if(!pattern.matcher(str).matches()) {
                throw new IllegalArgumentException(msg);
            }
        }
    }
    
    /**
     * @class OgnlTest
     * @author lzxz1234<314946925@qq.com>
     * @version v1.0
     */
    private static class OgnlTest implements Test {
        private Object express;
        private String msg;
        public OgnlTest(String express, String msg) throws OgnlException {
            this.express = Ognl.parseExpression(express);
            this.msg = msg;
        }
        @Override
        public void check(Object obj) throws IllegalArgumentException {
            try {
                if(Boolean.FALSE.equals(Ognl.getValue(express, obj))) {
                    throw new IllegalArgumentException(msg);
                }
            } catch (OgnlException e) {
                throw new IllegalArgumentException(msg, e);
            }
        }
    }
    
}
