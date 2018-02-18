package com.tanyouping.weixiao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanyp on 2018/2/8
 * StackOverflowError and OutOfMemoryError
 *
 */
public class ReSimple {

    private int stackSize = 0;

    public void add(){
        stackSize++;
        add();
    }

    public static void main(String[] args) {

        ReSimple test = new ReSimple();
        try {
            test.add();
        }catch (StackOverflowError e){
            System.out.println(test.stackSize);
            throw e;
        }


        List<ReSimple> strings = new ArrayList<>();
        try {
            while (true)
                strings.add(new ReSimple());
        }catch (OutOfMemoryError e){
            System.out.println(strings.size());
            throw  e;
        }

    }


}
