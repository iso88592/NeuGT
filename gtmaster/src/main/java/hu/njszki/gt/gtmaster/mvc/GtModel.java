package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.List;

public class GtModel {
    private static GtModel instance;

    private SessionFactory sessionFactory;

    private GtModel() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .configure()
                .buildSessionFactory();
    }

    private Session openSession() {
        return sessionFactory.openSession();
    }

    public static GtModel getInstance() {
        if (instance == null) {
            instance = new GtModel();
        }
        return instance;
    }

    public List<User> getUsers() {
        Session session = openSession();
        List<User> result = new LinkedList<>();
        session.close();
        return result;
    }
}
