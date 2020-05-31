package com.demo.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource(value = "redis.properties")
public class RedisConfig {

//    @Value("${masterName}")
//    private String redisMasterName;
//    @Value("${sentinelNode1}")
//    private String redisSentinelNode1;
//    @Value("${sentinelNode2}")
//    private String redisSentinelNode2;
//    @Value("${sentinelNode3}")
//    private String redisSentinelNode3;

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


//    @Bean
//    public JedisSentinelPool sentinelPoolConfig(){
//        Set<String> sentinels = getSentinels();
//        GenericObjectPoolConfig poolConfig = getGenericPoolConfig();
//        JedisSentinelPool sentinelPool = new JedisSentinelPool(redisMasterName,sentinels,poolConfig);
//        return sentinelPool;
//    }

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

    /**
     * redis Sentinel 的 地址:端口 集合
     * 服务端redis sentinel 和 redis 数据节点需要确保:
     *      (1)注释掉bind 127.0.0.1 这一行
     *      (2)保护模式 protectedMode 设置为 no
     * 两处的配置都配置到位,否则可能出现客户端jedis 连接不上哨兵的情况
     *
     * @return
     */
//    private Set<String> getSentinels() {
//        Set<String> set = new HashSet<>();
//        set.add(redisSentinelNode1);
//        set.add(redisSentinelNode2);
//        set.add(redisSentinelNode3);
//        return set;
//    }

    /**
     * Redis 单节点工作模式
     * @return
     */
    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        setPoolConfig(config);
        JedisPool jedisPool = new JedisPool(config);
        return jedisPool;
    }

    private void setPoolConfig(JedisPoolConfig config) {
        config.setMaxIdle(Integer.parseInt(maxIdle));
        config.setMaxTotal(Integer.parseInt(maxTotal));
        config.setMinIdle(Integer.parseInt(minIdle));
        config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        // 每次检查连接有效性的连接个数
        config.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
        // 对两次对连接的扫描的时间间隔
        config.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        // 释放空闲的连接, 释放的连接最小空闲时长 30min = 1800000ms
        config.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        // 若空闲时长 > softMinEvictableIdleTimeMillis 且 空闲连接数 > maxIdle 则直接释放该连接
        // minEvictableIdleTimeMillis 的设置需要等到扫描后再判断是否释放连接
        config.setSoftMinEvictableIdleTimeMillis(Long.parseLong(softMinEvictableIdleTimeMillis));

        // 检查连接的时间点
        config.setTestWhileIdle(true);
        config.setTestOnBorrow(false);
        config.setTestOnCreate(false);
        config.setTestOnReturn(false);
        // 连接耗尽时是否阻塞  false将报错，true阻塞直到超时
        config.setBlockWhenExhausted(false);
    }
}
