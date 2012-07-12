/*
 * @title: AbstractConfigReader.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 上午11:13:05
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.conf;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @class AbstractConfigReader
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public abstract class AbstractConfigReader {
    
    private Set<String> checkDefinitionIds = new HashSet<String>();
    
    /** 
     * 读取一个未解析的CheckDefinition，不存在则返回 null,若存在且同时包含子CheckDefinition会
     * 先触发子级结点解析
     * @return 下一个CheckDefinition或null
     */
    public abstract CheckDefinition nextCheckDefinition();
    
    public abstract MappingDefinition nextMappingDefinition();
    
    MappingDefinition mappingDefinition(String clazz, String method, List<String> checkId) {
        
        return new MappingDefinition(clazz, method, checkId);
    }
    
    /** 
     * 生成一个CheckDefinition对象，根据checkFlag决定是否对id进行唯一性校验
     * @param id
     * @param field
     * @param ognl
     * @param regex
     * @param msg
     * @param subCheck
     * @param checkFlag
     * @return
     */
    CheckDefinition checkDefinition(String id, String field, String ognl, String regex,
            String msg, List<CheckDefinition> subCheck, boolean checkFlag) {
        
        if(checkFlag) {
            synchronized(this) {
                if(!checkDefinitionIds.add(id)) {
                    throw new RuntimeException("[There shouldn't have more than one " +
                    		"check element with the same id attribute !!!]");
                }
            }
        }
        return new CheckDefinition(id, field, ognl, regex, msg, subCheck);
    }
    
}
