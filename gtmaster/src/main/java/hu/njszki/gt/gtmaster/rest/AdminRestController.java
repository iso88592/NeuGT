package hu.njszki.gt.gtmaster.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.njszki.gt.gtmaster.mvc.GtModel;
import hu.njszki.gt.gtmaster.mvc.model.Beka;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class AdminRestController {
    ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = Logger.getLogger(AdminRestController.class.getName());
    @RequestMapping(path = "/api/v1/")
    public String root() {
        return "{\"logout\" = \"/api/v1/logout\"}";
    }

    @RequestMapping(value="/api/v1/logout")
    public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @RequestMapping(path = "/api/v1/beka/{id}")
    public String beka(@PathVariable int id) throws JsonProcessingException {
        Session session = GtModel.getInstance().openSession();
        Beka beka = GtModel.getInstance().getBeka(id, session);
        String result = mapper.writeValueAsString(beka);
        session.close();
        return result;
    }
}
