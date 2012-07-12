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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.DocumentException;

import com.qianty.validater.conf.MappingDefinition;
import com.qianty.validater.conf.XMLConfigReader;
import com.qianty.validater.core.Checker;
import com.qianty.validater.core.CheckerFactory;

/**
 * @class Validater
 * @description 校验入口
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class Validater {
    
    /** Map<String,Checker> : 方法全名称 到 校验器的映射 */
    private static Map<String, Checker[]> map;
    
    /** 
     * 校验初始化
     * @param is
     * @throws DocumentException
     * @throws Exception
     */
    public static void initXML(InputStream is) throws Exception {
        
        map = new ConcurrentHashMap<String, Checker[]>();
        XMLConfigReader reader = new XMLConfigReader(is);
        CheckerFactory factory = new CheckerFactory(reader);
        MappingDefinition mdf;
        while((mdf = reader.nextMappingDefinition()) != null) {
            map.put(mdf.getCompleteMethodName(), constructArray(factory, mdf.getCheckId()));
        }
    }
    
    private static Checker[] constructArray(CheckerFactory factory, List<String> checkerIds) {
        
        Checker[] result = new Checker[checkerIds.size()];
        for(int i = 0; i < checkerIds.size(); i ++) {
            result[i] = factory.getChecker(checkerIds.get(i));
        }
        return result;
    }
    /** 
     * 校验参数合法性
     * @param methodName 格式应该：com.qianty.web.check.plugin.CheckManager.getCheckers
     * @param obj 方法参数 
     * @throws Exception 
     */
    public static void check(String methodName, Object... args) 
    throws Exception {
        
        Checker[] cs = map.get(methodName);
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
