package hu.njszki.gt.gtmaster.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Golya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @JsonIgnore
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

    @JsonIgnore
    @ManyToOne
    private BekaTeam bekaTeam;


}
