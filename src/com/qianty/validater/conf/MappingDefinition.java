/*
 * @title: MappingDefinition.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午04:47:16
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.conf;

import java.util.List;

/**
 * @class MappingDefinition
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class MappingDefinition {
    
    private String completeMethodName;
    private List<String> checkId;
    
    public MappingDefinition(String clazz, String method, List<String> checkId) {
        
        this.completeMethodName = clazz + "." + method;
        this.checkId = checkId;
    }
    
    /**
     * @return the completeMethodName
     */
    public String getCompleteMethodName() {
        return completeMethodName;
    }
    /**
     * @param completeMethodName the completeMethodName to set
     */
    public void setCompleteMethodName(String completeMethodName) {
        this.completeMethodName = completeMethodName;
    }
    /**
     * @return the checkId
     */
    public List<String> getCheckId() {
        return checkId;
    }
    /**
     * @param checkId the checkId to set
     */
    public void setCheckId(List<String> checkId) {
        this.checkId = checkId;
    }
    
}
