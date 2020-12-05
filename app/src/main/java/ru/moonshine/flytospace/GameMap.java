package ru.moonshine.flytospace;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;

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
    }

    public void onLvl1Click(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, tasks.get(0));
    }
}