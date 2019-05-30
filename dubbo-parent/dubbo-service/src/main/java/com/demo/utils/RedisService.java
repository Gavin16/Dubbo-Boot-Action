package com.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.List;
import java.util.Map;

@Component
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisSentinelPool jedisSentinelPool;


    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,long time){
        Jedis jedis = null;
        try {
            if(time>0){
                jedis = jedisSentinelPool.getResource();
                jedis.expire(key, (int) time);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService--expire--执行异常:{}",e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {
            logger.error("RedisService--getExpire--执行异常:{}",e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return -1;
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("RedisService--hasKey--执行异常:{}",e);
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String ... key){
        Jedis jedis = null;

        try {
            jedis = jedisSentinelPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("RedisService--del--执行异常:{}",e);
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("RedisService--get--执行异常:{}",e);
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public Boolean set(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.set(key,value);
            return true;
        } catch (Exception e) {
            logger.error("RedisService--del--执行异常:{}",e);
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true--成功 false--失败
     */
    public Boolean set(String key,String value,int time){
        Jedis jedis = null;
        try {
            if(time>0){
                jedis = jedisSentinelPool.getResource();
                jedis.setex(key, time, value);
            }else{
                jedis.set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService--set--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return key对应的新的值
     */
    public Long incr(String key, long delta){
        Jedis jedis = null;
        try {
            if(delta<0){
                return null;
            }
            jedis = jedisSentinelPool.getResource();
            return jedis.incrBy(key, delta);
        } catch (Exception e) {
            logger.error("RedisService--incr--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decr(String key, long delta){
        Jedis jedis = null;
        try {
            if(delta<0){
                return null;
            }
            jedis = jedisSentinelPool.getResource();
            return jedis.decrBy(key, delta);
        } catch (Exception e) {
            logger.error("RedisService--incr--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * HashGet
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return 值
     */
    public String hget(String key,String field){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("RedisService--hget--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key,String field,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hset(key, field,value);
            return true;
        } catch (Exception e) {
            logger.error("RedisService--hset--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key,String field,String value,int time) {
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hset(key, field, value);
            if(time>0){
                jedis.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService--hset--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }


    /**
     * 获取hashKey对应的所有键值
     * @param key
     * @param fields key中的多个域
     * @return key中多个fields对应的值
     */
    public List<String> hmget(String key, String ... fields){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error("RedisService--hmget--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return ok -- 成功 null -- 失败
     */
    public Boolean hmset(String key, Map<String,String> map){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hmset(key, map);
            return true;
        } catch (Exception e) {
            logger.error("RedisService--hmset--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public Boolean hmset(String key, Map<String,String> map, int time){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hmset(key, map);
            if(time > 0){
                jedis.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService--hmset--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }




    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param fields 项 可以使多个 不能为null
     */
    public void hdel(String key, String... fields){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.hdel(key, fields);
        } catch (Exception e) {
            logger.error("RedisService--hdel--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String field){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hexists(key, field);
        } catch (Exception e) {
            logger.error("RedisService--hHasKey--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return false;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param field 项
     * @param by 要增加几(大于0)
     * @return
     */
    public Long hincr(String key, String field, long by){
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            return jedis.hincrBy(key, field, by);
        } catch (Exception e) {
            logger.error("RedisService--hincr--执行异常:{}", e);
        }finally {
            if(null != jedis){
                jedis.close();
            }
        }
        return null;
    }

}
