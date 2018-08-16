package me.tanyp.controller;

import me.tanyp.param.HelloParam;
import me.tanyp.util.RedisManager;
import me.tanyp.util.SpringServiceFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tanyp on 2018/8/12
 */
@RestController
public class HelloController {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    @PostMapping("/hello")
    public void hello(@RequestBody HelloParam hello) {
        RedisManager redisManager = SpringServiceFactory.get().getService(RedisManager.class);
//        redisManager.put("tanyp","i.tanyp@qq.com");
        System.out.println(redisManager.get(String.class, "tanyp"));
        redisManager.delete("tanyp");
        System.out.println(redisManager);
        //默认非公平锁
        ReentrantLock reentrantLock = new ReentrantLock();
        //公平锁
        ReentrantLock reentrantLock1 = new ReentrantLock(true);
    }

    @GetMapping("/world")
    public void world() {
        System.out.println("world");
    }
//    public static void main(String[] args) {
//        if (reentrantLock.tryLock()){
//            reentrantLock.lock();
//            try {
//                System.out.println("Hello World");
//            }finally {
//                reentrantLock.unlock();
//            }
//        }
//    }

    public static void main(String[] args) {

        //基本数据类型是值传递（call by value）
        int i = 0;
        swap(i); //值传递不可以改变原变量的内容和地址
        System.out.println(i);

        //引用数据类型是引用传递（call by reference），
        Param param = new Param();
        param.setHello(100);
        param.setWorld("hello");
        swap(param);//引用传递不可以改变原变量的地址，但可以改变原变量的内容
        System.out.println(param.getHello());
        System.out.println(param.getWorld());
        String str = "Hello World";
        swap(str);
        System.out.println(str);
    }
    private static void swap(String str) {
        str = "World";
    }

    private static void swap(Param param) {
        param.setWorld("World");
        param.setHello(102);
    }

    private static void swap(int i) {
        i++;
        System.out.println(i);
    }


    static class Param {
        int hello;
        String world;

        public int getHello() {
            return hello;
        }

        public void setHello(int hello) {
            this.hello = hello;
        }

        public String getWorld() {
            return world;
        }

        public void setWorld(String world) {
            this.world = world;
        }
    }
}
