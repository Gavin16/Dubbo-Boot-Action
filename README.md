# Dubbo-Boot-Demo
SpringBoot 整合 Dubbo 多模块微服务项目,包括

接口定义包：dubbo-service-interfaces

微服务包  ：dubbo-parent

服务应用包：dubbo-springboot-consumer

工作方式： dubbo-parent 和 dubbo-springboot-consumer 共同依赖 dubbo-service-interfaces 包，dubbo-springboot-consumer 通过RPC 调用 dubbo-parent 获取服务并提供REST接口供前端访问。
