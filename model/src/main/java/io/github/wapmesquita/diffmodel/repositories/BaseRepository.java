package io.github.wapmesquita.diffmodel.repositories;

import java.util.Optional;

import io.github.wapmesquita.diffmodel.model.BaseEntity;

public interface BaseRepository<T extends BaseEntity> {

    public T save(T t);

    public Optional<T> find(Object id);

}
