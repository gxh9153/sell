package com.gxh.sell.reflect;

import com.gxh.sell.domain.Person;

import java.lang.reflect.Field;

public class ReflectDemo2 {
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
        Field[] fields = personClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("------------------------------");
        Person p = new Person();
        Field a = personClass.getField("a");
        Object value = a.get(p);
        System.out.println(value);
        a.set(p,"张三");
        System.out.println(p);

        System.out.println("==============================");
        Field[] fields1 = personClass.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field);
        }
        Field d = personClass.getDeclaredField("d");
        //忽略访问修饰符的安全检查
        d.setAccessible(true);
        Object value1 = d.get(p);
        d.set(p,"李四");
        System.out.println(value1);
        System.out.println(p);
    }

}
