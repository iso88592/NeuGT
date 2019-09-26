package hu.njszki.gt.gtmaster;

import hu.njszki.gt.gtmaster.mvc.GtModel;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

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
        // TODO: load users from DB
        GtModel model = GtModel.getInstance();
        List<User> users = model.getUsers();
        for (User user : users) {
            auth.inMemoryAuthentication().withUser(user.getUserName()).password("{noop}" + user.getPassword()).roles(user.getRole());
        }
        auth.inMemoryAuthentication().withUser("neugt").password("{noop}neugt").roles("USER", "ADMIN");
    }

}