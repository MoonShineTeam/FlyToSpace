package ru.moonshine.flytospace.model;

import java.util.ArrayList;

public class Task {
    private final int id;
    private final String taskText;
    private final int time;
    private final int score;
    private final Boolean isOpen;
    private final ArrayList<String> answers;

    public Task(int id, String taskText, ArrayList<String> answers) {
        this.id = id;
        this.taskText = taskText;
        this.answers = answers;
        this.time = 0;
        this.score = 0;
        this.isOpen = false;
    }

    public int getId() {
        return id;
    }

    public String getTaskText() {
        return taskText;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}
