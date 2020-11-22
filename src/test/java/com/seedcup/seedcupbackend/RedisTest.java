package com.seedcup.seedcupbackend;


import com.seedcup.seedcupbackend.global.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisString() {

        String redisKey = "test:count";

        //存入数据
        redisTemplate.opsForValue().set(redisKey, 1);
        //获取值
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        //增加
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        //减少
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));

    }

    @Test
    public void redisHashes() {

        String redisKey = "test:user";

        redisTemplate.opsForHash().put(redisKey,"id", "1");
        redisTemplate.opsForHash().put(redisKey,"username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));

    }

    @Test
    public void redisLists() {

        String redisKey = "test:ids";

        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);
        redisTemplate.opsForList().leftPush(redisKey, 104);

        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey,0));
        System.out.println(redisTemplate.opsForList().range(redisKey,0, 2));
        redisTemplate.opsForList().leftPop(redisKey);

    }

    @Test
    public void redisSet() {
        String redisKey = "test:teachers";
        redisTemplate.opsForSet().add(redisKey, "刘备","张飞","关羽","赵云");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));

    }

    @Test
    public void SortedSets() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐玄奘", 90);
        redisTemplate.opsForZSet().add(redisKey, "猪八戒", 80);
        redisTemplate.opsForZSet().add(redisKey, "孙悟空", 70);
        redisTemplate.opsForZSet().add(redisKey, "沙和尚", 60);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 50);

        //统计一共有多少数据
        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        //统计分数
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "猪八戒"));
        //统计排名（由大到小）
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"猪八戒"));
        //统计某个范围内的数据（由小到大）
        System.out.println(redisTemplate.opsForZSet().range(redisKey,0, 2));
    }

    @Test
    public void redisKeys() {
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));
        redisTemplate.expire("test:count", 10, TimeUnit.SECONDS);
    }

    //多次访问同一个Key
    @Test
    public void redisBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    //编程式事务
    @Test
    public void redisTransactional() {
        Object object = redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {

                String redisKey = "test:tx";
                //启用事务
                redisOperations.multi();

                redisOperations.opsForSet().add(redisKey, "zhangsan","lisi","wangwu");

                //提交事务
                return redisOperations.exec();
            }
        });
        System.out.println(object);
    }
    //统计20万个重复数据的独立总数
    @Test
    public void redisHyperLogLog() {
        String redisKey = "test:hll:01";
        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }
        for (int i = 1; i <= 100000; i++) {
            int r = (int) (Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, r);
        }
        long size = redisTemplate.opsForHyperLogLog().size(redisKey);
        System.out.println(size);
    }

    //将三组数据合并，再统计合并后的重复数据的独立总数
    @Test
    public void redisHyperLogLogUnion() {

        String redisKey1 = "test:hll:02";
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey1, i);
        }

        String redisKey2 = "test:hll:03";
        for (int i = 5001; i <= 15000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        }

        String redisKey3 = "test:hll:04";
        for (int i = 10001; i <= 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        }
        String unionKey = "test:hll:union";
        redisTemplate.opsForHyperLogLog().union(unionKey, redisKey1,redisKey2,redisKey3);

        System.out.println(redisTemplate.opsForHyperLogLog().size(unionKey));
    }

    //统计一组数据的布尔值
    @Test
    public void BitMap() {
        String redisKey = "test:bm:01";

        //记录
        redisTemplate.opsForValue().setBit(redisKey, 1, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);

        //查询
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));

        //统计
        Object object = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(object);
    }

    //统计三组数据的布尔值，并对这三组数据做OR运算
    @Test
    public void testBitMapOperation() {
        String redisKey1 = "test:bm:02";
        redisTemplate.opsForValue().setBit(redisKey1, 0, true);
        redisTemplate.opsForValue().setBit(redisKey1, 1, true);
        redisTemplate.opsForValue().setBit(redisKey1, 2, true);

        String redisKey2 = "test:bm:03";
        redisTemplate.opsForValue().setBit(redisKey2, 2, true);
        redisTemplate.opsForValue().setBit(redisKey2, 3, true);
        redisTemplate.opsForValue().setBit(redisKey2, 4, true);

        String redisKey3 = "test:bm:04";
        redisTemplate.opsForValue().setBit(redisKey3, 4, true);
        redisTemplate.opsForValue().setBit(redisKey3, 5, true);
        redisTemplate.opsForValue().setBit(redisKey3, 6, true);

        String redisKey = "test:bm:or";
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), redisKey1.getBytes(),redisKey2.getBytes(),redisKey3.getBytes());
                return connection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(obj);
    }
}