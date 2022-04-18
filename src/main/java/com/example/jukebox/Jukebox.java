package com.example.jukebox;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Jukebox {

//    private @Id @GeneratedValue Long id;
    private @Id String id;
    private String model;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Component> components;

    protected Jukebox() {}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Component> getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Jukebox))
            return false;
        Jukebox jukebox = (Jukebox) o;
        return Objects.equals(this.id, jukebox.id) && Objects.equals(this.model, jukebox.model)
                && Objects.equals(this.components, jukebox.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.model, this.components);
    }

    @Override
    public String toString() {
        return "Jukebox {" + "id=" + this.id + ", model='" + this.model + '\'' + ", components="
                + this.components.toString() + '}';
    }
}
