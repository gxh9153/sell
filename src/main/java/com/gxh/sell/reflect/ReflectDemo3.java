package com.gxh.sell.reflect;

import com.gxh.sell.domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectDemo3 {
    /**
     * Class对象的功能：
     *      *获取功能
     *          1:获取成员变量们：
     *              *Field[] getFields()获取public类型的所有方法
     *              *Field getField(String name)
     *
     *              *Field[] getDeclaredFields()获取所有的成员变量方法，不考虑修饰符
     *              *Field getDeclaredField(String name)
     *          2:获取构造方法们：
     *              *Constructor[] getDeclaredConstructors()
     *              *Constructor getDeclaredConstructor(String name)
     *
     *              *Constructor[] getConstructors()
     *              *Constructor getConstructor(String name)
     *          1:获取成员方法们：
     *              *Method[] getMethod()
     *              *Method getMethod(String name)
     *
     *              *Method[] getDeclaredMethods()
     *              *Method getDeclaredMethod(String name)
     *
     */

    public static void main(String[] args) throws Exception {
        Class personClass = Person.class;
        Constructor[] constructors = personClass.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
        Constructor constructor = personClass.getConstructor(String.class,int.class);
        System.out.println(constructor);

        //创建对象
        Object person = constructor.newInstance("张三", 18);
        System.out.println(person);

        Constructor constructor1 = personClass.getConstructor();
        //创建对象
        Object person1 = constructor1.newInstance();
        System.out.println(person1);

        //创建对象
        Object o = personClass.newInstance();
        System.out.println(o);
    }

}
