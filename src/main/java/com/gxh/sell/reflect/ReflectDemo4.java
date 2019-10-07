package com.gxh.sell.reflect;

import com.gxh.sell.domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectDemo4 {
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

        Method eat = personClass.getMethod("eat");
        Person person = new Person();
        eat.invoke(person);

        Person person1 = (Person) personClass.newInstance();
        eat.invoke(person1);

        Method eat1 = personClass.getMethod("eat", String.class);
        eat1.invoke(person,"要不我们去吃海鲜吧");
        eat1.invoke(person1,"去吃火锅也是可以的");

        System.out.println("---------------------------------");
        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            System.out.println(method);
            System.out.println(name);
        }

        String name = personClass.getName();
        System.out.println(name);
    }

}
