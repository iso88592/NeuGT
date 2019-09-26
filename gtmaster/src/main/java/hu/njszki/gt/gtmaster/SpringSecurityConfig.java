package hu.njszki.gt.gtmaster;

import hu.njszki.gt.gtmaster.mvc.GtModel;
import hu.njszki.gt.gtmaster.mvc.model.Role;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(SpringSecurityConfig.class.getName());

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // TODO: create a Password Encoder
        GtModel model = GtModel.getInstance();
        Session session = model.openSession();
        List<User> users = model.getUsers(session);
        logger.info("Adding users");
        for (User user : users) {

            auth
                    .inMemoryAuthentication()
                    .withUser(user.getUserName())
                    .password("{noop}" + user.getPassword())
                    .roles(user
                            .getRoles()
                            .stream()
                            .map(Role::getName)
                            .toArray(String[]::new));
            logger.info("Adding user " + user.getUserName() + " with roles " + user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")));
        }
        session.close();
        logger.info("All user added");
    }

}