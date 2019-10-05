package hu.njszki.gt.gtmaster.security;

import hu.njszki.gt.gtmaster.mvc.GtModel;
import hu.njszki.gt.gtmaster.mvc.model.Role;
import hu.njszki.gt.gtmaster.mvc.model.User;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        Session session = GtModel.getInstance().openSession();
        User user = findBySso(userName, session);
        if (user == null) {
            logger.log(Level.FINE, "User for " + userName + " not found");
            throw new UsernameNotFoundException("Username not found");
        }
        List<GrantedAuthority> authorities = getGrantedAuthorities(user);

        session.close();

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

    private User findBySso(String userName, Session session) {
        User result = GtModel.getInstance().getUsers(session).stream().filter(x -> x.getUserName().equals(userName)).findFirst().get();
        return result;
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }
}
