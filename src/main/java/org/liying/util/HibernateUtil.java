package util;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
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
    private Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
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

                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, dbDriver);
                settings.put(Environment.DIALECT, dbDialect);
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPassword);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "validate");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);

                EntityScanner.scanPackages(modelPackages).addTo(configuration);

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
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
