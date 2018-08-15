package me.tanyp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tanyp on 2018/8/14
 */
public class Hello<E> {

    private final Lock lock = new ReentrantLock();
    private final List<E> list = new ArrayList<>();
    private static int i = 0;

    public void set(E o) {
        lock.lock();
        try {
            i++;
            list.add(o);
            System.out.println(Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {

        final Hello<String> lockExample = new Hello<>();

        Runnable syncThread = new Runnable() {
            @Override
            public void run() {
                while (i < 6) {
                    try {
                        lockExample.set(String.valueOf(i));
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable lockingThread = new Runnable() {
            @Override
            public void run() {
                while (i < 6) {
                    try {
                        lockExample.set(String.valueOf(i));
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(syncThread, "syncThread");
        Thread t2 = new Thread(syncThread, "lockingThread");
        t1.start();
        t2.start();

    }
}
