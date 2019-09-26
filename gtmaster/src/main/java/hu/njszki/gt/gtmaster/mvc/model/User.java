package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "GtUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Column
    private String userName;
    @Column
    private String password;
    @OneToMany
    @JoinTable(name = "UserRoles",
            joinColumns = {@JoinColumn(name = "USER_ROLE")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_USER")})
    private List<Role> roles = new LinkedList<>();
}
