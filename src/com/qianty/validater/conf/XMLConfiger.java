/*
 * @title: XMLConfiger.java
 * @package com.qianty.validater.config
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-8 下午06:01:29
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.OgnlException;

import org.dom4j.Document;
import org.dom4j.Element;

import com.qianty.validater.core.CheckUnit;
import com.qianty.validater.core.TestFactory;

/**
 * @class XMLConfiger
 * @description XML 配置文件解析工具
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public abstract class XMLConfiger {
    
    private static final String PATH_QCHECK = "check-config/check-list/check[@id='$']";
    private static final String PATH_MAPPING = "check-config/check-mapping/class/method";
    private static final String ATTR_ID = "id";
    private static final String ATTR_CLASS = "class";
    private static final String ATTR_FIELD = "field";
    private static final String ATTR_OGNL = "ognl";
    private static final String ATTR_REGEX = "regex";
    private static final String ATTR_MSG = "msg";
    private static final String ATTR_NAME = "name";
    private static final String ELE_CUNIT = "cunit";

    /** 
     * @param document
     * @return 解析配置文件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, CheckUnit[]> parseConfig(Document document)
    throws Exception {
        
        Map<String, CheckUnit[]> map = new HashMap<String, CheckUnit[]>();
        
        String methodName;
        String className;
        List<Element> list;
        for(Element ele : (List<Element>)document.selectNodes(PATH_MAPPING)) {
            methodName = ele.attributeValue(ATTR_NAME);
            className = ele.getParent().attributeValue(ATTR_NAME);
            list = listCheckElement(document, ele);
            map.put(className + "." + methodName, getCheckUnits(list));
        }
        
        return map;
    }
    
    /** 
     * @param list
     * @return 解析列表，生成CheckUnit数组，一个数组与一个方法调用的参数列表对应 
     * @throws Exception
     */
    private static CheckUnit[] getCheckUnits(List<Element> list) throws Exception {
        CheckUnit[] cs = new CheckUnit[list.size()];
        for(int i = 0; i < list.size(); i ++) {
            cs[i] = analyzeCheck(list.get(i));
        }
        return cs;
    }
    
    /** 
     * @param element
     * <code><br>&lt;method name="regist"&gt;
     * <br>　　&lt;check&gt;registChecker&lt;/check&gt;
     * <br>&lt;/method&gt;
     * @return 返回method节点下的Check节点列表，该处节点有两种表示形式，一种正常递归
     * 表示，一种为ref引用，均合法
     */
    private static List<Element> listCheckElement(Document doc, Element ele) {
        List<Element> list = new ArrayList<Element>();
        for(Object obj : ele.elements()) {
            if(((Element)obj).attributeValue(ATTR_ID) != null) {
                list.add((Element)obj);
            } else {
                list.add((Element) (doc.selectSingleNode(
                        PATH_QCHECK.replace("$", ((Element)obj).getText()))));
            }
        }
        return list;
    }
    
    /** 
     * 解析&lt;check&gt;结点，结点有效属性ognl msg，有效子结点&lt;cunit&gt;
     * （&lt;cunit&gt;为递归结构）
     * @param element 示例
     * <br>&lt;check id="registChecker" class="com.qianty.web.obj.dto.RegisterUser"
     *  ognl="user != null" msg="用户不能为空"&gt;
     * <br>　　&lt;cunit field="user" ognl="username != null and password != null" msg="用户无效"&gt;
     * <br>　　　　&lt;cunit field="username" regex="[a-z A-Z 0-9]{6,}" msg="无效用户名"/&gt;
     * <br>　　　　&lt;cunit field="password" regex="[a-z A-Z 0-9]{6,}" msg="无效密码"/&gt;
     * <br>　　&lt;/cunit>
     * <br>&lt;/check></code>
     * @throws Exception
     */
    private static CheckUnit analyzeCheck(Element element) 
    throws Exception {
        
        Class<?> clazz = Class.forName(element.attributeValue(ATTR_CLASS));
        CheckUnit check = analyzeAttribute(element);
        
        for(Object e : element.selectNodes(ELE_CUNIT)) {
            recursive((Element) e, clazz, check);
        }
        
        return check;
    }
    
    /** 
     * 递归解析&lt;cunit&gt;结点
     */
    private static void recursive(Element element, Class<?> clazz, 
            CheckUnit check) throws Exception {

        String field = element.attributeValue(ATTR_FIELD);
        
        CheckUnit subCheck = analyzeAttribute(element);
        
        subCheck.setField(clazz.getDeclaredField(field));
        check.addSubCheck(subCheck);
        for(Object e : element.selectNodes(ELE_CUNIT)) {
            recursive((Element)e, 
                    clazz.getDeclaredField(field).getType(), subCheck);
        }
    }
    
    /** 
     * 解析结点属性构造 CheckUnit 实体,仅为初始化实体
     */
    private static CheckUnit analyzeAttribute(Element element) 
    throws OgnlException {
        
        CheckUnit check = new CheckUnit();
        String ognl = element.attributeValue(ATTR_OGNL);
        String regex = element.attributeValue(ATTR_REGEX);
        String msg = element.attributeValue(ATTR_MSG);
        
        if(ognl != null) check.addTest(TestFactory.parseOgnl(ognl, msg));
        if(regex != null) check.addTest(TestFactory.parseRegex(regex, msg));
        return check;
    }
    
}
