package com.gxh.sell.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //1:加载配置文件
        //1.1创建properties对象
        Properties properties = new Properties();
        //1.2加载配置文件，转换为一个集合
        //1.3获取class目录下的配置文件
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("pro.properties");
        properties.load(is);

        //2获取配置文件中定义的数据
        String className = properties.getProperty("ClassName");
        String methodName = properties.getProperty("MethodName");

        //3加载类进内存
        Class cls = Class.forName(className);

        //4创建对象
        Object obj = cls.newInstance();
        //获取方法对象
        Method method = cls.getMethod(methodName);

        //执行方法
        method.invoke(obj);

    }
}
