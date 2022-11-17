package repository;

import exceptions.RepositoryException;
import model.entities.Entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    void add(E entity) throws RepositoryException, IOException;
    E getOne(ID id) throws RepositoryException, IOException;
    Collection<E> getAll() throws RepositoryException, IOException;
    void update(E entity);
    void remove(ID id) throws RepositoryException, IOException;
    void clear();
}
