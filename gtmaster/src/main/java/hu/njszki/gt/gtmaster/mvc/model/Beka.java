package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;

@Entity
public class Beka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @OneToOne
    private BekaTeam bekaTeam;
}
