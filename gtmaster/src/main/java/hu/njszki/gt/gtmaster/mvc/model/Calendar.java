package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "calendar")
    private List<Event> events = new LinkedList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column
    private int year;

    @ManyToOne
    private Beka leader;

    public Beka getLeader() {
        return leader;
    }

    public void setLeader(Beka leader) {
        this.leader = leader;
    }

    public Beka getDeputy() {
        return deputy;
    }

    public void setDeputy(Beka deputy) {
        this.deputy = deputy;
    }

    @ManyToOne
    private Beka deputy;

    public int getLeaderId() {
        return getLeader().getId();
    }

    public int getDeputyId() {
        return getDeputy().getId();
    }
}
