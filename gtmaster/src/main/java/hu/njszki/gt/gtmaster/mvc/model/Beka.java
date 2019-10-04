package hu.njszki.gt.gtmaster.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Beka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @OneToOne
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return getUser().getUserName();
    }

    public int getUserId() {
        return getUser().getId();
    }
    public int getTeamId() {
        if (getBekaTeam() == null) return -1;
        return getBekaTeam().getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BekaTeam getBekaTeam() {
        return bekaTeam;
    }

    public void setBekaTeam(BekaTeam bekaTeam) {
        this.bekaTeam = bekaTeam;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public List<Event> getResponsibleFor() {
        return responsibleFor;
    }

    public void setResponsibleFor(List<Event> responsibleFor) {
        this.responsibleFor = responsibleFor;
    }

    @ManyToOne
    private BekaTeam bekaTeam;

    @Column
    private byte[] photo;

    @Column
    private String greeting;

    @JsonIgnore
    @OneToMany(mappedBy = "responsible")
    private List<Event> responsibleFor = new LinkedList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column
    private String fullName;
}
