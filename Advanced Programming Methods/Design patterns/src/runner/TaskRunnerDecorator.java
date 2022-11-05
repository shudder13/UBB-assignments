package runner;

import model.Task;

public abstract class TaskRunnerDecorator implements TaskRunner {
    protected final TaskRunner taskRunner;

    public TaskRunnerDecorator(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    @Override
    public void executeAll() {
        while (taskRunner.hasTask())
            executeOneTask();
    }

    @Override
    public void addTask(Task task) {
        taskRunner.addTask(task);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
