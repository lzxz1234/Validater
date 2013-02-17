Validater
=========

JAVA 版本的参数校验工具

参数设置文件示例
---------
    <?xml version="1.0" encoding="UTF-8"?>
    <check-config>
        <check-list>
        </check-list>
        <check-mapping>
            <class name="com.qianty.validater.ValidaterTest">
                <method name="test">
                    <check id="checkUserInfo" class="com.qianty.validater.ValidaterTest$UserInfo" ognl="user != null" msg="用户不能为空">
                        <check field="user" ognl="username != null and password != null" msg="用户无效">
                            <check field="username" regex="[a-z A-Z 0-9]{6,}" msg="无效用户名"/>
                            <check field="password" regex="[a-z A-Z 0-9]{6,}" msg="无效密码"/>
                        </check>
                    </check>
                </method>
            </class>
        </check-mapping>
    </check-config>
    
也可

    <?xml version="1.0" encoding="UTF-8"?>
    <check-config>
        <check-list>
            <check id="checkUserInfo" ognl="user != null" msg="用户不能为空">
                <check field="user" ognl="username != null and password != null" msg="用户无效">
                    <check field="username" regex="[a-z A-Z 0-9]{6,}" msg="无效用户名"/>
                    <check field="password" regex="[a-z A-Z 0-9]{6,}" msg="无效密码"/>
                </check>
            </check>
        </check-list>
        <check-mapping>
            <class name="com.qianty.validater.ValidaterTest">
                <method name="test">
                    <check ref="checkUserInfo"/>
                </method>
            </class>
        </check-mapping>
    </check-config>