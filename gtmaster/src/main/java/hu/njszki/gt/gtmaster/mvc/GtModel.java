package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GtModel {
    private static GtModel instance;

    private SessionFactory sessionFactory;

    private GtModel() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Beka.class)
                .addAnnotatedClass(BekaTeam.class)
                .addAnnotatedClass(Golya.class)
                .addAnnotatedClass(Calendar.class)
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();
        Session session = openSession();
        int resultCount = session.createQuery("FROM Role WHERE name='ADMIN'", Role.class).list().size();
        if (resultCount == 0) {
            Transaction tx = session.beginTransaction();
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role userRole = new Role();
            userRole.setName("USER");
            session.save(adminRole);
            session.save(userRole);
            User testUser = new User();
            testUser.setUserName("alma");
            testUser.setPassword("xyz");
            testUser.getRoles().add(userRole);
            session.save(testUser);

            testUser = new User();
            testUser.setPassword("neugt");
            testUser.setUserName("neugt");
            testUser.getRoles().add(adminRole);
            session.save(testUser);

            tx.commit();
        }
        session.close();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public static GtModel getInstance() {
        if (instance == null) {
            instance = new GtModel();
        }
        return instance;
    }

    public List<User> getUsers(Session session) {
        List<User> result = session.createQuery("FROM User", User.class).list();
        return result;
    }


    public Beka getBeka(int id, Session session) {
        return session.get(Beka.class, id);
    }

    private Role findRole(Session session, String roleName) {
        List<Role> roles = session.createQuery("FROM Role", Role.class).list();
        for (Role role : roles) {
            if (role.getName().equals(roleName)) return role;
        }
        return null;
    }

    public Role adminRole(Session session) {
        return findRole(session, "ADMIN");
    }
    public Role userRole(Session session) {
        return findRole(session, "USER");
    }
}
