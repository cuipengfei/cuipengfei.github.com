package com.example;

import java.util.List;
import java.util.ArrayList;

public class GenericClassDemo<T extends Number> {
    private List<T> items;
    
    public GenericClassDemo() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(T item) {
        items.add(item);
    }
    
    public List<T> getItems() {
        return items;
    }
    
    public <E> void processWithGenericMethod(E element, List<? super E> sink) {
        sink.add(element);
    }
}