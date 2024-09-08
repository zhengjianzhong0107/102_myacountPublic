package com.my.count;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.my.count.dao")
public class CountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountApplication.class, args);
    }

}
