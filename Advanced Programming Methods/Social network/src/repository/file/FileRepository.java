package repository.file;

import model.entities.Entity;
import repository.memory.InMemoryRepository;

public abstract class FileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
}
