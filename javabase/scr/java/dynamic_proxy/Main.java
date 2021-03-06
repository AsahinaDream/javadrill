package dynamic_proxy;

import java.lang.reflect.*;

public class Main {
    static void customer(ProxyInterface pi) {
        pi.say();
    }

    public static void main(String[] args) {
        RealObject real = new RealObject();
        ProxyInterface proxy = (ProxyInterface) Proxy.newProxyInstance(
                ProxyInterface.class.getClassLoader(),
                new Class[]{ProxyInterface.class},
                new ProxyObject(real)
        );
        customer(proxy);
    }
}



interface ProxyInterface {
    void say();
}

//被代理类
class RealObject implements ProxyInterface {
    public void say() {
        System.out.println("i'm talking");
    }
}

//代理类，实现InvocationHandler 接口
class ProxyObject implements InvocationHandler {
    private Object proxied = null;

    public ProxyObject() {
    }

    public ProxyObject(Object proxied) {
        this.proxied = proxied;
    }

    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
        System.out.println("hello");
        arg1.invoke(proxied, arg2);
        System.out.println("are you ok");
        return arg1.invoke(proxied, arg2);
    }
}
