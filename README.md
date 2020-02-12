# SSM 框架整合
##1 SSM 整合工程结构

整体工程的目录结构：

<div align="center"><img src="https://pic.downk.cc/item/5e438c322fb38b8c3cc75b01.jpg"></div>
<div align="center"><img src="https://pic.downk.cc/item/5e43872c2fb38b8c3cc65280.jpg"></div>
创建 web 工程步骤：

1. 创建一个 Maven 工程
2. 右键选择工程根目录下的 `Add Framework Support`：

<div align="center"><img src="https://pic.downk.cc/item/5e43881f2fb38b8c3cc67ec4.jpg"></div>
3. 勾选 `Web Application`，选择 `web.xml` 的版本：

<div align="center"><img src="https://pic.downk.cc/item/5e4388b72fb38b8c3cc69a03.jpg"></div>
4. 会自动生成如下的 web 目录，其中 `WEB-INF` 子目录下的资源是安全的，外界无法直接通过地址栏访问，`web.xml` 配置了 web 项目启动时需要加载的信息：

<div align="center"><img src="https://pic.downk.cc/item/5e4389242fb38b8c3cc6aed2.jpg"></div>
<br>

##2 具体配置

* 数据库：

```mysql
CREATE TABLE t_customer (
	id INT(32) PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(50),   
	jobs VARCHAR(50),
	phone VARCHAR(16)
);

INSERT INTO t_customer VALUES(1, 'joy', 'doctor', '11111111111');
INSERT INTO t_customer VALUES(2, 'jack', 'teacher', '22222222222');
INSERT INTO t_customer VALUES(3, 'tom', 'worker', '33333333333');
```

* `pom.xml` 文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>SSM</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring-version>5.2.3.RELEASE</spring-version>
        <slf4j.version>2.0.0-alpha1</slf4j.version>
        <jackson-version>2.10.1</jackson-version>
    </properties>

    <dependencies>
        <!-- junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

        <!-- spring -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring-version}</version>
        </dependency>

        <!-- spring test-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring-version}</version>
        </dependency>

        <!-- spring-jdbc(spring事务管理) -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring-version}</version>
        </dependency>

        <!-- aspectJ AOP -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.5</version>
        </dependency>

        <!-- mysql 驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.19</version>
        </dependency>

        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.3</version>
        </dependency>

        <!-- 数据库连接池 c3p0 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>

        <!-- mybatis 和 spring 整合的包 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.3</version>
        </dependency>

        <!-- JSON 转换注解包 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>${jackson-version}</version>
        </dependency>

        <!-- JSON 转换核心包 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>${jackson-version}</version>
        </dependency>

        <!--  JSON 转换的数据绑定包 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson-version}</version>
        </dependency>

        <!-- servlet -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>

        <!-- jsp -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>

        <!-- slf4j 日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
    </dependencies>
</project>
```

* `web.xml` 文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- 设置 Spring 配置文件的路径，多个配置文件时，以逗号分隔 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:beans.xml</param-value>
    </context-param>
    <!-- 以 ContextLoaderListener 方式启动 Spring 容器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!--配置 SpringMVC 前端控制器 DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--初始化时加载 spring MVC 的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <!--表示服务器启动时立即加载此 Servlet-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- 设置被前端控制器拦截的 URL 格式-->
    <!--/  拦截所有的请求（不包括 .jsp 这类有后缀的资源文件）-->
    <!--/* 拦截所有的请求（包括 .jsp 这类有后缀的资源文件）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!-- 配置编码过滤器，将请求内容的编码统一设置为 UTF-8 -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

* Spring 核心配置文件 `beans.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx.xsd
	http://www.springframework.org/schema/context
	https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 开启包扫描，扫描 service 层和 AOP 切面类 -->
    <context:component-scan base-package="com.it.service, com.it.aspect"/>
    <!-- 启动基于注解的声明式 AspectJ 支持 -->
    <aop:aspectj-autoproxy/>

    <!-- 配置数据库数据源，c3p0连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 配置连接池属性 -->
        <!-- 数据库驱动 -->
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <!-- 连接数据库的 url，如果使用 MySQL8 要加 allowPublicKeyRetrieval=true -->
        <property name="jdbcUrl"
                  value="jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&amp;useSSL=false&amp;serverTimezone=Hongkong&amp;allowPublicKeyRetrieval=true"/>
        <!-- 连接数据库的用户名 -->
        <property name="user" value="root"/>
        <!-- 连接数据库的密码 -->
        <property name="password" value="123456"/>
    </bean>

    <!-- 配置 Spring 框架声明式事务管理 -->
    <!-- 事务管理器，依赖于数据源 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 开启 Spring 事务注解 -->
    <!-- transaction-manager 可以不指定，会自动装配 transactionManager-->
    <tx:annotation-driven transaction-manager="transactionManager"/>

    <!-- 配置 Mybatis 的 SqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入数据源 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 指定 Mybatis 核心配置文件位置 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!-- 配置 mapper 扫描器，让指定包下的 Mapper 接口成为 Spring 容器中的 Bean 对象 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- mapper 接口所在包路径 -->
        <property name="basePackage" value="com.it.mapper"/>
        <!-- 指定 SqlSessionFactory 的 Bean 名称 -->
        <!-- sqlSessionFactoryBeanName 可以不指定，会自动装配 sqlSessionFactory-->
        <!-- 但是，如果使用多个 DataSource，自动装配可能会失效，此时需要指定 -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
