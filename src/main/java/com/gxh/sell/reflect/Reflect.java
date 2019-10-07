package com.gxh.sell.reflect;

import com.gxh.sell.domain.Person;

public class Reflect {
    /**
     * 获取class对象的方式
     *      1：class.forName("全类名"):将字节码文件加入内存，返回Class对象
     *      2: 类名.class :通过类名的属性class获取
     *      3：对象.getClass : getClass()方法在Object类中定义着
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //方式一：
        Class cls1 = Class.forName("com.gxh.sell.domain.Person");
        System.out.println(cls1);

        //方式二:
        Class cls2 = Person.class;
        System.out.println(cls2);

        //方式三
        Person p = new Person();
        Class cls3 = p.getClass();
        System.out.println(cls3);
    }
}
