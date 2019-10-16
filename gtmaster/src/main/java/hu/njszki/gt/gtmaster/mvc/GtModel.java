package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.logging.Logger;

public class GtModel {
    private static GtModel instance;
    private static Logger logger = Logger.getLogger(GtModel.class.getName());

    private SessionFactory sessionFactory;

    PasswordEncoder passwordEncoder;

    private GtModel() {
        passwordEncoder = new BCryptPasswordEncoder();
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
            testUser.setPassword(passwordEncoder.encode("xyz"));
            testUser.getRoles().add(userRole);
            session.save(testUser);

            testUser = new User();
            testUser.setPassword(passwordEncoder.encode("neugt"));
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

    private static<T> List<T> getList(Session session, Class<T> c) {
        return session.createQuery("FROM " + c.getName(), c).list();
    }

    public List<User> getUsers(Session session) {
        return getList(session, User.class);
    }


    public Beka getBeka(int id, Session session) {
        return session.get(Beka.class, id);
    }

    private Role findRole(Session session, String roleName) {
        List<Role> roles = getList(session, Role.class);
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

    public List<Beka> getBekas(Session session) {
        return getList(session, Beka.class);
    }

    public List<BekaTeam> getTeams(Session session) {
        return getList(session, BekaTeam.class);
    }

    public List<Golya> getGolyas(Session session) {
        return getList(session, Golya.class);
    }

    public List<Event> getEvents(Session session) {
        return getList(session, Event.class);
    }

    public List<Calendar> getCalendars(Session session) {
        return getList(session, Calendar.class);
    }

    public static ImmutablePair events(Session session) {
        return new ImmutablePair<>("events", getInstance().getEvents(session));
    }
    public static ImmutablePair bekas(Session session) {
        return new ImmutablePair<>("bekas", getInstance().getBekas(session));
    }
    public static ImmutablePair users(Session session) {
        return new ImmutablePair<>("gtusers", getInstance().getUsers(session));
    }

    public static ImmutablePair calendars(Session session) {
        return new ImmutablePair<>("calendars", getInstance().getCalendars(session));
    }
    public static ImmutablePair teams(Session session) {
        return new ImmutablePair<>("teams", getInstance().getTeams(session));
    }
    public static ImmutablePair golyas(Session session) {
        return new ImmutablePair<>("golyas", getInstance().getGolyas(session));
    }
}
