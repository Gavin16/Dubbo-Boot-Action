# Dubbo-Boot-Demo

Springboot 整合 Dubbo 多模块项目 DEMO

项目用途：
(1) 用于快速搭建新的应用
(2) 测试新用到的技术

项目结构：
项目分为服务提供端 dubbo-parent  和 服务消费端 dubbo-boot-consumer 

其中dubbo-parent 包括以下5部分：
(1) dubbo-api 用于定义api接口及传参实体类
(2) dubbo-common 常用工具类定义
(3) dubbo-dao 持久层访问
(4) dubbo-manager 访问三方接口
(5) dubbo-service 业务逻辑模块

 
文件配置：
动态配置使用Apollo实现

消息队列：
使用RocketMQ

缓存&登录Session:
使用Redis实现

数据库：
mysql主从模式







