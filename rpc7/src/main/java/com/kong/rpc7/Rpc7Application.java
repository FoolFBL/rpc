package com.kong.rpc7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

//@EnableAspectJAutoProxy
@SpringBootApplication
public class Rpc7Application {
    public static void main(String[] args) {
        SpringApplication.run(Rpc7Application.class, args);
    }
}
