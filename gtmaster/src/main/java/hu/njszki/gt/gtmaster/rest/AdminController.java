package hu.njszki.gt.gtmaster.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

}
