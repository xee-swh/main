package com.jvm;

/**
 * 双亲委派模型
 * 是Java类加载机制中的一种处理方式。该模型要求所有的类加载请求都由上层向下级别依次传递，
 * 直到遇到可以处理该请求的类加载器，如果没有找到，则抛出ClassNotFoundException异常。
 *
 * 双亲委派模型的优点是：
 * 1、避免重复加载：由于父加载器已经加载了类，所以子加载器就可以避免重复加载，从而提高了程序的运行效率。
 * 2、保证安全性：通过优先委派给父加载器进行加载，可以避免恶意代码通过自定义的类加载器来劫持JVM，从而保证了程序的安全性。
 * 3、统一管理：由于所有的类的加载都由顶层的启动类加载器加载，因此可以统一管理类的加载，从而更好地进行内存管理和JVM优化。
 */
public class TestEnvironment {
    public static void main(String[] args) {
        //启动类加载器
        System.out.println("1"+System.getProperty("sun.boot.class.path"));
        //扩展类加载器
        System.out.println("2"+System.getProperty("java.ext.dirs"));
        //应用类加载器
        System.out.println("3"+System.getProperty("java.class.path"));
    }
}
