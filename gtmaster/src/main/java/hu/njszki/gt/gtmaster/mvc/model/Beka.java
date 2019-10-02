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

    @ManyToOne
    private BekaTeam bekaTeam;

    @Column
    private byte[] photo;

    @Column
    private String greeting;

    @JsonIgnore
    @OneToMany(mappedBy = "responsible")
    private List<Event> responsibleFor = new LinkedList<>();
}
