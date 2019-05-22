package com.demo.config;

import com.alibaba.dubbo.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo 配置覆盖顺序
 * (1) -Ddubbo.register.address=zookeeper://127.0.0.1:2181  ==>
 * (2) dubbo.reg
 * (3)
 * (4)
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

