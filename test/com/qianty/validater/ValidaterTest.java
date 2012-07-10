/*
 * @title: ValidaterTest.java
 * @package com.qianty.validater
 * @author lzxz1234<314946925@qq.com>
 * @date 2012-7-10 上午09:36:31
 * @version V1.0
 * Copyright (c) 2012 QianTY.com. All Right Reserved
 */
package com.qianty.validater;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * @class ValidaterTest
 * @author lzxz1234<314946925@qq.com>
 * @version v1.0
 */
public class ValidaterTest {
    
    UserInfo info = new UserInfo();
    
    @Before
    public void before() {
        User user = new User();
        user.password = "13 ds57";
        user.username = "567ytfds";
        info.user = user;
    }
    
    @Test
    public void test() throws Exception {
        Validater.initXML(getInputStream1());
        Validater.check("com.qianty.validater.ValidaterTest.test", info);
        Validater.initXML(getInputStream2());
        Validater.check("com.qianty.validater.ValidaterTest.test", info);
    }
    
    public static class UserInfo {
        User user;
        public User getUser() {
            return user;
        }
    }
    public static class User {
        String username;
        String password;
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }
    private static InputStream getInputStream1() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>                                                                                ");
        sb.append("<check-config>                                                                                                            ");
        sb.append("    <check-list>                                                                                                          ");
        sb.append("     <check id=\"checkUserInfo\" class=\"com.qianty.validater.ValidaterTest$UserInfo\" ognl=\"user != null\" msg=\"用户不能为空\">");
        sb.append("         <cunit field=\"user\" ognl=\"username != null and password != null\" msg=\"用户无效\">                           ");
        sb.append("             <cunit field=\"username\" regex=\"[a-z A-Z 0-9]{6,}\" msg=\"无效用户名\"></cunit>                            ");
        sb.append("             <cunit field=\"password\" regex=\"[a-z A-Z 0-9]{6,}\" msg=\"无效密码\"></cunit>                              ");
        sb.append("         </cunit>                                                                                                         ");
        sb.append("     </check>                                                                                                             ");
        sb.append("    </check-list>                                                                                                         ");
        sb.append("                                                                                                                          ");
        sb.append("    <check-mapping>                                                                                                       ");
        sb.append("        <class name=\"com.qianty.validater.ValidaterTest\">                                                      ");
        sb.append("            <method name=\"test\">                                                                                      ");
        sb.append("                <check>checkUserInfo</check>                                                                              ");
        sb.append("            </method>                                                                                                     ");
        sb.append("        </class>                                                                                                          ");
        sb.append("    </check-mapping>                                                                                                      ");
        sb.append("</check-config>                                                                                                           ");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
    private static InputStream getInputStream2() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>                                                                                ");
        sb.append("<check-config>                                                                                                            ");
        sb.append("    <check-list>                                                                                                          ");
        sb.append("    </check-list>                                                                                                         ");
        sb.append("                                                                                                                          ");
        sb.append("    <check-mapping>                                                                                                       ");
        sb.append("        <class name=\"com.qianty.validater.ValidaterTest\">                                                      ");
        sb.append("            <method name=\"test\">                                                                                      ");
        sb.append("     <check id=\"checkUserInfo\" class=\"com.qianty.validater.ValidaterTest$UserInfo\" ognl=\"user != null\" msg=\"用户不能为空\">");
        sb.append("         <cunit field=\"user\" ognl=\"username != null and password != null\" msg=\"用户无效\">                           ");
        sb.append("             <cunit field=\"username\" regex=\"[a-z A-Z 0-9]{6,}\" msg=\"无效用户名\"></cunit>                            ");
        sb.append("             <cunit field=\"password\" regex=\"[a-z A-Z 0-9]{6,}\" msg=\"无效密码\"></cunit>                              ");
        sb.append("         </cunit>                                                                                                         ");
        sb.append("     </check>                                                                                                             ");
        sb.append("            </method>                                                                                                     ");
        sb.append("        </class>                                                                                                          ");
        sb.append("    </check-mapping>                                                                                                      ");
        sb.append("</check-config>                                                                                                           ");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
