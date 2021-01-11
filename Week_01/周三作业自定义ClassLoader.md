```java
package com.java.learn.jvm;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * 作业：
 * 1. 完成一个自定义的类加载器，将对应的xlass文件读取为最后的对象，并执行其中的方法
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {


        try {
            // 加载并初始化Hello类 com.java.learn.jvm.Hello
//            Hello hello = (Hello) new MyClassLoader().findClass("com.java.learn.jvm.Hello").newInstance();
//            hello.hello();
            // 这里无法获得对应的Hello对象，因为我从作业文件夹中读取的xlass内容和项目文件夹中的Hello最后的字节码不一致
            Class class_ = new MyClassLoader().findClass("");
            Method[] methods = class_.getDeclaredMethods();
            Object object = class_.newInstance();
            for (Method method : methods) {
                method.invoke(object);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 这个需要在控制台输入base64 hello.class得到
//        String helloBase64 = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAHAAgHABYMABcAGAEAE0hlbGxvLCBjbGFzc0xvYWRlciEHABkMABoAGwEABUhlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACAABAAkAAAAdAAEAAQAAAAUqtwABsQAAAAEACgAAAAYAAQAAAAEAAQALAAgAAQAJAAAAJQACAAEAAAAJsgACEgO2AASxAAAAAQAKAAAACgACAAAABAAIAAUAAQAMAAAAAgAN";
        // 这个是xclass文件中的 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
        String helloBase64_ = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////+//3//r//v/z/////f/y";
        byte[] bytes = decode(helloBase64_);
        // byte -128 ～ 127
        for (int i = 0; i < bytes.length; i++) {
            // 重新减一次 变成原来的class文件
            bytes[i] = (byte) (255 - bytes[i]);
        }
        // 这里name 其实最大作用用来得到其字节码数组（通过输入流，不过这里直接使用控制台得到文件内容，name作用就不大了）
        // 然后这里传Hello是传类名吧 要和字节码转换后的类名一致
        return defineClass("Hello", bytes, 0, bytes.length);
    }


    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
```