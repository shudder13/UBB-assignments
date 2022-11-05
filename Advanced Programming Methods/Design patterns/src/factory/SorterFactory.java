package factory;

import sorter.AbstractSorter;
import sorter.BubbleSorter;
import sorter.QuickSorter;
import sorter.Strategy;

public class SorterFactory {
    private static final SorterFactory instance = new SorterFactory();

    private SorterFactory() {}

    public static SorterFactory getInstance() {
        return instance;
    }

    public AbstractSorter createSorter(Strategy strategy) {
        if (strategy == Strategy.BUBBLE)
            return new BubbleSorter();
        else if (strategy == Strategy.QUICK)
            return new QuickSorter();
        return null;
    }
}
