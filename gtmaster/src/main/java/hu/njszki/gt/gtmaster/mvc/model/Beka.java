package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Beka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @ManyToOne
    private BekaTeam bekaTeam;

    @Column
    private byte[] photo;

    @Column
    private String greeting;

    @OneToMany(mappedBy = "responsible")
    private List<Event> responsibleFor = new LinkedList<>();
}
