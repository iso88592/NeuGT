package hu.njszki.gt.gtmaster.mvc.model;

import javax.persistence.*;

@Entity
@Table(name = "GtRole")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    private String name;
}
