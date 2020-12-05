package ru.moonshine.flytospace.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Task implements Parcelable {
    private int id;
    private String taskText;
    private String taskType;
    private ArrayList<Integer> answers;
    private ArrayList<String> equations;
    private String style;
    private int trueAnswer;

    public Task() {}

    public Task(int id, String taskText, String taskType, ArrayList<Integer> answers,
                ArrayList<String> equations, String style, int trueAnswer) {
        this.id = id;
        this.taskText = taskText;
        this.taskType = taskType;
        this.answers = answers;
        this.equations = equations;
        this.style = style;
        this.trueAnswer = trueAnswer;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        taskText = in.readString();
        taskType = in.readString();
        answers = new ArrayList<>();
        in.readList(answers, null);
        equations = new ArrayList<>();
        in.readStringList(equations);
        style = in.readString();
        trueAnswer = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
    }

    public void setEquations(ArrayList<String> equations) {
        this.equations = equations;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTrueAnswer(int answer) {
        this.trueAnswer = answer;
    }

    public int getId() {
        return id;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTaskText() {
        return taskText;
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public ArrayList<String> getEquations() {
        return equations;
    }

    public String getStyle() {
        return style;
    }

    public int getTrueAnswer() {
        return trueAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskText);
        dest.writeString(taskType);
        dest.writeList(answers);
        dest.writeStringList(equations);
        dest.writeString(style);
        dest.writeInt(trueAnswer);
    }
}
