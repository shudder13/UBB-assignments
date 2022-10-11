package container;

import model.Task;
import utils.Constants;

public abstract class AbstractContainer implements Container {
    protected Task[] tasks;
    protected int size;

    public AbstractContainer() {
        this.tasks = new Task[Constants.INITIAL_CONTAINER_SIZE];
        this.size = 0;
    }

    @Override
    public void add(Task task) {
        if (tasks.length == size) {
            Task[] new_tasks = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, new_tasks, 0, tasks.length);
            tasks = new_tasks;
        }
        tasks[size] = task;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
