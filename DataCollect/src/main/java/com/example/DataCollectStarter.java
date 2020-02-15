package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:bean.xml"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DataCollectStarter {
    public static void main(String[] args) {
        SpringApplication.run(DataCollectStarter.class,args);
    }
}
