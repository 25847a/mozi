package cn.mozistar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("cn.mozistar.mapper") //扫描所有mapper,就不用每个mapper都单独加上@mapper注解
@EnableTransactionManagement
@SpringBootApplication
public class Application{
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}