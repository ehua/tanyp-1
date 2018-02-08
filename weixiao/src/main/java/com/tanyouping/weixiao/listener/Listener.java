package com.tanyouping.weixiao.listener;

import com.tanyouping.weixiao.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Tan Youping on 2018/1/9
 */
@Component
public class Listener {


    @Autowired
    private RedisManager redisManager;

    @Scheduled(cron = "*/5 * * * * ?")//每隔5秒执行一次
    public void handle(){
//        Thread thread = new Thread(){
//
//            @Override
//            public void run(){
//                Jedis jedis = null;
//                String aaa = null;
//
//                try {
//                    jedis = redisManager.getResource();
//                    aaa =  jedis.rpop("angel");
//                    System.out.println(aaa);
//                    System.out.println("spring定时任务");
//                }catch (Exception e){
//                    e.printStackTrace();
//                    jedis.rpush("angel",aaa);
//                }finally {
//                    if (jedis != null)
//                        jedis.close();
//                }
//            }
//        };
//
//        thread.start();
    }
}
