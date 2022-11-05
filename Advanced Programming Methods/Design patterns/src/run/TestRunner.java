package run;

import container.Strategy;
import model.MessageTask;
import model.SortingTask;
import runner.DelayTaskRunner;
import runner.PrinterTaskRunner;
import runner.StrategyTaskRunner;

import java.time.LocalDateTime;

public class TestRunner {
    private static MessageTask[] createMessageTasks() {
        MessageTask taskLaboratory = new MessageTask(
                "1", "Seminar", "tema laborator", "Florentin", "Razvan", LocalDateTime.now());
        MessageTask taskHomework = new MessageTask(
                "2", "Laborator", "Solutie", "Razvan", "Florentin", LocalDateTime.now());
        MessageTask taskGrade = new MessageTask(
                "3", "Nota Lab", "10", "Florentin", "Razvan", LocalDateTime.now());
        return new MessageTask[] {
                taskLaboratory, taskHomework, taskGrade
        };
    }

    private static void printMessageTasks(MessageTask[] messageTasks) {
        for (MessageTask messageTask : messageTasks) {
            System.out.println(messageTask);
        }
    }

    public static void run(Strategy taskRunnerStrategy) {
        MessageTask[] messageTasks = createMessageTasks();
        printMessageTasks(messageTasks);
        System.out.println();
        printMessageTasksViaStrategyTaskRunner(messageTasks, taskRunnerStrategy);
        System.out.println();
        printMessageTasksViaPrinterTaskRunner(messageTasks, taskRunnerStrategy);
        System.out.println();
        printMessageTasksViaDelayTaskRunner(messageTasks, taskRunnerStrategy);
        System.out.println();
    }

    private static void printMessageTasksViaDelayTaskRunner(MessageTask[] messageTasks, Strategy taskRunnerStrategy) {
        System.out.println("Delay Task Runner:");
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(taskRunnerStrategy);
        DelayTaskRunner delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
        for (MessageTask messageTask : messageTasks)
            delayTaskRunner.addTask(messageTask);
        delayTaskRunner.executeAll();
    }

    private static void printMessageTasksViaPrinterTaskRunner(MessageTask[] messageTasks, Strategy taskRunnerStrategy) {
        System.out.println("Printer Task Runner:");
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(taskRunnerStrategy);
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);
        for (MessageTask messageTask : messageTasks)
            printerTaskRunner.addTask(messageTask);
        printerTaskRunner.executeAll();
    }

    private static void printMessageTasksViaStrategyTaskRunner(MessageTask[] messageTasks, Strategy taskRunnerStrategy) {
        System.out.println("Strategy Task Runner:");
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(taskRunnerStrategy);
        for (MessageTask messageTask : messageTasks)
            strategyTaskRunner.addTask(messageTask);
        strategyTaskRunner.executeAll();
    }

    public static void sort(int[] array, sorter.Strategy sortStrategy) {
        System.out.println("Sorted array:");
        SortingTask sortingTask = new SortingTask("1", "Sortare", array, sortStrategy);
        sortingTask.execute();
    }
}
