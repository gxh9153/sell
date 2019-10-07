package com.gxh.sell.domain;

import lombok.Data;

@Data
public class Person {

    private String name;
    private int age;

    public String a;
    protected String b;
    String c;
    private String d;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }
    public void eat(){
        System.out.println("今天我们去吃些什么呢？ ");
    }
    public void eat(String food){
        System.out.println(food);
    }
}
