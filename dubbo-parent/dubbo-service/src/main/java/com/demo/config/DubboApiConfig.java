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
 *         (4)服务端 method
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
        appConfig.setName("dubbo-springboot-demo");
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
        protocolConfig.setPort(20886);
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
     * 失败策略配置
     * @return
     */
    @Bean
    public ProviderConfig setDubboProviderConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setCluster("failsafe");
        return providerConfig;
    }
}

