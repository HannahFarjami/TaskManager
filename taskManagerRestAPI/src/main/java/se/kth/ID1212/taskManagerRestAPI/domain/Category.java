package se.kth.ID1212.taskManagerRestAPI.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Category implements Serializable {

    @Id
    private String name;

    protected Category() {}


    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
