package domain;

import java.io.Serializable;

public class Entity<T> implements Serializable {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}

