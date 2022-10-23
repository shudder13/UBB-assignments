package repository;

import exceptions.RepositoryException;
import model.entities.Entity;

import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    void add(E entity) throws RepositoryException;
    E getOne(ID id);
    Collection<E> getAll();
    void update(E entity);
    void remove(ID id) throws RepositoryException;
    void clear();
}
