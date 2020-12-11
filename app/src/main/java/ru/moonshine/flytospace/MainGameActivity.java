package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.moonshine.flytospace.adapters.ItemsAdapter;
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
        taskTextView = findViewById(R.id.task_easy_text_view);
        taskTextView.setText(task.getTaskText());
        //TODO: Здесь будет работа с графическими элементами

        RecyclerView recyclerView = findViewById(R.id.answers_list);
        AnswersAdapter adapter = new AnswersAdapter(this, answers);
        recyclerView.setAdapter(adapter);

        String equation = equations.get(0).trim();

        // Нахождение всех элементов уравнения
        Pattern numbersPattern = Pattern.compile("([\\d?]+).([\\d?]+).([\\d?]+)");
        Matcher matcher = numbersPattern.matcher(equation);

        if (matcher.find()) {
            // Получение кол-ва объектов первого элемента уравнения
            int firstNumber = !Objects.equals(matcher.group(1), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(1)))
                    : 0;
            // Установка объектов первого элемента уравнения в рамку
            RecyclerView firstNumberItems = findViewById(R.id.first_number_items);
            ItemsAdapter firstNumberAdapter = new ItemsAdapter(firstNumber);
            firstNumberItems.setAdapter(firstNumberAdapter);

            // Получение кол-ва объектов второго элемента уравнения
            int secondNumber = !Objects.equals(matcher.group(2), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(2)))
                    : 0;
            // Установка объектов второго элемента уравнения в рамку
            RecyclerView secondNumberItems = findViewById(R.id.second_number_items);
            ItemsAdapter secondNumberAdapter = new ItemsAdapter(secondNumber);
            secondNumberItems.setAdapter(secondNumberAdapter);

            // Получение кол-ва объектов третьего элемента уравнения
            int thirdNumber = !Objects.equals(matcher.group(3), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(3)))
                    : 0;
            // Установка объектов третьего элемента уравнения в рамку
            RecyclerView thirdNumberItems = findViewById(R.id.third_number_items);
            ItemsAdapter thirdNumberAdapter = new ItemsAdapter(thirdNumber);
            thirdNumberItems.setAdapter(thirdNumberAdapter);
        }

        // Поиск знака уравнения по шаблону
        Pattern signPattern = Pattern.compile("[\\d?]+(.)[\\d?]+.[\\d?]+");
        matcher = signPattern.matcher(equation);

        if (matcher.find()) {
            String sign = matcher.group(1);
            TextView signTextView = findViewById(R.id.equation_sign);
            signTextView.setText(sign);
        }
    }

    // Нажитие на стрелку назад, которое возвращает пользователя на игровую карту
    public void onClickToGameMap(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        super.onBackPressed();
    }

}