package ru.moonshine.flytospace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.moonshine.flytospace.adapters.ItemsAdapter;
import ru.moonshine.flytospace.adapters.AnswersAdapter;
import ru.moonshine.flytospace.listeners.RecyclerItemClickListener;
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
    private int currentPressedItemIndex;
    private int attempts = 3;
    private int clickedPosition;
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
        attempts = 3;

        setContentView(R.layout.level_easy_layout);
        Utils.setFullScreenMode(this);

        taskTextView = findViewById(R.id.task_easy_text_view);
        taskTextView.setText(task.getTaskText());
        //TODO: Здесь будет работа с графическими элементами

        // Скрытие кнопки "готово"
        findViewById(R.id.level_easy_btn_ready).setVisibility(View.INVISIBLE);

        final RecyclerView recyclerView = findViewById(R.id.answers_list);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
                    @Override
                    public void onItemClick(View view, int position) {

                        clickedPosition = position;

                        if (attempts <= 0) {
                            findViewById(R.id.level_easy_btn_ready).setVisibility(View.INVISIBLE);
                            return;
                        }

                        // Открытие кнопки "готово"
                        findViewById(R.id.level_easy_btn_ready).setVisibility(View.VISIBLE);

                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            if (i == position)
                            {
                                View answerBoxView = view.findViewById(R.id.answer_box_layout);
                                answerBoxView.setBackground(getDrawable(R.drawable.answer_box_shape));
                                currentPressedItemIndex = i;
                            }
                            else
                            {
                                View rvView = recyclerView.getChildAt(i);
                                rvView.findViewById(R.id.answer_box_layout).setBackgroundResource(0);
                            }
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
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
        Utils.intentAnimation(this, GameMap.class, R.anim.fade_in, R.anim.fade_out, task);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CommitPrefEdits", "DefaultLocale", "UseCompatLoadingForDrawables"})
    public void readyButtonClick(View view) {
        // Проигрывание анимации нажатия
        Utils.startViewAnimation(this, view, R.anim.scale);

        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        final String PATH = "task" + task.getId() + "_score";
        SharedPreferences.Editor editor = myPrefs.edit();
        if (answers.indexOf(trueAnswer) == currentPressedItemIndex) {

            // Установка зеленой рамки для правильного ответа
            View rvView = ((RecyclerView) findViewById(R.id.answers_list)).getChildAt(clickedPosition);
            View answerBoxView = rvView.findViewById(R.id.answer_box_layout);
            answerBoxView.setBackground(getDrawable(R.drawable.correct_answer_box_shape));

            CharSequence textMessage = "";

            switch (attempts) {
                case 3: {
                    task.setScore(3);
                    editor.putInt(PATH, 3);
                    editor.commit();
                    textMessage = getText(R.string.threeStarsMessage);
                    break;
                }
                case 2: {
                    task.setScore(2);
                    editor.putInt(PATH, 2);
                    editor.commit();
                    textMessage = getText(R.string.twoStarsMessage);
                    break;
                }
                case 1: {
                    task.setScore(1);
                    editor.putInt(PATH, 1);
                    editor.commit();
                    textMessage = getText(R.string.oneStarMessage);
                    break;
                }
            }

            Dialog dialog = new Dialog(MainGameActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.task_end_dialog);

            // Установка звезд в диалоговом окне
            RatingBar ratingBar = dialog.findViewById(R.id.task_end_stars);
            ratingBar.setRating(task.getScore());

            // Установка текста в диалоговом окне
            TextView textView = dialog.findViewById(R.id.task_end_message);
            textView.setText(textMessage);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад
            dialog.show();
        }
        else {
            // Установка красной рамки для неправильного ответа
            View rvView = ((RecyclerView) findViewById(R.id.answers_list)).getChildAt(clickedPosition);
            View answerBoxView = rvView.findViewById(R.id.answer_box_layout);
            answerBoxView.setBackground(getDrawable(R.drawable.incorrect_answer_box_shape));

            attempts--;

            if (attempts == 0) {
                findViewById(R.id.level_easy_btn_ready).setVisibility(View.INVISIBLE);
                task.setScore(0);
                editor.putInt(PATH, 0);
                editor.commit();

                // Создание диалогового окна
                Dialog dialog = new Dialog(MainGameActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.task_end_dialog);

                // Установка звезд в диалоговом окне
                RatingBar ratingBar = dialog.findViewById(R.id.task_end_stars);
                ratingBar.setRating(task.getScore());

                // Установка текста в диалоговом окне
                TextView textView = dialog.findViewById(R.id.task_end_message);
                textView.setText(getText(R.string.zeroStarsMessage));

                //
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад
                dialog.show();

                return;
            }
        }

        TextView attemptsTextView = findViewById(R.id.level_easy_attempts);
        attemptsTextView.setText(String.format("Попытка %d/3", 4 - attempts));
    }

    public void onClickToNextTask(View view) {
        // Проигрывание анимации нажатия
        Utils.startViewAnimation(this, view, R.anim.scale);
    }

    public void onClickRestartLevel1(View view) {
        // Проигрывание анимации нажатия
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, task);
    }
}