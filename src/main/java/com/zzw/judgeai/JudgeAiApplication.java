package com.zzw.judgeai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zzw.judgeai.mapper")
public class JudgeAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeAiApplication.class, args);
    }

}
