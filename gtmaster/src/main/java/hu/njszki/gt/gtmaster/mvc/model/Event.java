package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Time start;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public List<BekaTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<BekaTeam> teams) {
        this.teams = teams;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Beka getResponsible() {
        return responsible;
    }

    public void setResponsible(Beka responsible) {
        this.responsible = responsible;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    private Time end;

    @ManyToMany
    private List<BekaTeam> teams = new LinkedList<>();

    @Column
    private String text;

    @Column
    private String name;

    @ManyToOne
    private Beka responsible;

    @ManyToOne
    private Calendar calendar;

    @Column
    private String location;
}
