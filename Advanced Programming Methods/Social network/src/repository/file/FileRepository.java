package repository.file;

import exceptions.RepositoryException;
import model.entities.Entity;
import repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public abstract class FileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;

    public FileRepository(String fileName, boolean loadDataInConstructor) throws IOException, RepositoryException {
        this.fileName = fileName;
        if (loadDataInConstructor)
            loadData();
    }

    public abstract E extractEntity(List<String> attributes) throws RepositoryException, IOException;

    public abstract String createEntityAsString(E entity);

    protected void loadData() throws IOException, RepositoryException {
        super.clear();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] parts = data.split(";");
            E entity = extractEntity(Arrays.asList(parts));
            super.add(entity);
        }
    }

    private void writeToFile(E entity) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
        bufferedWriter.write(createEntityAsString(entity));
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    private void writeAllToFile() throws IOException, RepositoryException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        Collection<E> entities = super.getAll();
        for (E entity : entities) {
            bufferedWriter.write(createEntityAsString(entity));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    @Override
    public void add(E entity) throws RepositoryException, IOException {
        loadData();
        super.add(entity);
        writeToFile(entity);
    }

    @Override
    public void remove(ID id) throws RepositoryException, IOException {
        loadData();
        super.remove(id);
        writeAllToFile();
    }

    @Override
    public E getOne(ID id) throws RepositoryException, IOException {
        loadData();
        return super.getOne(id);
    }

    @Override
    public Collection<E> getAll() throws RepositoryException, IOException {
        loadData();
        return super.getAll();
    }
}
