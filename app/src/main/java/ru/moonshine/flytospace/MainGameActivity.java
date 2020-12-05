package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import ru.moonshine.flytospace.adapters.AnswersAdapter;
import ru.moonshine.flytospace.model.Task;
import ru.moonshine.flytospace.source.Utils;

public class MainGameActivity extends AppCompatActivity {
    private int id;
    private String taskType;
    private ArrayList<Integer> answers;
    private ArrayList<String> equations;
    private String style;
    private int trueAnswer;
    private Task task;

    private TextView taskTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ожидается запуск с передачей данных levelType:int
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        task = (Task) intent.getParcelableExtra("Task");
        id = task.getId();
        taskType = task.getTaskType();
        style = task.getStyle();
        answers = task.getAnswers();
        equations = task.getEquations();
        trueAnswer = task.getTrueAnswer();

        setContentView(R.layout.level_easy_layout);
        Utils.setFullScreenMode(this);
        taskTextView = findViewById(R.id.task_easy_textview);
        taskTextView.setText(task.getTaskText());
        //TODO: Здесь будет работа с графическими элементами

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.answers_list);
        AnswersAdapter adapter = new AnswersAdapter(this, answers);
        recyclerView.setAdapter(adapter);
    }
}