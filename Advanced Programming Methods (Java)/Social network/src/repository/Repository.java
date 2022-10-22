package repository;

import exceptions.RepositoryException;
import model.Entity;

import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    void save(E entity) throws RepositoryException;
    E getOne(ID id);
    Collection<E> getAll();
    void update(E entity);
    void delete(ID id);
    void clear();
}
