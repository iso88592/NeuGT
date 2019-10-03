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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Golya> getGolyas() {
        return golyas;
    }

    public void setGolyas(List<Golya> golyas) {
        this.golyas = golyas;
    }

    public List<Beka> getBekas() {
        return bekas;
    }

    public void setBekas(List<Beka> bekas) {
        this.bekas = bekas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
