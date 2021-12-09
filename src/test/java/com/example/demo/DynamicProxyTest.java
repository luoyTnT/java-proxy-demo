package com.example.demo;

import com.example.demo.common.proxy.dynamic.AdminServiceDynamicProxy;
import com.example.demo.common.proxy.dynamic.AdminServiceInvocation;
import com.example.demo.service.AdminService;
import com.example.demo.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * 动态代理测试
 */
@Slf4j
public class DynamicProxyTest {

    @Test
    public void dynamicProxyTest() {
        AdminServiceImpl adminService = new AdminServiceImpl();
        log.info("代理目标对象: {}", adminService.getClass());

        AdminServiceInvocation invocation = new AdminServiceInvocation(adminService);

        this.methodOneTest(adminService, invocation);

        this.methodTwoTest(adminService, invocation);

        this.methodThreeTest(adminService);
    }

    private void methodOneTest(AdminServiceImpl adminService, AdminServiceInvocation invocation) {
        log.info("\n\n=============方法1=============");

        Object dynamicProxy = new AdminServiceDynamicProxy(adminService, invocation).getPersonProxy();

        this.adminServiceCall(dynamicProxy);
    }

    private void methodTwoTest(AdminServiceImpl adminService, AdminServiceInvocation invocation) {
        log.info("\n\n=============方法2=============");

        ClassLoader classLoader = adminService.getClass().getClassLoader();
        Class<?>[] interfaces = adminService.getClass().getInterfaces();
        Object dynamicProxy = Proxy.newProxyInstance(classLoader, interfaces, invocation);

        this.adminServiceCall(dynamicProxy);
    }

    private void methodThreeTest(AdminServiceImpl adminService) {
        log.info("\n\n=============方法3=============");

        ClassLoader classLoader = adminService.getClass().getClassLoader();
        Class<?>[] interfaces = adminService.getClass().getInterfaces();
        Object dynamicProxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            log.info("判断用户是否有权限进行操作");
            Object obj = method.invoke(adminService, args);
            log.info("记录用户操作日志");
            return obj;
        });

        this.adminServiceCall(dynamicProxy);
    }

    private void adminServiceCall(Object dynamicProxy) {
        AdminService proxy = (AdminService) dynamicProxy;

        log.info("代理对象: {}", proxy.getClass());
        Object obj = proxy.find();
        log.info("find 返回对象: {}", obj.getClass());
        log.info("--------------------------------");
        proxy.update();
    }

}
