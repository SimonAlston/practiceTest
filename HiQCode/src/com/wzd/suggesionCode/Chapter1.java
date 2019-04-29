package com.wzd.suggesionCode;


import org.junit.Test;

import java.text.NumberFormat;
import java.util.Random;

/**
 * @author wangzhengdong
 * @date 2019年04月22日
 * @description 包名全小写，类名首字母全大写，常量全部大写并用下划线分隔，变量采用驼峰命名法（Camel Case）命名
 */
public class Chapter1 {


    //字母"l"作为长整型标志时务必大写
    @Test
    public void suggestion1() {
        long i = 1l;//error
        long c = 1L;//correct
        System.out.println("i的两倍是：" + i * 2);
        System.out.println("c的两倍是：" + c * 2);
    }

    //务必让常量的值在运行期保持不变。
    public static final int RAND_CONST = new Random().nextInt();//error

    @Test
    public void suggestion2() {
        System.out.println("常量会变哦：" + RAND_CONST);
    }

    //务必使三元表达式【三元操作符】的类型保持一致
    @Test
    public void suggestion3() {
        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        System.out.println("两者是否相等：" + s.equals(s1));
    }


    //if user overload  --> 虚拟机会根据Method Signature 来判断使用哪个方法
    @Test
    public void suggestion4() {
        //499元的货物,打75折
        this.calPrice(49900, 75);
    }

    //简单折扣计算
    public void calPrice(int price, int discount) {
        float knockdownPrice = price * discount / 100.0F;
        System.out.println("简单折扣后的价格是:" + formateCurrency(knockdownPrice));
    }

    //复杂多折扣计算
    public void calPrice(int price, int... discounts) {
        float knockdownPrice = price;
        for (int discount : discounts) {
            knockdownPrice = knockdownPrice * discount / 100;
        }
        System.out.println("复杂折扣后的价格是:" + formateCurrency(knockdownPrice));
    }

    //格式化成本的货币形式
    private String formateCurrency(float price) {
        return NumberFormat.getCurrencyInstance().format(price / 100);
    }

    //在overload 当中有多个变长方法的时候, 传入空值或null,因为未指定类型, 所以虚拟机会找不到用那个方法
    //KSS原则 Keep It Simple, Stupid，即懒人原则
    @Test
    public void suggestion5() {
        Chapter1 client = new Chapter1();
        client.methodA("China", 0);
        client.methodA("China", "People");
        //error
//        client.methodA("China");
//        client.methodA("China", null);

        //current
        Integer[] in = null;
        client.methodA("China", in);
        String[] str = null;
        client.methodA("China", str);
    }

    public void methodA(String str, Integer... is) {
    }

    public void methodA(String str, String... strs) {
    }

    //使用多态[左父右子] 的时候, 编译看左, 运行看右
    @Test
    public void suggestion6() {
        //向上转型
        Base base = new Sub();
        base.fun(100, 50);
        //不转型
        Sub sub = new Sub();
//        sub.fun(100,50);
    }

    //基类
    class Base {
        void fun(int price, int... discounts) {
            System.out.println("Base...fun");
        }
    }

    //子类,覆写父类方法
    class Sub extends Base {
        @Override
        void fun(int price, int[] discounts) {
            System.out.println("Sub...fun");

        }
    }

    //后自增表达式, 是返回自增前的数

    /**
     * 步骤1　JVM把count值（其值是0）拷贝到临时变量区。
     *
     * 步骤2　count值加1，这时候count的值是1。
     *
     * 步骤3　返回临时变量区的值，注意这个值是0，没修改过。
     *
     * 步骤4　返回值赋值给count，此时count值被重置成0。
     */

    //C++中"count=count++"与"count++"是等效的，而在PHP中则保持着与Java相同的处理方式
    @Test
    public void suggestion7() {
        int count=0;
        for(int i=0;i<10;i++){
            count++;
        }
        System.out.println("count="+count);
    }

    @Test
    public void suggestion8() {

    }








































}