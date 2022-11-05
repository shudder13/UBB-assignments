package container;

import model.Task;

public class QueueContainer extends AbstractContainer {

    public QueueContainer() {
        super();
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            Task removedTask = tasks[0];
            for (int i = 0; i < tasks.length - 1; i++)
                tasks[i] = tasks[i + 1];
            size--;
            return removedTask;
        }
        return null;
    }
}
