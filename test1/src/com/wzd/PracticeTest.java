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


    @Test
    public void stringTest() {
        String string1 = new String("abc");
        String string2 = new String("abc");
        String str1="abc";
        String str2="abc";
        System.out.println(string1==string2);
        System.out.println("=====================");
        System.out.println(str1==str2);
    }

    @Test
    public void assertTest() {
        int a =3;
        assert !false :  "raw ScriptObject not expected here";
        System.out.println(a);
    }

    @Test
    public void instanceofTest() {
        Double d = new Double(2.22);
        assert ( d instanceof Comparable) :"不是他的实现类或子类的实例";
    }


























}
