package com.example.demo;

import com.example.demo.common.proxy.statics.AdminServiceProxy;
import com.example.demo.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 静态代理测试
 */
@Slf4j
public class ProxyStaticTest {

    @Test
    public void proxyStaticTest() {
        AdminServiceImpl adminService = new AdminServiceImpl();
        AdminServiceProxy proxy = new AdminServiceProxy(adminService);
        proxy.update();
        log.info("==========================");
        proxy.find();
    }

}
