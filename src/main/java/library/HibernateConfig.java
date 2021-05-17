package library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static final Configuration configuration = new Configuration().configure();

    private static SessionFactory factory;

    public static Session openSession() {
        return factory.openSession();
    }

    public static void buildSessionFactory() {
        factory = configuration.buildSessionFactory();
    }

    public static void closeSessionFactory() {
        factory.close();
    }
}
