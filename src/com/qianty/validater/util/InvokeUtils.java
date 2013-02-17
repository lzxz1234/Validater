/*
 * @title: InvokeUtils.java
 * @package com.qianty.validater.util
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午02:27:40
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.util;

/**
 * @class InvokeUtils
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public abstract class InvokeUtils {

    /** 
     * 从对象 obj 中查找 field 字段，并取值 ,查不到时递归查找父类，均不存在时抛出异常
     * @param obj 待取值所在对象
     * @param field 待取字段
     * @return obj 中 field 字段值
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final Object getFieldValue(Object obj, String field) throws 
    NoSuchFieldException {
        
        Class<?> clazz = obj.getClass();
        try {
            return getFieldValue(obj.getClass(), obj, field);
        } catch (NoSuchFieldException e) {
            //当前类不存在该字段则继续查找父类
            while((clazz = clazz.getSuperclass()) != Object.class) {
                try {
                    return getFieldValue(clazz, obj, field);
                } catch (NoSuchFieldException ee) {
                    //GO ON
                }
            }
            throw new NoSuchFieldException(field);
        }
    }
    
    /** 
     * 按 clazz 查找对象 obj 中的字段 field 并取值，查不到抛出 NoSuchFieldException
     * @param clazz 要求 obj 必需为 clazz 的实例
     * @param obj 待取值所在对象
     * @param field 待取字段
     * @return obj 中 field 字段值
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final Object getFieldValue(Class<?> clazz, Object obj, String field) 
    throws NoSuchFieldException {
        
        try {
            if(field == null || field.equals("")) {
                return obj;
            }
            return FieldCache.getField(clazz, field).get(obj);
        } catch (IllegalAccessException e) {
            // Not Accessable
        }
        throw new RuntimeException("This Exception Should Never Occured，If You " +
                "See This, Please Check InvokeUtils.class");
    }
    
    /** 
     * 从对象 obj 中查找 field 字段，并设值 value ,查不到时递归查找父类，均不存在时抛出异常
     * @param obj 待填值对象
     * @param field 待填字段
     * @return value 待填内容
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final void setFieldValue(Object obj, String field, Object value) 
    throws NoSuchFieldException {
        
        Class<?> clazz = obj.getClass();
        try {
            setFieldValue(clazz, obj, field, value);
        } catch (NoSuchFieldException e) {
            //当前类不存在该字段则继续查找父类
            while((clazz = clazz.getSuperclass()) != Object.class) {
                if(setFieldValueSafely(clazz, obj, field, value)) {
                    return;
                }
            }
            throw new NoSuchFieldException("NO " + field + " IN " + obj);
        }
    }
    
    /** 
     * 按类 clazz 查找字段并设值，查不到抛出 NoSuchFieldException 
     * @param clazz 要求 obj 必需为 clazz 的实例
     * @param obj 待填值对象
     * @param field 待填字段
     * @return value 待填内容
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final void setFieldValue(Class<?> clazz, Object obj, String field, 
        Object value) throws NoSuchFieldException {
        
        try {
            FieldCache.getField(clazz, field).set(obj, value);
        } catch (IllegalAccessException e) {
            // Not Accessable
        }
    }
    
    /** 
     * 按类 clazz 查找字段并设值，查不到或设值失败返回false
     * @param clazz 要求 obj 必需为 clazz 的实例
     * @param obj 待填值对象
     * @param field 待填字段
     * @return value 待填内容
     */
    public static final boolean setFieldValueSafely(Class<?> clazz, Object obj, String field, 
        Object value) {
        
        try {
            setFieldValue(clazz, obj, field, value);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }
    
}
