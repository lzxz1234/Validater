/*
 * @title: CheckerFactory.java
 * @package com.qianty.validater.core
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午02:10:41
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qianty.validater.conf.AbstractConfigReader;
import com.qianty.validater.conf.CheckDefinition;

/**
 * @class CheckerFactory
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class CheckerFactory {
    
    private final Map<String, Checker> chcks = new HashMap<String, Checker>();
    
    public Checker getChecker(String id) {
        
        return chcks.get(id);
    }
    
    public CheckerFactory() {
        
    }
    
    public CheckerFactory(AbstractConfigReader reader) throws Exception {
        
        init(reader);
    }
    
    public void init(AbstractConfigReader reader) throws Exception {
        CheckDefinition def = null;
        while((def = reader.nextCheckDefinition()) != null) {
            instance(def);
        }
    }
    
    private Checker instance(CheckDefinition def) throws Exception {
        
        List<Checker> subList = new ArrayList<Checker>();
        for(CheckDefinition sub : def.getSubCheck()) {
            subList.add(instance(sub));
        }
        
        Checker chck = new Checker(def.getField(), def.getOgnl(), def.getRegex(),
                def.getMsg(), subList);
        chcks.put(def.getId(), chck);
        
        return chck;
    }
    
    public boolean containsChecker(String id) {
        
        return chcks.containsKey(id);
    }
    
}
