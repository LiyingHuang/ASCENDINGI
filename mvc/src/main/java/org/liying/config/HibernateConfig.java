package org.liying.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.liying.util.HibernateUtil;

@Configuration
public class HibernateConfig {
    @Bean
    // costumized the new object then stroed in storage
    // SF sF = hibernateUtil.getSessionFactory();
    public SessionFactory getHibernateSessionFactory(){
        HibernateUtil hibernateUtil = new HibernateUtil();
        return hibernateUtil.getSessionFactory();
    }
}
