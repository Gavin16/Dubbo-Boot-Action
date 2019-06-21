package code.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *  Dubbo API 配置虽然看着直观简洁,但是这仅限是只有单级配置的时候; 当需要某个配置需要有多级 譬如
 *  某个服务的配置中还要对其中的方法做更细颗粒的配置时, 反而不方便。 因此：
 *
 *  建议: 应用级别的配置 如：ApplicationConfig,RegistryConfig,ProtocolConfig 配置可以放到API中来配置。
 *       而服务暴露或引用的配置,统一将配置放到xml文件中
 */
@Configuration
public class DubboApiConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service_consumer");
//        applicationConfig.setQosPort(33333);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("192.168.0.102:2181");
        registryConfig.setProtocol("zookeeper");
        registryConfig.setCheck(true);
        registryConfig.setClient("curator");
        registryConfig.setRegister(true);
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20881);
        return protocolConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        // 消费端应用级超时时间设置
        consumerConfig.setTimeout(7000);
        // 启动时检查,为true时若引用的服务不可用会阻止spring初始化; 一般项目上线时可以设置为true,研发阶段为false
        consumerConfig.setCheck(false);
        consumerConfig.setRetries(0);
        consumerConfig.setTimeout(10000);
        return consumerConfig;
    }

}