</beans>
```

* mybatis 核心配置文件 `mybatis-config.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 配置别名 -->
    <typeAliases>
        <package name="com.it.pojo"/>
    </typeAliases>

    <!-- 配置 Mapper 的位置 -->
    <mappers>
        <mapper resource="com/it/mapper/CustomerMapper.xml"/>
    </mappers>
</configuration>
```

* `spring-mvc` 核心配置文件 `springmvc-config.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:mvn="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 指定注解扫描包，扫描 @Controller 注解的类 -->
    <context:component-scan base-package="com.it.controller"/>

    <!-- 配置 mvc 注解驱动 -->
    <!-- 要使 @RequestMapping 注解生效，必须向上下文注册这 RequestMappingHandlerMapping
		 和 RequestMappingHandlerAdapter 这两个 Bean，而该驱动会自动注册这两个 Bean。 -->
    <!-- 并且提供对读写 XML 和读写 JSON 等功能的支持 -->
    <mvc:annotation-driven/>

    <!-- 配置静态资源的访问映射，此配置中的文件，将不会被前端控制器拦截 -->
    <!-- location：用于定位需要访问的本地静态资源文件路径，具体到某个文件夹 -->
    <!-- mapping：用于匹配静态资源全路径，其中"/**" 表示文件夹下的某个具体文件 -->
    <mvn:resources location="/js/" mapping="/js/**"/>
    <mvn:resources location="/css/" mapping="/css/**"/>

    <!--定义视图解析器-->
    <!--真正的视图路径为： 前缀 + 逻辑视图名 + 后缀-->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

* 日志文件 `log4j.propertis`：

```properties
# Set root category priority to INFO and its only appender to CONSOLE.
#log4j.rootCategory=INFO, CONSOLE            debug   info   warn error fatal
log4j.rootCategory=info, CONSOLE, LOGFILE

# Set the enterprise logger category to FATAL and its only appender to CONSOLE.
log4j.logger.org.apache.axis.enterprise=FATAL, CONSOLE
# com.it.mapper 包使用 DEBUG 级别日志
log4j.logger.com.it.mapper=DEBUG

# CONSOLE is set to be a ConsoleAppender using a PatternLayout.
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n

# LOGFILE is set to be a File appender using a PatternLayout.
log4j.appender.LOGFILE=org.apache.log4j.FileAppender
log4j.appender.LOGFILE.File=E:/axis.log
log4j.appender.LOGFILE.Append=true
log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
log4j.appender.LOGFILE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n
```

* pojo 类：

```java
package com.it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;        // 客户 id
    private String username;   // 客户名称
    private String jobs;       // 职业
    private String phone;      // 电话
}
```

* mapper 层接口及其映射文件：

