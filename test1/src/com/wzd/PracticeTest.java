package com.wzd;

import org.junit.Test;

public class PracticeTest {
    @Test
    public void test1() {
        System.out.println((1&2)==0);
        System.out.println(((0)&(0)) !=0);
        System.out.println(0&0);
    }


    @Test
    public void test2() {
        Boolean b = true && false || false ;
        Boolean c = false || true && false;
        System.out.println(c);
    }
}
