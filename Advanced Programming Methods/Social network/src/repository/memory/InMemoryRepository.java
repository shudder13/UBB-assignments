package repository.memory;

import exceptions.RepositoryException;
import model.entities.Entity;
import repository.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private final Map<ID, E> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public void add(E entity) throws RepositoryException, IOException {
        if (entity == null)
            throw new IllegalArgumentException("The entity must not be null.");
        if (entities.containsKey(entity.getId()) || entities.containsValue(entity))
            throw new RepositoryException("An entity with that identifier already exists.");
        entities.put(entity.getId(), entity);
    }

    @Override
    public E getOne(ID id) throws RepositoryException, IOException {
        if (id == null)
            throw new IllegalArgumentException("The identifier must not be null.");
        return entities.get(id);
    }

    @Override
    public Collection<E> getAll() throws RepositoryException, IOException {
        return entities.values();
    }

    @Override
    public void update(E entity) {

    }

    @Override
    public void remove(ID id) throws RepositoryException, IOException {
        if (id == null)
            throw new IllegalArgumentException("Identifier must not be null.");
        if (entities.get(id) == null)
            throw new RepositoryException("There is no such entity in the repository.");
        entities.remove(id);
    }

    @Override
    public void clear() {
        entities.clear();
    }
}
