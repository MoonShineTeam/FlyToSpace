package ru.moonshine.flytospace;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.moonshine.flytospace.model.Task;
import ru.moonshine.flytospace.source.Utils;

public class GameMap extends AppCompatActivity {
    private ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_map);
        Utils.setFullScreenMode(this);
        XmlResourceParser parser = getResources().getXml(R.xml.tasks);
        tasks = Utils.readXmlTasks(parser);
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        initTaskScores(myPrefs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RatingBar bar = (RatingBar) findViewById(R.id.lvl1_stars);
        SharedPreferences myPrefs = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        initTaskScores(myPrefs);
    }

    public void initTaskScores(SharedPreferences prefs) {
        for (Task task : tasks) {
            boolean flag = true;
            if (task.getTaskType().equalsIgnoreCase("easy")) {
                task.setScore(prefs.getInt("task" + task.getId() + "_score", 0));
            }
            RatingBar bar = (RatingBar) findViewById(R.id.lvl1_stars);
            final String PATH = "task" + task.getId() + "_score";
            bar.setRating(prefs.getInt(PATH, 0));
            bar = (RatingBar) findViewById(R.id.lvl2_stars);
            bar.setVisibility(View.INVISIBLE);
            bar = (RatingBar) findViewById(R.id.lvl3_stars);
            bar.setVisibility(View.INVISIBLE);
            bar = (RatingBar) findViewById(R.id.lvl4_stars);
            bar.setVisibility(View.INVISIBLE);
            bar = (RatingBar) findViewById(R.id.lvl5_stars);
            bar.setVisibility(View.INVISIBLE);
            }
    }

    public void onLvl1Click(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, tasks.get(0));
    }

    // Нажитие на стрелку назад, которое возвращает пользователя в главное меню
    public void onClickToMainMenu(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        super.onBackPressed();
    }

}