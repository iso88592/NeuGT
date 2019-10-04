package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.Beka;
import hu.njszki.gt.gtmaster.mvc.model.BekaTeam;
import hu.njszki.gt.gtmaster.mvc.model.Golya;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class AdminController {
    private Logger logger = Logger.getLogger(AdminController.class.getName());

    @RequestMapping(path = "/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("welcome", "Welcome!");
        modelAndView.addObject("title", "Gólyatábor WebApp");
        return modelAndView;
    }

    @RequestMapping(path = "/admin")
    public ModelAndView adminRoot() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("title", "Gólyatábor admin");
        return modelAndView;
    }

    @RequestMapping(path = "/admin/users")
    public ModelAndView adminUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("useradmin");
        modelAndView.addObject("title", "Gólyatábor admin");
        Session session = GtModel.getInstance().openSession();
        modelAndView.addObject("gtusers", GtModel.getInstance().getUsers(session));
        session.close();
        return modelAndView;
    }

    @RequestMapping(path = "/admin/beka")
    public ModelAndView adminBeka() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bekaadmin");
        modelAndView.addObject("title", "Gólyatábor admin");
        Session session = GtModel.getInstance().openSession();
        modelAndView.addObject("gtusers", GtModel.getInstance().getUsers(session));
        modelAndView.addObject("teams", createTeamList(session));
        modelAndView.addObject("bekas", GtModel.getInstance().getBekas(session));
        session.close();
        return modelAndView;
    }

    private List<BekaTeam> createTeamList(Session session) {
        List<BekaTeam> teams = GtModel.getInstance().getTeams(session);
        BekaTeam emptyTeam = new BekaTeam();
        emptyTeam.setId(-1);
        emptyTeam.setName("-- nincs --");
        teams.add(emptyTeam);
        return teams;
    }

    @RequestMapping(path = "/admin/golya")
    public ModelAndView adminGolya() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("golyaadmin");
        modelAndView.addObject("title", "Gólyatábor admin");
        Session session = GtModel.getInstance().openSession();
        modelAndView.addObject("golyas", GtModel.getInstance().getGolyas(session));
        modelAndView.addObject("teams", createTeamList(session));
        String[] classes = {"-- nincs --", "9.A", "9.B", "9.C", "9.nyek", "9.E", "9.X"};
        String[] houses = {"-- nincs --", "1. faház", "2. faház", "3. faház", "4. faház", "5. faház", "6. faház"};
        modelAndView.addObject("classes", classes);
        modelAndView.addObject("houses", houses);

        session.close();
        return modelAndView;
    }

    @RequestMapping(path = "/admin/team")
    public ModelAndView adminTeam() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("teamadmin");
        modelAndView.addObject("title", "Gólyatábor admin");
        Session session = GtModel.getInstance().openSession();
        modelAndView.addObject("teams", GtModel.getInstance().getTeams(session));
        session.close();
        return modelAndView;
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
                                @RequestParam int team) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka beka = new Beka();
        if (team != -1) {
            beka.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == team).findFirst().get());
        }
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
        Golya golya = GtModel.getInstance().getGolyas(session).stream().filter(x->x.getId() == id).findFirst().get();
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

    @RequestMapping(path = "/admin/beka/update", method = RequestMethod.POST)
    public RedirectView updateBeka(@RequestParam int id,
                                   @RequestParam(defaultValue = "0") int userId,
                                   @RequestParam(defaultValue = "0") int teamId) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka beka = GtModel.getInstance().getBeka(id, session);
        if (userId != 0) beka.setUser(GtModel.getInstance().getUsers(session).stream().filter(x->x.getId() == userId).findFirst().get());
        if (teamId != 0) {
            if (teamId == -1) {
                beka.setBekaTeam(null);
            } else {
                beka.setBekaTeam(GtModel.getInstance().getTeams(session).stream().filter(x -> x.getId() == teamId).findFirst().get());
            }
        }
        session.save(beka);
        tx.commit();
        session.close();
        return new RedirectView("/admin/beka");
    }
}
