/*
 * @title: FieldCache.java
 * @package com.qianty.web.utils
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-6-25 下午03:07:43
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 类字段缓存
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class FieldCache {
    
    private static ConcurrentHashMap<Class<?>, Map<String, Field>> fieldsMap = 
            new ConcurrentHashMap<Class<?>, Map<String, Field>>();
    
    /** 
     * @param clazz 类名
     * @param field 字段名
     * @return 返回类的指定 Field 对象，已经设置 Accessible
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Field getField(Class<?> clazz, String field) {
        
        return ensureFieldsStored(clazz).get(field);
    }
    
    /** 
     * @param clazz
     * @return 目标类全部字段组成的信息
     */
    public static Collection<Field> getFields(Class<?> clazz) {
        
        return ensureFieldsStored(clazz).values();
    }
    
    /** 
     * 确认缓存中已存在目标类字段信息并返回该字段信息
     * @param clazz
     * @return 目标类字段信息
     */
    private static Map<String, Field> ensureFieldsStored(Class<?> clazz) {
        
        Map<String, Field> result = fieldsMap.get(clazz);
        if(result == null) {
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            }); //按字段名自然顺序排序，确保每次返回顺序相同
            result = new LinkedHashMap<String, Field>();
            for(Field field : fields) {
                field.setAccessible(true);
                result.put(field.getName(), field);
            }
            fieldsMap.putIfAbsent(clazz, result);
        }
        return result;
    }
    
    /** 
     * @param clazz
     * @return 返回非 static 非 transient 字段数组
     */
    public static Collection<Field> getUntransUnstaticFields(Class<?> clazz) {
        
        return getFields(clazz, new FieldFilter() {
    
            @Override
            public boolean accept(Field field) {
    
                if(Modifier.isStatic(field.getModifiers())) {
                    return false;
                }
                if(Modifier.isTransient(field.getModifiers())) {
                    return false;
                }
                return true;
            }
            
        });
    }
    
    /** 
     * 根据过滤策略过滤目标类中全部字段，并将符合条件的字段组成数组返回
     * @param clazz
     * @param filter
     * @return 目标类中符合条件的字段数组
     */
    public static Collection<Field> getFields(Class<?> clazz, FieldFilter filter) {
        
        Map<String, Field> tmp = ensureFieldsStored(clazz);
        List<Field> result = new ArrayList<Field>(tmp.size());
        
        for(Field field : tmp.values()) {
            if(filter.accept(field)) {
                result.add(field);
            }
        }
        return result;
    }
    
    /** 
     * 清除目标类的缓存信息
     * @param clazz
     */
    public static void remove(Class<?> clazz) {
        fieldsMap.remove(clazz);
    }
    
    /**
     * 调用 <code>FieldCache.getFields()</code> 时的过滤策略
     * @see com.qianty.web.utils.FieldCache#getFields(Class, FieldFilter)
     * @author lzxz1234<314946925@qq.com>
     * @version v1.0
     */
    public static interface FieldFilter {
        
        /** 
         * @param field
         * @return true - 有效<br>false - 无效
         */
        public boolean accept(Field field);
        
    }
    
}
