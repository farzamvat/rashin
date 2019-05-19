package com.emersun.rashin;

import com.emersun.rashin.utils.MongodbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {
    @Autowired
    private MongodbUtil mongodbUtil;
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @PostConstruct
    public void init() {
        mongodbUtil.insertAdminRole();
        mongodbUtil.insertAdminUserAsAgent();
    }
}
