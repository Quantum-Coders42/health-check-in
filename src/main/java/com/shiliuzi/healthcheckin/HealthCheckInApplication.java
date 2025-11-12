package com.shiliuzi.healthcheckin;

import com.tangzc.autotable.springboot.EnableAutoTable;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoTable
public class HealthCheckInApplication {
    static {
        try {
            Dotenv dotenv = Dotenv.load();
            // 将 .env 中的变量设置为系统环境变量
            dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        } catch (Exception e) {
            System.out.println("加载 .env 文件失败，已跳过");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthCheckInApplication.class, args);
    }

}
