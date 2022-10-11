package model;

import utils.Constants;

import java.time.LocalDateTime;

public class MessageTask extends Task {
    private final String message;
    private final String from;
    private final String to;
    private final LocalDateTime date;

    public MessageTask(String taskID, String description, String message, String from, String to, LocalDateTime date) {
        super(taskID, description);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public void execute() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString() + "|message=" + message + "|from=" + from + "|to=" + to + "|date=" + date.format(Constants.DATE_TIME_FORMATTER);
    }
}
