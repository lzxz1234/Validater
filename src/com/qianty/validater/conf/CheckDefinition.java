/*
 * @title: CheckDefinition.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 上午10:50:51
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.conf;

import java.util.Collections;
import java.util.List;

/**
 * @class CheckDefinition
 * @description 配置信息
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class CheckDefinition {
    
    private final String id;
    private final String field;
    private final String ognl;
    private final String regex;
    private final String msg;
    private final List<CheckDefinition> subCheck;
    
    /**
     * create a new instance
     * @param id
     * @param field
     * @param ognl
     * @param regex
     * @param msg
     */
    @SuppressWarnings("unchecked")
    public CheckDefinition(String id, String field, String ognl, String regex,
            String msg) {
        super();
        this.id = id;
        this.field = field;
        this.ognl = ognl;
        this.regex = regex;
        this.msg = msg;
        this.subCheck = Collections.EMPTY_LIST;
    }
    
    /**
     * create a new instance
     * @param id
     * @param field
     * @param ognl
     * @param regex
     * @param msg
     * @param subCheck
     */
    public CheckDefinition(String id, String field, String ognl, String regex,
            String msg, List<CheckDefinition> subCheck) {
        super();
        this.id = id;
        this.field = field;
        this.ognl = ognl;
        this.regex = regex;
        this.msg = msg;
        this.subCheck = Collections.unmodifiableList(subCheck);
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return the subCheck
     */
    public List<CheckDefinition> getSubCheck() {
        return subCheck;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @return the ognl
     */
    public String getOgnl() {
        return ognl;
    }

    /**
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

}
