/*
 * @title: XMLConfigReader.java
 * @package com.qianty.validater.conf
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-12 下午12:36:49
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.conf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @class XMLConfigReader
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class XMLConfigReader extends AbstractConfigReader {
    
    private Iterator<?> checkIterator;
    private Iterator<?> mappingIterator;
    
    public XMLConfigReader(InputStream is) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        checkIterator = document.selectNodes("//check[@id]").iterator();
        mappingIterator = document.selectNodes("//check-mapping/class/method").iterator();
    }
    
    public static void main(String[] args) throws DocumentException {
        XMLConfigReader reader = (new XMLConfigReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("param-checker-setting.xml")));
        CheckDefinition def = null;
        while((def = reader.nextCheckDefinition()) != null) {
            System.out.println(def.getId());
        }
    }
    
    /* (non-Javadoc)
     * @see com.qianty.validater.conf.AbstractConfigReader#nextMappingDefinition()
     */
    @Override
    public MappingDefinition nextMappingDefinition() {
        
        if(mappingIterator.hasNext()) {
            Element element = (Element)mappingIterator.next();
            
            return createMappingDefinition(element);
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.qianty.validater.conf.AbstractConfigReader#nextCheckDefinition()
     */
    @Override
    public CheckDefinition nextCheckDefinition() {
        
        if(checkIterator.hasNext()) {
            Element element = (Element)checkIterator.next();
                
            return createCheckDefinition(element, true);
        } else {
            
            return null;
        }
    }
    
    private MappingDefinition createMappingDefinition(Element element) {
        
        String clazz = element.getParent().attributeValue("name");
        String method = element.attributeValue("name");
        List<?> checks = element.selectNodes("check");
        List<String> checkerIds = new ArrayList<String>(checks.size());
        for(int i = 0; i < checks.size(); i ++) {
            checkerIds.add(getCheckerId((Element)checks.get(i)));
        }
        return mappingDefinition(clazz, method, checkerIds);
    }
    
    private String getCheckerId(Element check) {
        String ref = check.attributeValue("ref");
        String id = check.attributeValue("id");
        if(id != null && ref != null) {
            throw new RuntimeException("[Illegal attribute : ref and id !!!]");
        }
        if(ref != null) {
            return ref;
        }
        if(id != null) {
            return id;
        }
        throw new RuntimeException("[At least has one attribute between ref and id !!!]");
    }
    
    private CheckDefinition createCheckDefinition(Element element, boolean flag) {
        
        List<?> subCheckElements = element.selectNodes("check");
        List<CheckDefinition> subChecks = new ArrayList<CheckDefinition>();
        if(subCheckElements != null && subCheckElements.size() > 0) {
            for(Object obj : subCheckElements) {
                //子层结点不需要校验ID属性
                subChecks.add(createCheckDefinition((Element)obj, false));
            }
        }
        return checkDefinition(element.attributeValue("id"),
                element.attributeValue("field"), element.attributeValue("ognl"),
                element.attributeValue("regex"), element.attributeValue("msg"),
                subChecks, flag);
    }

}
