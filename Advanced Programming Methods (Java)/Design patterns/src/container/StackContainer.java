package container;

import model.Task;

public class StackContainer extends AbstractContainer {

    public StackContainer() {
        super();
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            size--;
            return tasks[size];
        }
        return null;
    }
}
