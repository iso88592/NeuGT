package hu.njszki.gt.gtmaster.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {
    @RequestMapping(path = "/api/v1/")
    public String root() {
        return "";
    }
}
