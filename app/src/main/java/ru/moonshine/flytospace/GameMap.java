package ru.moonshine.flytospace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.moonshine.flytospace.adapters.LevelsAdapter;
import ru.moonshine.flytospace.model.Level;
import ru.moonshine.flytospace.model.Task;
import ru.moonshine.flytospace.source.Utils;

public class GameMap extends AppCompatActivity {
    private ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Level> levels = new ArrayList<>();
    private LevelsAdapter levelsAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_map);
        Utils.setFullScreenMode(this);
        XmlResourceParser parser = getResources().getXml(R.xml.tasks);



        // MEDIUM LEVELS


        tasks = Utils.readXmlTasks(parser);
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);

        // Добавление уровней уровней
        levels.add(new Level(this, 0, tasks.get(0), getDrawable(R.drawable.gm_earth), 125, 125));
        levels.add(new Level(this, 1, tasks.get(1), getDrawable(R.drawable.gm_moon), 65, 65));
        levels.add(new Level(this, 2, tasks.get(2), getDrawable(R.drawable.gm_mars), 110, 110));
        levels.add(new Level(this, 3, tasks.get(3), getDrawable(R.drawable.gm_red_planet), 200, 200));
        levels.add(new Level(this, 4, tasks.get(4), getDrawable(R.drawable.gm_saturn), 250, 250));

        levels.add(new Level(this, 5, tasks.get(5), getDrawable(R.drawable.gm_uranus), 120, 120));
        levels.add(new Level(this, 6, tasks.get(6), getDrawable(R.drawable.gm_neptune), 110, 110));
        levels.add(new Level(this, 7, tasks.get(7), getDrawable(R.drawable.gm_yellow_planet), 100, 100));
        levels.add(new Level(this, 8, tasks.get(8), getDrawable(R.drawable.gm_green_planet), 120, 120));
        levels.add(new Level(this, 9, tasks.get(9), getDrawable(R.drawable.gm_purple_planet), 160, 160));

        levels.add(new Level(this, 10, tasks.get(10), getDrawable(R.drawable.gm_lightgreen_planet), 130, 130));
        levels.add(new Level(this, 11, tasks.get(11), getDrawable(R.drawable.gm_planet3), 120, 120));
        levels.add(new Level(this, 12, tasks.get(12), getDrawable(R.drawable.gm_planet5), 145, 145));
        levels.add(new Level(this, 13, tasks.get(13), getDrawable(R.drawable.gm_planet12), 155, 155));
        levels.add(new Level(this, 14, tasks.get(14), getDrawable(R.drawable.gm_black_hole), 130, 130));


        // Установка адаптера для уровней игровой карты
        RecyclerView recyclerView = findViewById(R.id.levels_list);
        levelsAdapter = new LevelsAdapter(this, levels, myPrefs);
        recyclerView.setAdapter(levelsAdapter);

        // Обновление данных итогового количества набранных очков
        updateTotalScore();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Обновление данных итогового количества набранных очков
        updateTotalScore();

        // Установка обновленного адаптера для уровней игровой карты
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        RecyclerView recyclerView = findViewById(R.id.levels_list);
        levelsAdapter = new LevelsAdapter(this, levels, myPrefs);
        recyclerView.setAdapter(levelsAdapter);
    }

    // Обновление итогового количества набранных очков
    @SuppressLint("SetTextI18n")
    public void updateTotalScore() {
        TextView totalScore = findViewById(R.id.total_score);
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        int score = 0;
        for (Task task : tasks) {
            final String PATH = "task" + task.getId() + "_score";
            score += myPrefs.getInt(PATH, 0);
        }
        totalScore.setText(score + "/" + tasks.size() * 3);
    }

    // Нажитие на стрелку назад, которое возвращает пользователя в главное меню
    public void onClickToMainMenu(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, MainMenu.class, R.anim.fade_in, R.anim.fade_out);
    }

}