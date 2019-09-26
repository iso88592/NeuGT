package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Golya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Date birthDate;

    @Column
    private String phone;

    @Column
    private String classLetter;

    @Column
    private String houseNumber;

    @Column
    private String parentPhone;

    @ManyToOne
    private BekaTeam bekaTeam;


}
