package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class AdminController {
    private Logger logger = Logger.getLogger(AdminController.class.getName());

    ModelAndView createView(String viewName, String title, ImmutablePair[] items) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        modelAndView.addObject("title", title);
        for (ImmutablePair item : items) {
            modelAndView.addObject((String) item.left, item.right);
        }
        return modelAndView;
    }

    @RequestMapping(path = "/")
    public ModelAndView root() {
        return createView(
                "index",
                "Gólyatábor Webapp",
                new ImmutablePair[]{});
    }

    @RequestMapping(path = "/admin")
    public ModelAndView adminRoot() {
        return createView(
                "admin",
                "Gólyatábor admin",
                new ImmutablePair[]{});
    }

    // user management

    @RequestMapping(path = "/admin/users")
    public ModelAndView adminUsers() {
        Session session = GtModel.getInstance().openSession();
        ModelAndView modelAndView = createView(
                "useradmin",
                "Gólyatábor admin",
                new ImmutablePair[]{
                        GtModel.users(session)});
        session.close();
        return modelAndView;
    }

    // event management

    // calendar management

    @RequestMapping(path = "/admin/events")
    public ModelAndView adminEvents() {
        Session session = GtModel.getInstance().openSession();
        ModelAndView modelAndView = createView(
                "events",
                "Gólyatábor admin",
                new ImmutablePair[]{
                        GtModel.events(session),
                        GtModel.calendars(session),
                        GtModel.bekas(session)
                });
        session.close();
        return modelAndView;
    }

    // beka management

    @RequestMapping(path = "/admin/beka")
    public ModelAndView adminBeka() {
        Session session = GtModel.getInstance().openSession();
        ModelAndView modelAndView = createView(
                "bekaadmin",
                "Gólyatábor admin",
                new ImmutablePair[]{
                        GtModel.users(session),
                        createTeamList(session),
                        GtModel.bekas(session)
                });
        session.close();
        return modelAndView;
    }

    private ImmutablePair createTeamList(Session session) {
        List<BekaTeam> teams = GtModel.getInstance().getTeams(session);
        BekaTeam emptyTeam = new BekaTeam();
        emptyTeam.setId(-1);
        emptyTeam.setName("-- nincs --");
        teams.add(emptyTeam);
        return new ImmutablePair<>("teams", teams);
    }

    private ImmutablePair classes() {
        String[] classes = {"-- nincs --", "9.A", "9.B", "9.C", "9.nyek", "9.E", "9.X"};
        return new ImmutablePair<>("classes", classes);
    }

    private ImmutablePair houses() {
        String[] houses = {
                "-- nincs --",
                "2. faház",
                "3. faház",
                "4. faház",
                "5. faház",
                "6. faház",
                "7. faház",
                "8. faház",
                "9. faház",
                "10. faház",
                "11. faház",
                "12. faház",
                "13/1 kőház",
                "13/2 kőház",
                "13/3 kőház",
                "13/4 kőház",
                "14/1 kőház",
                "14/2 kőház",
                "14/3 kőház",
                "14/4 kőház"};
        return new ImmutablePair<>("houses", houses);
    }

    @RequestMapping(path = "/admin/golya")
    public ModelAndView adminGolya() {
        Session session = GtModel.getInstance().openSession();
        ModelAndView modelAndView = createView(
                "golyaadmin",
                "Gólyatábor admin",
                new ImmutablePair[]{
                        GtModel.users(session),
                        createTeamList(session),
                        GtModel.bekas(session),
                        GtModel.golyas(session),
                        classes(),
                        houses()
                });
        session.close();
        return modelAndView;
    }

    @RequestMapping(path = "/admin/team")
    public ModelAndView adminTeam() {
        Session session = GtModel.getInstance().openSession();
        ModelAndView modelAndView = createView(
                "teamadmin",
                "Gólyatábor admin",
                new ImmutablePair[]{
                        GtModel.teams(session)
                });
        session.close();
        return modelAndView;
    }

    @RequestMapping(path = "/admin/calendar/new")
    public RedirectView newCalendar(@RequestParam int year,
                                    @RequestParam int leader,
                                    @RequestParam int deputy) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setLeader(GtModel.getInstance().getBekas(session).stream().filter(x -> x.getId() == leader).findFirst().get());
        calendar.setDeputy(GtModel.getInstance().getBekas(session).stream().filter(x -> x.getId() == deputy).findFirst().get());
        session.save(calendar);
        tx.commit();
        session.close();

        return new RedirectView("/admin/events");
    }

    @RequestMapping(path = "/admin/calendar/update")
    public RedirectView updateCalendar(
            @RequestParam int id,
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int leader,
            @RequestParam(defaultValue = "0") int deputy) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Calendar calendar = GtModel.getInstance().getCalendars(session).stream().filter(x -> x.getId() == id).findFirst().get();
        if (year != 0) calendar.setYear(year);
        if (leader != 0)
            calendar.setLeader(GtModel.getInstance().getBekas(session).stream().filter(x -> x.getId() == leader).findFirst().get());
        if (deputy != 0)
            calendar.setDeputy(GtModel.getInstance().getBekas(session).stream().filter(x -> x.getId() == deputy).findFirst().get());
        session.save(calendar);
        tx.commit();
        session.close();

        return new RedirectView("/admin/events");
    }

    @RequestMapping(path = "/admin/users/new", method = RequestMethod.POST)
    public RedirectView newUser(@RequestParam String userName,
                                @RequestParam String password,
                                @RequestParam(defaultValue = "false") boolean isAdmin) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.getRoles().add(isAdmin ? GtModel.getInstance().adminRole(session) : GtModel.getInstance().userRole(session));
        session.save(user);
        tx.commit();
        session.close();
        return new RedirectView("/admin/users");
    }

    @RequestMapping(path = "/admin/team/new", method = RequestMethod.POST)
    public RedirectView newUser(@RequestParam String name) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        BekaTeam bekaTeam = new BekaTeam();
        bekaTeam.setName(name);
        session.save(bekaTeam);
        tx.commit();
        session.close();
        return new RedirectView("/admin/team");
    }

    @RequestMapping(path = "/admin/beka/new", method = RequestMethod.POST)
    public RedirectView newBeka(@RequestParam int userName,
                                @RequestParam int team,
                                @RequestParam String phone,
                                @RequestParam String fullName) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka beka = new Beka();
        if (team != -1) {
            beka.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == team).findFirst().get());
        }
        beka.setFullName(fullName);
        beka.setPhone(phone);
        beka.setUser(GtModel.getInstance().getUsers(session).stream().filter(x -> x.getId() == userName).findFirst().get());
        session.save(beka);
        tx.commit();
        session.close();
        return new RedirectView("/admin/beka");
    }

    @RequestMapping(path = "/admin/golya/new", method = RequestMethod.POST)
    public RedirectView newGolya(@RequestParam String name,
                                 @RequestParam int team,
                                 @RequestParam String classes,
                                 @RequestParam String house,
                                 @RequestParam String phone,
                                 @RequestParam String parentPhone) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Golya golya = new Golya();
        if (team != -1) {
            golya.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == team).findFirst().get());
        }
        golya.setName(name);
        golya.setClassLetter(classes);
        golya.setHouseNumber(house);
        golya.setPhone(phone);
        golya.setParentPhone(parentPhone);
        session.save(golya);
        tx.commit();
        session.close();
        return new RedirectView("/admin/golya");
    }

    @RequestMapping(path = "/admin/golya/update", method = RequestMethod.POST)
    public RedirectView updateGolya(@RequestParam int id,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "0") int team,
                                    @RequestParam(defaultValue = "") String classes,
                                    @RequestParam(defaultValue = "") String house,
                                    @RequestParam(defaultValue = "") String phone,
                                    @RequestParam(defaultValue = "") String parentPhone) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Golya golya = GtModel.getInstance().getGolyas(session).stream().filter(x -> x.getId() == id).findFirst().get();
        if (team != 0) {
            if (team != -1) {
                golya.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == team).findFirst().get());
            } else {
                golya.setBekaTeam(null);
            }
        }
        if (!name.equals("")) {
            golya.setName(name);
        }
        if (!classes.equals("")) {
            golya.setClassLetter(classes);
        }
        if (!house.equals("")) {
            golya.setHouseNumber(house);
        }
        if (!phone.equals("")) {
            logger.info(phone);
            golya.setPhone(phone);
        }
        if (!parentPhone.equals("")) {
            logger.info(parentPhone);
            golya.setParentPhone(parentPhone);
        }
        session.save(golya);
        tx.commit();
        session.close();
        return new RedirectView("/admin/golya");
    }

    @RequestMapping(path = "/admin/beka/rm", method = RequestMethod.POST)
    public RedirectView rmBeka(@RequestParam int id) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka toErase = GtModel.getInstance().getBeka(id, session);
        session.delete(toErase);
        tx.commit();
        session.close();
        return new RedirectView("/admin/beka");
    }


    @RequestMapping(path = "/admin/team/rm", method = RequestMethod.POST)
    public RedirectView rmTeam(@RequestParam int id) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        BekaTeam toErase = GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == id).findFirst().get();
        session.delete(toErase);
        tx.commit();
        session.close();
        return new RedirectView("/admin/team");
    }

    @RequestMapping(path = "/admin/calendar/rm", method = RequestMethod.POST)
    public RedirectView rmCalendar(@RequestParam int id) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Calendar toErase = GtModel.getInstance().getCalendars(session).stream().filter(x -> x.getId() == id).findFirst().get();
        session.delete(toErase);
        tx.commit();
        session.close();
        return new RedirectView("/admin/events");
    }

    @RequestMapping(path = "/admin/golya/rm", method = RequestMethod.POST)
    public RedirectView rmGolya(@RequestParam int id) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Golya toErase = GtModel.getInstance().getGolyas(session).stream().filter(x -> x.getId() == id).findFirst().get();
        session.delete(toErase);
        tx.commit();
        session.close();
        return new RedirectView("/admin/golya");
    }

    @RequestMapping(path = "/admin/users/makeadmin", method = RequestMethod.POST)
    public RedirectView makeAdmin(@RequestParam int id,
                                  @RequestParam boolean admin) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        User user = GtModel.getInstance().getUsers(session).stream().filter(x->x.getId() == id).findFirst().get();
        user.setAdmin(admin, session);
        if (user.getUserName().equals("neugt") && !user.isAdmin()) {
            user.setAdmin(true, session);
        }
        session.save(user);
        tx.commit();
        session.close();
        return new RedirectView("/admin/users");
    }


    @RequestMapping(path = "/admin/beka/update", method = RequestMethod.POST)
    public RedirectView updateBeka(@RequestParam int id,
                                   @RequestParam(defaultValue = "0") int userId,
                                   @RequestParam(defaultValue = "0") int teamId,
                                   @RequestParam(defaultValue = "") String phone,
                                   @RequestParam(defaultValue = "") String fullName) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka beka = GtModel.getInstance().getBeka(id, session);
        if (userId != 0)
            beka.setUser(GtModel.getInstance().getUsers(session).stream().filter(x -> x.getId() == userId).findFirst().get());
        if (teamId != 0) {
            if (teamId == -1) {
                beka.setBekaTeam(null);
            } else {
                beka.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == teamId).findFirst().get());
            }
        }
        if (!fullName.equals("")) {
            beka.setFullName(fullName);
        }
        if (!phone.equals("")) {
            beka.setPhone(phone);
        }
        session.save(beka);
        tx.commit();
        session.close();
        return new RedirectView("/admin/beka");
    }
}
