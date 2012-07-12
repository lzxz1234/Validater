/*
 * @title: InvokeUtils.java
 * @package com.qianty.validater.util
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午02:27:40
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.util;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

/**
 * @class InvokeUtils
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public abstract class InvokeUtils {
    
    public static final Object getFieldValue(Object obj, String field) throws 
    IllegalAccessException, NoSuchFieldException {
        
        if(field == null || field.equals("")) {
            return obj;
        }
        return FieldCache.getField(obj, field).get(obj);
    }
    
    private static class FieldCache {
        
        private static WeakHashMap<String, Field> map = new WeakHashMap<String, Field>();
        
        public static Field getField(Object obj, String field) throws SecurityException, 
        NoSuchFieldException {
            String key = obj.getClass().getName() + field;
            Field result = map.get(key);
            if(result == null) {
                result = obj.getClass().getDeclaredField(field);
                result.setAccessible(true);
                map.put(key, result);
            }
            return result;
        }
    }
}
