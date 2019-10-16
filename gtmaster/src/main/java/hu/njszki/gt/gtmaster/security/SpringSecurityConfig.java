package hu.njszki.gt.gtmaster.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(SpringSecurityConfig.class.getName());

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

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

    public static SpringSecurityConfig instance;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        instance = this;
        auth.userDetailsService(userDetailsService);
    }

}