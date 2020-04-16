package code.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Class: RedisConfig
 * @Description:
 *
 * 用户登录session 配置
 *
 * @Author: Minsky
 * @Date: 2019/9/9 10:36
 * @Version: v1.0
 */
@Configuration
@PropertySource(value = "session-redis.properties")
public class RedisConfig {

    @Value("${masterName}")
    private String redisMasterName;
    @Value("${sentinelNode1}")
    private String redisSentinelNode1;
    @Value("${sentinelNode2}")
    private String redisSentinelNode2;
    @Value("${sentinelNode3}")
    private String redisSentinelNode3;

    @Value("${maxIdle}")
    private String maxIdle;
    @Value("${maxTotal}")
    private String maxTotal;
    @Value("${minIdle}")
    private String minIdle;
    @Value("${numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;

    @Value("${maxWaitMillis}")
    private String maxWaitMillis;
    @Value("${timeBetweenEvictRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${minEvicIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    @Value("${softMinEvicIdleTimeMillis}")
    private String softMinEvictableIdleTimeMillis;

    @Bean
    public JedisSentinelPool jedisSentinelPool(){
        Set<String> sentinels = getSentinels();
        GenericObjectPoolConfig poolConfig = getGenericPoolConfig();
        JedisSentinelPool sentinelPool = new JedisSentinelPool(redisMasterName,sentinels,poolConfig);
        return sentinelPool;
    }


    /**
     * 连接池配置
     * @return
     */
    private GenericObjectPoolConfig getGenericPoolConfig(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal));
        poolConfig.setMinIdle(Integer.parseInt(minIdle));

        /** 时间相关设置 **/
        // 获得连接的最大等待毫秒数
        poolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        // 对两次对连接的扫描的时间间隔
        poolConfig.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        // 释放空闲的连接, 释放的连接最小空闲时长 30min = 1800000ms
        poolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        // 若空闲时长 > softMinEvictableIdleTimeMillis 且 空闲连接数 > maxIdle 则直接释放该连接
        // minEvictableIdleTimeMillis 的设置需要等到扫描后再判断是否释放连接
        poolConfig.setSoftMinEvictableIdleTimeMillis(Long.parseLong(softMinEvictableIdleTimeMillis));

        /** 对连接池中连接的有效性的检查**/
        // 每次检查连接有效性的连接个数
        poolConfig.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
        // 检查连接的时间点
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnCreate(false);
        poolConfig.setTestOnReturn(false);
        // 连接耗尽时是否阻塞  false将报错，true阻塞直到超时
        poolConfig.setBlockWhenExhausted(false);
        return poolConfig;
    }

    private Set<String> getSentinels() {
        Set<String> set = new HashSet<>();
        set.add(redisSentinelNode1);
        set.add(redisSentinelNode2);
        set.add(redisSentinelNode3);
        return set;
    }
}
