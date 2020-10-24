package com.tjrac.studentAffairs.service.user.proxy;

import com.tjrac.studentAffairs.service.user.TeacherService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * UserServiceProxy 用户业务代理抽象类
 *
 * @author : xziying
 * @create : 2020-10-24 10:08
 */
public abstract class UserServiceProxy<T> implements InvocationHandler {
    HttpSession session;
    T userService;

    public UserServiceProxy(HttpSession session, T userService) {
        this.session = session;
        this.userService = userService;
    }

    /**
     * 对浏览器回话对象的用户进行全面检查 即可通过
     * @return 通过返回null 失败返回json文本
     */
    protected abstract String checkpoint();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String checkpoint = checkpoint();
        if (checkpoint != null){
            return checkpoint;
        }
        return method.invoke(userService, args);
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), this);
    }
}
