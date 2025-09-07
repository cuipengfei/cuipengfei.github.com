package com.example;

import java.util.*;
import java.lang.reflect.Method;

public class RuntimeTypeBypassDemo {
    public static void main(String[] args) throws Exception {
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        
        // 编译时会报错，所以我们用反射绕过
        System.out.println("=== Using Reflection to Bypass Compile-time Check ===");
        
        // 获取 add 方法
        Method addMethod = List.class.getMethod("add", Object.class);
        
        // 运行时添加 Integer 到 String list - 成功！
        boolean result = (Boolean) addMethod.invoke(stringList, 42);
        System.out.println("Successfully added integer 42: " + result);
        
        // 打印列表内容
        System.out.println("List contents: " + stringList);
        System.out.println("List size: " + stringList.size());
        
        // 遍历时才会出错
        System.out.println("=== Iterating through list ===");
        try {
            for (String s : stringList) {  // 这里会 ClassCastException
                System.out.println("String: " + s);
            }
        } catch (ClassCastException e) {
            System.out.println("ClassCastException caught: " + e.getMessage());
            System.out.println("Error occurred when trying to cast Integer to String");
        }
        
        // 直接获取对象不会出错
        System.out.println("=== Direct access ===");
        Object obj = stringList.get(1);
        System.out.println("Object at index 1: " + obj + " (type: " + obj.getClass() + ")");
    }
}