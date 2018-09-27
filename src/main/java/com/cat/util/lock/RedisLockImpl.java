/*package com.cat.util.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ValueOperations;

*//**
 * Redis封装类, 包含一些常用的方法
 *
 * @author : xjding
 * @date :   2017-08-22 18:34
 *//*
public class RedisLock{

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    public boolean lock(String key, String value, long expire, TimeUnit expireUnit) {
        return lock(key, value, 0L, null, expire, expireUnit);
    }

    public boolean lock(final String key, final String value, long timeout, TimeUnit timeoutUnit, long expire, TimeUnit expireUnit) {
        try {
            long start = System.nanoTime();
            do {

                boolean flag = redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        return redisConnection.setNX(key.getBytes(Charsets.UTF_8),
                                value.getBytes(Charsets.UTF_8));
                    }
                });
                if (flag) {
                    ValueOperations<String, Object> ops = redisTemplate.opsForValue();
                    ops.set(key, value, expire, TimeUnit.MILLISECONDS);
                    return Boolean.TRUE;
                }
                Thread.sleep(100);
            } while (timeout != 0L && (System.nanoTime() - start) < timeoutUnit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (Exception e) {
            logger.error("[RedisService] 加锁失败, 错误原因: {}", e.getMessage());
            return Boolean.TRUE;
        }
    }

    *//**
     * 删除锁，强制执行，可能会释放其他线程占用的锁，
     * 慎用，慎用！！！
     *
     * @param key 锁键
     *//*
    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void release(Lock lock) {
        if (lock == null) {
            return;
        }
        if (redisTemplate.hasKey(lock.getName())) {
            String value = (String) redisTemplate.opsForValue().get(lock.getName());
            if (lock.getValue().equals(value)) {
                redisTemplate.delete(lock.getName());
            }
        }
    }
}*/
