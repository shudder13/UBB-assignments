package com.socialnetwork.repository;

import com.socialnetwork.domain.entities.Entity;
import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    void add(E entity);
    E getOne(ID id);
    Collection<E> getAll();
    void update(E entity);
    void remove(ID id);
    void clear();
}
