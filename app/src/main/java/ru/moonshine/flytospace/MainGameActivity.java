package ru.moonshine.flytospace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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

    public static Task lastOpenedTask;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Обработка данных переданных через Intent
        Intent intent = getIntent();
        task = (Task) intent.getParcelableExtra("Task");
        id = task.getId();
        taskType = task.getTaskType();
        style = task.getStyle();
        answers = task.getAnswers();
        equations = task.getEquations();
        trueAnswer = task.getTrueAnswer();
        attempts = 3;

        // Инициализация View и установка для него параметров FullScreen
        setContentView(R.layout.level_easy_layout);
        Utils.setFullScreenMode(this);


        // Установка фонового изображения в зависимости от уровня сложности
        ImageView levelBG = findViewById(R.id.level_bg);
        if (task.getTaskType().equals("EASY")) {
            levelBG.setImageDrawable(getDrawable(R.drawable.bg_orange));
        }
        if (taskType.equals("MEDIUM")) {
            levelBG.setImageDrawable(getDrawable(R.drawable.bg_green));
            findViewById(R.id.text_box).setVisibility(View.INVISIBLE);
        }
        if (taskType.equals("HARD")) {
            levelBG.setImageDrawable(getDrawable(R.drawable.bg_mountains));
            findViewById(R.id.text_box).setVisibility(View.INVISIBLE);
        }


        // Установка текста задания
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
                        // Получение позции выбранного ответа
                        clickedPosition = position;

                        // Скрытие кнопки, при количестве попыток <= 0
                        if (attempts <= 0) {
                            findViewById(R.id.level_easy_btn_ready).setVisibility(View.INVISIBLE);
                            return;
                        }

                        // Установка видимости для кнопки "ГНотово"
                        findViewById(R.id.level_easy_btn_ready).setVisibility(View.VISIBLE);

                        // Установкка фонов для элементов
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

        // Установка адаптера
        AnswersAdapter adapter = new AnswersAdapter(this, task);
        recyclerView.setAdapter(adapter);

        // Отображение ID уровня в игре
        TextView attemptsTextView = findViewById(R.id.level_easy_attempts);
        attemptsTextView.setText(String.format("Уровень %d     Попытка %d/3", id, 4 - attempts));

        // Сохранение данных в SharedPreferences
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        final String PATH = "task" + task.getId() + "_score";
        if (myPrefs.getInt(PATH, 0) == 0 && myPrefs.getInt("last_task_id", -1) <= id) {
            lastOpenedTask = task;
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putInt("last_task_id", id);
            editor.commit();
        }

        // Получение уровнения в уровне
        String equation = equations.get(0).trim();

        // Нахождение всех элементов уравнения в задании
        Pattern numbersPattern = Pattern.compile("([\\d?]+).([\\d?]+).([\\d?]+)");
        Matcher matcher = numbersPattern.matcher(equation);

        if (matcher.find()) {
            // Получение кол-ва объектов первого элемента уравнения
            int firstNumber = !Objects.equals(matcher.group(1), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(1)))
                    : 0;
            // Установка объектов первого элемента уравнения в рамку
            setEquationItemsByLevelType(R.id.first_number_items, R.id.first_number_in_box, firstNumber);
//            RecyclerView firstNumberItems = findViewById(R.id.first_number_items);
//            ItemsAdapter firstNumberAdapter = new ItemsAdapter(firstNumber);
//            firstNumberItems.setAdapter(firstNumberAdapter);

            // Получение кол-ва объектов второго элемента уравнения
            int secondNumber = !Objects.equals(matcher.group(2), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(2)))
                    : 0;
            // Установка объектов второго элемента уравнения в рамку
            setEquationItemsByLevelType(R.id.second_number_items, R.id.second_number_in_box, secondNumber);
//            RecyclerView secondNumberItems = findViewById(R.id.second_number_items);
//            ItemsAdapter secondNumberAdapter = new ItemsAdapter(secondNumber);
//            secondNumberItems.setAdapter(secondNumberAdapter);

            // Получение кол-ва объектов третьего элемента уравнения
            int thirdNumber = !Objects.equals(matcher.group(3), "?")
                    ? Integer.parseInt(Objects.requireNonNull(matcher.group(3)))
                    : 0;
            // Установка объектов третьего элемента уравнения в рамку
            setEquationItemsByLevelType(R.id.third_number_items, R.id.third_number_in_box, thirdNumber);
