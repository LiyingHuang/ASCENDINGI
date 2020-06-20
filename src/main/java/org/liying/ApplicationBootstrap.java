package org.liying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.liying"})
public class ApplicationBootstrap {
    public static void main(String[] args){
        SpringApplication.run(org.liying.ApplicationBootstrap.class, args);
    }
}
 