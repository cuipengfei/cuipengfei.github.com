package com.example;

import java.lang.reflect.*;
import java.util.List;

public class GenericReflectionTest {
    public static void main(String[] args) throws Exception {
        Class<GenericClassDemo> clazz = GenericClassDemo.class;
        
        System.out.println("=== Class Level Generic Info ===");
        TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
        for (TypeVariable<?> typeParam : typeParameters) {
            System.out.println("Type Parameter: " + typeParam.getName());
            Type[] bounds = typeParam.getBounds();
            for (Type bound : bounds) {
                System.out.println("  Bound: " + bound.getTypeName());
            }
        }
        
        System.out.println("\n=== Field Generic Info ===");
        Field itemsField = clazz.getDeclaredField("items");
        Type fieldType = itemsField.getGenericType();
        System.out.println("Field type: " + fieldType);
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) fieldType;
            Type[] actualTypes = pType.getActualTypeArguments();
            System.out.println("  Actual type argument: " + actualTypes[0]);
        }
        
        System.out.println("\n=== Method Generic Info ===");
        Method addMethod = clazz.getMethod("addItem", Number.class);
        Type[] paramTypes = addMethod.getGenericParameterTypes();
        System.out.println("addItem parameter type: " + paramTypes[0]);
        
        Method processMethod = clazz.getMethod("processWithGenericMethod", Object.class, List.class);
        TypeVariable<?>[] methodTypeParams = processMethod.getTypeParameters();
        System.out.println("processWithGenericMethod type parameter: " + methodTypeParams[0].getName());
        Type[] methodParamTypes = processMethod.getGenericParameterTypes();
        System.out.println("  First parameter type: " + methodParamTypes[0]);
        System.out.println("  Second parameter type: " + methodParamTypes[1]);
        
        System.out.println("\n=== Runtime Instance Type Info ===");
        GenericClassDemo<Integer> instance = new GenericClassDemo<>();
        // Note: Cannot get actual type parameter Integer here due to type erasure!
        System.out.println("Instance class: " + instance.getClass());
        System.out.println("Instance type parameter: Cannot retrieve (type erasure)");
    }
}