//            RecyclerView thirdNumberItems = findViewById(R.id.third_number_items);
//            ItemsAdapter thirdNumberAdapter = new ItemsAdapter(thirdNumber);
//            thirdNumberItems.setAdapter(thirdNumberAdapter);
        }

        // Поиск знака уравнения по шаблону
        Pattern signPattern = Pattern.compile("[\\d?]+(.)[\\d?]+.[\\d?]+");
        matcher = signPattern.matcher(equation);

        // Устанвока знгака в TextView
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

    // Обработка нажития на кнопке "Готово"
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CommitPrefEdits", "DefaultLocale", "UseCompatLoadingForDrawables"})
    public void readyButtonClick(View view) {
        // Проигрывание анимации нажатия
        Utils.startViewAnimation(this, view, R.anim.scale);

        // Получение очков из SharedPreferences
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        final String PATH = "task" + task.getId() + "_score";
        SharedPreferences.Editor editor = myPrefs.edit();

        if (answers.indexOf(trueAnswer) == currentPressedItemIndex) {
            // Установка зеленой рамки для правильного ответа
            View rvView = ((RecyclerView) findViewById(R.id.answers_list)).getChildAt(clickedPosition);
            View answerBoxView = rvView.findViewById(R.id.answer_box_layout);
            answerBoxView.setBackground(getDrawable(R.drawable.correct_answer_box_shape));

            // Переменная для сообщения диалогового окна
            CharSequence textMessage = "";

            // Установка сообщения для диалогового окна в зависимости от количества попыток
            switch (attempts) {
                // Сообщение при оставшихся 3-х попытках
                case 3: {
                    task.setScore(3);
                    editor.putInt(PATH, 3);
                    editor.commit();
                    textMessage = getText(R.string.threeStarsMessage);
                    break;
                }

                // Сообщение при оставшихся 2-х попытках
                case 2: {
                    task.setScore(2);
                    editor.putInt(PATH, 2);
                    editor.commit();
                    textMessage = getText(R.string.twoStarsMessage);
                    break;
                }

                // Сообщение при оставшейся 1-ой попытке
                case 1: {
                    task.setScore(1);
                    editor.putInt(PATH, 1);
                    editor.commit();
                    textMessage = getText(R.string.oneStarMessage);
                    break;
                }
            }

            // Инициализация диалогового окна
            Dialog dialog = new Dialog(MainGameActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // Установка View в диалоговое окно
            dialog.setContentView(R.layout.task_end_dialog);

            // Установка звезд в диалоговом окне
            RatingBar ratingBar = dialog.findViewById(R.id.task_end_stars);
            ratingBar.setRating(task.getScore());

            // Установка текста в диалоговом окне
            TextView textView = dialog.findViewById(R.id.task_end_message);
            textView.setText(textMessage);

            // Установка параметров
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

                // Установка параметров
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

                dialog.show();
                return;
            }
        }

        // Вывод количества оставшихся попыток
        TextView attemptsTextView = findViewById(R.id.level_easy_attempts);
        attemptsTextView.setText(String.format("Уровень %d     Попытка %d/3", id, 4 - attempts));
    }

    // Нажитие на кнопку "Далее" в диалоговом окне
    public void onClickToNextTask(View view) {
            // Получение списка уровней
            XmlResourceParser parser = getResources().getXml(R.xml.tasks);
            ArrayList<Task> tasks = Utils.readXmlTasks(parser);

            // Проверка айди таска
            if (id >= tasks.size()) {
                // Проигрывание кнопки
                Utils.startViewAnimation(this, view, R.anim.scale);
            }
            else {
                // Переход на следующий уровень
                Utils.intentAnimation(this, MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, tasks.get(id));
            }
    }

    // Нажатие на кнопку "Заново" в диалоговом окне
    public void onClickRestartLevel1(View view) {
        // Проигрывание анимации нажатия
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, task);
    }

    public void setEquationItemsByLevelType(int recyclerViewID, int textViewID, int number) {
        RecyclerView numberItems = findViewById(recyclerViewID);
        TextView numberText = findViewById(textViewID);

        if (number == 0) {
            numberItems.setVisibility(View.INVISIBLE);
            numberText.setVisibility(View.INVISIBLE);
            return;
        }

        if (taskType.equals("EASY")) {
            numberItems.setVisibility(View.VISIBLE);
            numberText.setVisibility(View.INVISIBLE);

            ItemsAdapter numberAdapter = new ItemsAdapter(number);
            numberItems.setAdapter(numberAdapter);
        }
        else {
            numberItems.setVisibility(View.INVISIBLE);
            numberText.setVisibility(View.VISIBLE);

            numberText.setText(Integer.toString(number));
        }

    }
}