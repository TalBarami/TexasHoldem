package Server.data.Hybernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by achiad on 27/05/2017.
 */
public class HibernateUtil {
    private static HibernateUtil instance = null;

    private SessionFactory sessionFactory;

    private HibernateUtil(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static HibernateUtil getInstance(){
        if(instance == null){
            instance  = new HibernateUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
