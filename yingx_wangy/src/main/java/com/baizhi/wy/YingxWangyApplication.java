package com.baizhi.wy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.baizhi.wy.dao")
@SpringBootApplication
public class YingxWangyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxWangyApplication.class, args);
    }

}
