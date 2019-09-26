package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String role;
}
