package org.liying.util;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HibernateUtil {
    // HibernateConfig(make sure singleton) -> remove static
    // static: in order to make singleton class
    private SessionFactory sessionFactory;
    private Logger logger = LoggerFactory.getLogger(getClass());
    /*
    Define JVM options
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5431/dealer2
    -Ddatabase.user=admin
    -Ddatabase.password=password
     */
    public SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try {
                String[] modelPackages = {"org.liying.model"};

//                String dbDriver = "org.postgresql.Driver";
//                String dbDialect = "org.hibernate.dialect.PostgreSQL9Dialect";
//                String dbUrl = "jdbc:postgresql://localhost:5431/dealer2";
//                String dbUser = "admin";
//                String dbPassword = "password";

                String dbDriver = System.getProperty("database.driver");
                String dbDialect = System.getProperty("database.dialect");
                String dbUrl = System.getProperty("database.url");
                String dbUser = System.getProperty("database.user");
                String dbPassword = System.getProperty("database.password");


                // 解析映射的信息
                Configuration configuration = new Configuration();

                // 以下的这些put相当于 调用configuration的configiure()方法来从配置文件里解析信息
                Properties settings = new Properties();  // map集合，是一个容器
                // database connection配置
                settings.put(Environment.DRIVER, dbDriver);  //注册驱动，为了创建collection
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPassword);
                // 选项配置
                settings.put(Environment.DIALECT, dbDialect);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "validate");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                // 映射配置文件路径，scan这个modelPackages = {"org.liying.model"}，放进配置文件中
                // 主要是解析了database connection配置 和 选项配置
                EntityScanner.scanPackages(modelPackages).addTo(configuration);
                // StandardServiceRegistryBuilder对象主要是解析映射数据
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                //
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();


                // 创建sessionfactory对象，解析实体类和annotation的映射信息，生成基本的sql语句
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
//    public static void main(String[] args){
//        SessionFactory sf = HibernateUtil.getSessionFactory();
//        logger.info("Success generate sf" + sf.hashCode());
//        Session s1 = sf.openSession();
//        Session s2 = sf.openSession();
//        s1.close();
//        s2.close();
//    }
}
