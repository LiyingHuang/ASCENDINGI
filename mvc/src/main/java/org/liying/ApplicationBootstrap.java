package org.liying;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@ServletComponentScan(basePackages = {"org.liying.filter"})
@SpringBootApplication(scanBasePackages = {"org.liying"})
// 组件/component/controller web层/service 业务层/repository dao层/...扫描：告诉spring在这个包下面需要组建扫描，basePackage基础包，会扫描基础包及其子包，完成备案的创建和注入
public class ApplicationBootstrap extends SpringBootServletInitializer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static void main(String[] args){
        SpringApplication.run(org.liying.ApplicationBootstrap.class, args);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)  // 有singleton 单例 和prototype 每次new一个新的返回
    public Logger logger(InjectionPoint injectionPoint){
        logger.debug("Debug Information");
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
