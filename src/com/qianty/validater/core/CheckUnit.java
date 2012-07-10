/*
 * @title: CheckUnit.java
 * @package com.qianty.web.check.plugin
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-6-12 下午02:11:27
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @class CheckUnit
 * @description 检测单元，对应一个类对象中单个字段的检测
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class CheckUnit {

    /** Field : 需检测字段 */
    private Field field;
    /** Test : 校验规则 */
    private List<Test> testList = new ArrayList<Test>(2);
    /** List<Map<Field,CheckUnit>> : field 内容的详细校验 */
    private List<CheckUnit> checkList = new ArrayList<CheckUnit>();
    
    /** 
     * 增加校验规则
     * @param test
     */
    public void addTest(Test test) {
        testList.add(test);
    }
    
    /** 
     * @param field 校验字段，不设置时指当前对象
     */
    public void setField(Field field) {
        this.field = field;
        if(!field.isAccessible()) field.setAccessible(true);
    }
    
    /** 
     * @param check 子校验 
     */
    public void addSubCheck(CheckUnit check) {
        checkList.add(check);
    }
    
    /** 
     * 检验对象指定字段及字段的字段的合法性，如果传参为RegisterUser的实例，而this.field为
     * RegisterUser.user字段，那么当前对象会检查user字段内容的合法性（可能也会根据配置文件
     * 检查user.username的合法性）
     * @param obj
     */
    public void check(Object obj) throws IllegalArgumentException{
        try {
            if(field != null) obj = field.get(obj);
            if(testList.size() > 0) {
                for(Test test : testList) {
                    test.check(obj);
                }
            }
            if(checkList.size() > 0) {
                for(CheckUnit checkUnit : checkList) {
                    checkUnit.check(obj);
                }
            }
        } catch (IllegalAccessException e) {
            //Not Avaliable
        }
    }
    
}
