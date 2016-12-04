package io.github.wapmesquita.diffmodel.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.github.wapmesquita.diffmodel.model.BaseEntity;

public class StaticRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {

    protected static final Map<Object, Object> db = new HashMap<>();

    @Override
    public T save(T t) {
        db.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<T> find(Object id) {
        Object t = db.get(id);
        if (t == null) {
            return Optional.empty();
        }

        return Optional.of((T) t);
    }
}
