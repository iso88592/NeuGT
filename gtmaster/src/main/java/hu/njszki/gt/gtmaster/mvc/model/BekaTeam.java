package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class BekaTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "bekaTeam")
    private List<Golya> golyas = new LinkedList<>();

    @OneToMany(mappedBy = "bekaTeam")
    private List<Beka> bekas = new LinkedList<>();

}
