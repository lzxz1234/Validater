/*
 * @title: Validater.java
 * @package com.qianty.validater
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-8 ����05:49:04
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater;

import java.io.InputStream;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.qianty.validater.conf.XMLConfiger;
import com.qianty.validater.core.CheckUnit;

/**
 * @class Validater
 * @description 校验入口
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class Validater {
    
    /** Map<String,Checker> : 方法全名称 到 校验器的映射 */
    private static Map<String, CheckUnit[]> map;
    
    /** 
     * 校验初始化
     * @param is
     * @throws DocumentException
     * @throws Exception
     */
    public static void initXML(InputStream is) throws Exception {
        SAXReader reader = new SAXReader();
        map = XMLConfiger.parseConfig(reader.read(is));
    }
    
    /** 
     * 校验参数合法性
     * @param methodName 格式应该：com.qianty.web.check.plugin.CheckManager.getCheckers
     * @param obj 方法参数 
     * @throws IllegalArgumentException 参数不合法时抛出
     */
    public static void check(String methodName, Object... args) 
    throws IllegalArgumentException {
        
        CheckUnit[] cs = map.get(methodName);
        if(cs == null || args == null) return; // 不需要校验
        if(args.length != cs.length) 
            throw new IllegalArgumentException("[args.length=" + args.length
                    + "][checkUnits.length=" + cs.length + "]");
        for(int i = 0; i < cs.length; i ++) {
            if(args[i] == null) continue;
            cs[i].check(args[i]);
        }
    }
    
}
