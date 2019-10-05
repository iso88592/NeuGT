package hu.njszki.gt.gtmaster;

import hu.njszki.gt.gtmaster.mvc.GtModel;
import hu.njszki.gt.gtmaster.mvc.model.Role;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .antMatchers("/admin/*").access("hasRole('ADMIN')")
                .antMatchers("/").access("hasRole('USER')")
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static SpringSecurityConfig instance;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        instance = this;
        GtModel model = GtModel.getInstance();
        Session session = model.openSession();
        List<User> users = model.getUsers(session);
        logger.info("Adding users");
        for (User user : users) {

            auth
                    .inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser(user.getUserName())
                    .password(user.getPassword())
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