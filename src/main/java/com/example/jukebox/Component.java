package com.example.jukebox;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Component {
    private @Id @GeneratedValue(strategy= GenerationType.IDENTITY) Long id;
    private String name;

    protected Component() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Component))
            return false;
        Component component = (Component) o;
        return Objects.equals(this.id, component.id) && Objects.equals(this.name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Component {" + "id=" + this.id + ", name='" + this.name + '\'' + '}';
    }
}
