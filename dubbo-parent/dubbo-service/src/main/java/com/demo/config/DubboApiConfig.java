package com.demo.config;

import com.alibaba.dubbo.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 配置覆盖顺序
 *
 * 配置颗粒度维度来看   : method==>service==> application
 * 消费端与服务端维度来看: consumer ==> provider
 * 综合来看: 优先级从高到底
 *         (1)消费端 method
 *         (2)服务端 method
 *         (3)消费端 service
 *         (4)服务端 service
 *         (5)消费端 application
 *         (6)服务端 application
 */
@Configuration
public class DubboApiConfig {

    /**
     * Application 配置
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig appConfig = new ApplicationConfig();
        appConfig.setName("dubbo-springboot-action");
        return appConfig;
    }


    /**
     * registry 配置
     * @return
     */
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.0.102:2181");

        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
//        protocolConfig.setSerialization("kryo");
        protocolConfig.setPort(20886);
        // 最大接收的连接数,长连接连接多长时间?什么时候释放
        protocolConfig.setAccepts(1000);
        return protocolConfig;
    }

    /**
     * provider 调用信息写入Dubbo监控
     * @return
     */
    @Bean
    public MonitorConfig monitorConfig(){
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setAddress("192.168.0.102:7070");
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }


    /**
     * 集群容错策略：
     *  (1) failover 默认， 失败后可以发起重试，可用于幂等性操作: 读取，修改 和 删除; 一般用于读操作
     *  (2) failfast 快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录
     *  (3) failsafe 失败安全,出现异常时,直接忽略
     *  (4) failback 失败后自动恢复,(不知道怎么恢复) 可以定时重发,通常用于消息通知操作
     *  (5) forking  并行调用多个服务器,只要有一个成功就返回. 通常用于实时性要求较高的读操作
     * @return
     */
    @Bean
    public ProviderConfig setDubboProviderConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setCluster("failover");
        return providerConfig;
    }


    // 不使用 Dubbo 自带的Service注解时, 是否可以通过以下设置暴露服务??
//    public ServiceConfig setServiceConfig(){
//        ServiceConfig<Object> serviceConfig = new ServiceConfig<>();
//        serviceConfig.setRef();
//    }

}

