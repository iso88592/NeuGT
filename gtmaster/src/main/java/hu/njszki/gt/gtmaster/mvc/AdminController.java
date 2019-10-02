package hu.njszki.gt.gtmaster.mvc;

import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
}
