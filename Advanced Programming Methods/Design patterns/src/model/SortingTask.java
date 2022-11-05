package model;

import factory.SorterFactory;
import sorter.AbstractSorter;
import sorter.Strategy;

public class SortingTask extends Task {
    private final int[] array;
    private final AbstractSorter sorter;

    public SortingTask(String taskID, String description, int[] array, Strategy strategy) {
        super(taskID, description);
        this.array = array;
        this.sorter = SorterFactory.getInstance().createSorter(strategy);
    }

    @Override
    public void execute() {
        sorter.sort(array);
        for (int i : array)
            System.out.print(i + " ");
    }
}