```java
package com.it.mapper;

import com.it.pojo.Customer;

public interface CustomerMapper {
    // 根据客户 id 获取客户信息
    Customer findCustomerById(Integer id);

    // 添加客户信息
    Integer addCustomer(Customer customer);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.it.mapper.CustomerMapper">
    <!-- 根据客户 id 查询客户信息 -->
    <select id="findCustomerById" resultType="customer">
        select * from t_customer where id = #{id}
    </select>

    <!-- 添加客户信息 -->
    <insert id="addCustomer">
    insert into t_customer(username, jobs, phone) values (#{username}, #{jobs}, #{phone})
</insert>
</mapper>
```

* service 层接口及其实现类：

```java
package com.it.service;

import com.it.pojo.Customer;

public interface CustomerService {
    // 查询客户信息
    Customer queryCustomerById(Integer id);

    // 添加新客户
    void insertCustomer(Customer customer);
}
```

```java
package com.it.service.impl;

import com.it.mapper.CustomerMapper;
import com.it.pojo.Customer;
import com.it.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer queryCustomerById(Integer id) {
        return customerMapper.findCustomerById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertCustomer(Customer customer) {
        customerMapper.addCustomer(customer);
    }
}
```

* controller 层：

```java
package com.it.controller;

import com.it.pojo.Customer;
import com.it.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/query/{id}")
    public String findCustomerService(@PathVariable Integer id, Model model) {
        Customer customer = customerService.queryCustomerById(id);
        model.addAttribute("customer", customer);
        // 返回客户信息展示页面
        return "customer";
    }
}
```

* AOP 实现类：

```java
package com.it.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect   // 该类是一个切面类
@Component
public class MyAspect {

    // 获取日志对象
    private static final Logger logger= LoggerFactory.getLogger(MyAspect.class);

    // 定义切入点表达式
    @Pointcut("execution(* com.it.service.*.*(..))")
    private void myPointCut() {
    }

    // 前置通知(切入点方法执行之前执行)，注解内的值既可以是表达式，也可以是表达式的引用
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        logger.info("进入了" + joinPoint.getSignature().getName() + "方法");
    }

    @AfterReturning("myPointCut()")
    public void myAfter(JoinPoint joinPoint) {
        logger.info("退出了" + joinPoint.getSignature().getName() + "方法");
    }
}
```

* 展示视图 `customer.jsp`：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户信息</title>
</head>
<body>
    <table border="1">
        <tr>
            <td>编号</td>
            <td>名称</td>
            <td>职业</td>
            <td>电话</td>
        </tr>
        <tr>
            <td>${customer.id}</td>
            <td>${customer.username}</td>
            <td>${customer.jobs}</td>
            <td>${customer.phone}</td>
        </tr>
    </table>
</body>
</html>
```

<br>

##3 测试

### 3.1 Mapper 层测试

```java
package com.it.mapper;

import com.it.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class CustomerMapperTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCustomerService() {
        System.out.println(customerService.queryCustomerById(1));
    }
}
```

```
- 进入了queryCustomerById方法
- ==>  Preparing: select * from t_customer where id = ?
- ==> Parameters: 1(Integer)
- <==      Total: 1
- 退出了queryCustomerById方法
Customer(id=1, username=joy, jobs=doctor, phone=11111111111)
```

<br>

### 3.2 Controller 层测试

* 配置 tomcat：

<div align="center"><img src="https://pic.downk.cc/item/5e43abbd2fb38b8c3ccd441a.jpg"></div>
<div align="center"><img src="https://pic.downk.cc/item/5e43b6052fb38b8c3ccf1cd2.jpg"></div>
* 直接启动 tomcat 会直接报错，查看 web 工程输出文件 out，发现少了 jar 包依赖的文件夹 lib：

<div align="center"><img src="https://pic.downk.cc/item/5e43b6b32fb38b8c3ccf3bac.jpg"></div>
因此，在启动 tomcat 前我们需要手动导 jar 包：

<div align="center"><img src="https://pic.downk.cc/item/5e22ac902fb38b8c3c5564d3.jpg"></div>
<div align="center"><img src="https://pic.downk.cc/item/5e43b80b2fb38b8c3ccf7333.jpg"></div>
* 再次启动 tomcat，如果启动成功，浏览器访问 `http://localhost:8080/query/1`：

<div align="center"><img src="https://pic.downk.cc/item/5e43b9572fb38b8c3ccfb1f6.jpg"></div>
