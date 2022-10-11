package factory;

import container.Container;
import container.Strategy;

public interface ContainerFactory {
    Container createContainer(Strategy strategy);
}
