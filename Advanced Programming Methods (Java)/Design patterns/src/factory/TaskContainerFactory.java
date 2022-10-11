package factory;

import container.Container;
import container.QueueContainer;
import container.StackContainer;
import container.Strategy;

public class TaskContainerFactory implements ContainerFactory {
    private static final TaskContainerFactory instance = new TaskContainerFactory();

    private TaskContainerFactory() {} // TO-DO Singleton

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        if (strategy == Strategy.LIFO)
            return new StackContainer();
        else
            return new QueueContainer();
    }
}
