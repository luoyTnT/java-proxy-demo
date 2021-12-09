package com.example.demo;

import com.example.demo.common.proxy.cglib.AdminServiceCglibProxy;
import com.example.demo.service.AdminCglibService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CglibProxyTest {

    @Test
    public void cglibProxyTest() {
        AdminCglibService adminCglibService = new AdminCglibService();
        AdminServiceCglibProxy cglibProxy = new AdminServiceCglibProxy(adminCglibService);
        AdminCglibService proxy = (AdminCglibService) cglibProxy.getProxyInstance();

        this.adminServiceCall(proxy);
    }

    private void adminServiceCall(AdminCglibService proxy) {
        log.info("代理对象: {}", proxy.getClass());
        Object obj = proxy.find();
        log.info("find 返回对象: {}", obj.getClass());
        log.info("--------------------------------");
        proxy.update();
    }

}
