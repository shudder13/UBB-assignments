package run;

import container.Strategy;

public class Main {
    public static void main(String[] args) {
        Strategy taskRunnerStrategy = Strategy.LIFO;
        TestRunner.run(taskRunnerStrategy);

        int[] array = new int[]{0, 7, 3, 9, 1, 11, 14, 23, 4};
        sorter.Strategy sortStrategy = sorter.Strategy.QUICK;
        TestRunner.sort(array, sortStrategy);
    }
}
