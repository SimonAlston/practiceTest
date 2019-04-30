package com.wzd.utils;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class ChineseSymbolToEnglish {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line=null;
        try {
            System.out.println("请输入要转换的字符：");

            while ((line=reader.readLine())!=null){
                String replace = line.replace('（', '(');
                replace =replace.replace('）',')');
                replace =replace.replace('：',':');
                replace =replace.replace('；',';');
                replace =replace.replace('“','\"');
                replace =replace.replace('”','\"');
                replace =replace.replace("……","...");
                replace =replace.replace('，',',');
                replace =replace.replace('’','\'');
                replace =replace.replace('‘','\'');
                replace=replace.replace("＜","<");
                replace=replace.replace("＞",">");
                replace=replace.replace("fnal","final");
                replace=replace.replace("_"," ");
                System.out.println(replace);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void changeStream() {
        String data = "Hello, World!\r\n";
        InputStream stdin = System.in;
        /*try {*/
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scanner = new Scanner(System.in);
            System.out.println(scanner.nextLine());
//        } /*finally {
//            System.setIn(stdin);
//        }*/
    }

    @Test
    public void test1() {
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        System.out.println(next);
    }

    @Test
    public void testMethodName() {
        String s = "abcd";
        String replace = s.replace('a', 'c');
        System.out.println(replace);
    }
}
