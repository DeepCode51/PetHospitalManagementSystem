package com.phms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 开启注解事务管理
@EnableTransactionManagement
@MapperScan("com.phms.mapper")
public class PhmsApp {
    public static void main(String[] args) {
        SpringApplication.run(PhmsApp.class, args);
    }

}
