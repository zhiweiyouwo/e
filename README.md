#项目演示地址
http://www.17jee.com     QQ交流群: 540553957
#目的
E框架的目的是让开发不必从零开始开发，让开发者只关注自己的业务功能。
#技术说明
* 此框架采用前后台分开，前后台都可以单独部署，前端采用轻量级的扁平化设计（html+javascript+Bootstrap）, 会自动针对不同的屏幕尺寸调整页面，使其在各个尺寸的屏幕上表现良好。 
* 后端采用Spring boot，它使我们更容易去创建基于Spring的独立和产品级的可以即时运行的应用和服务。直接嵌入Tomcat 或Jetty服务器，不需要部署WAR 文件，可直接运行jar文件。
* 系统权限框架采用Shiro，实现前后台权限校验。
* 持久层采用JPA ，并实现类ibatis的查询功能；数据响应该采用json格式。
* 服务采用REST形式，能够方便的与手机app进行对接，集成swagger能够在线查看RESTAPI 文档和在线测试服务接口
* 支持单点登录，可以多系统进行菜单集成，形成一个portal。
* 支持高并发和水平扩展，支持Session的统一存储。
* 项目采用gradle构建，能够方便的将各项目进行按需组装。


#多系统构架
![](e-core-static/src/main/resources/public/readme/jiagou.jpg)