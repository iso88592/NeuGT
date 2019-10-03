package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.Beka;
import hu.njszki.gt.gtmaster.mvc.model.BekaTeam;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class AdminController {
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
        List<BekaTeam> teams = GtModel.getInstance().getTeams(session);
        BekaTeam emptyTeam = new BekaTeam();
        emptyTeam.setId(-1);
        emptyTeam.setName("-- nincs --");
        teams.add(emptyTeam);
        modelAndView.addObject("teams", teams);
        modelAndView.addObject("bekas", GtModel.getInstance().getBekas(session));
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
                                   @RequestParam int userId) {
        Session session = GtModel.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Beka beka = GtModel.getInstance().getBeka(id, session);
        beka.setUser(GtModel.getInstance().getUsers(session).stream().filter(x->x.getId() == userId).findFirst().get());
        session.save(beka);
        tx.commit();
        session.close();
        return new RedirectView("/admin/beka");
    }
}
