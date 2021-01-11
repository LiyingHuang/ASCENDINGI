package org.liying.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.liying.util.HibernateUtil;

@Configuration  //用于指定当前类是一个Spring配置类，当创建容器时会从该类上加载注解
public class HibernateConfig {
    @Bean // 使用在方法上，标注该方法的返回值存储到spring容器中
    // customized the new object then stored in storage
    // SF sF = hibernateUtil.getSessionFactory();
    public SessionFactory getHibernateSessionFactory(){
        HibernateUtil hibernateUtil = new HibernateUtil();
        return hibernateUtil.getSessionFactory();
    }
}

// @PropertySource 用于加载properties文件中的配置
// @Import 用于导入其他配置类
