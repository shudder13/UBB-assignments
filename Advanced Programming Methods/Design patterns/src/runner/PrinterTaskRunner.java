package runner;

import utils.Constants;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends TaskRunnerDecorator {
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
        displayTime();
    }

    private void displayTime() {
        System.out.println("Task executat la ora " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER) + ".");
    }
}
