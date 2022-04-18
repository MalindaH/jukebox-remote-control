package com.example.jukebox;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Setting {

    private @Id UUID id;
    @ElementCollection
    private List<String> requires;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    public List<String> getRequires() {
        return requires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Setting))
            return false;
        Setting setting = (Setting) o;
        return Objects.equals(this.id, setting.id) && Objects.equals(this.requires, setting.requires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.requires);
    }

    @Override
    public String toString() {
        return "Setting {" + "id=" + this.id + ", requires=" + Arrays.toString(requires.toArray()) + '}';
    }
}
