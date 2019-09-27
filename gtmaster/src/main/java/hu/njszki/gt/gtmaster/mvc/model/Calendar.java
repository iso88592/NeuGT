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
}
