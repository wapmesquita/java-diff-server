package io.github.wapmesquita.diffmodel.model;

public abstract class BaseEntity<T> {

    public abstract T getId();

    public abstract void setId(T id);

}
