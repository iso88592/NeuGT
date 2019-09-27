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
