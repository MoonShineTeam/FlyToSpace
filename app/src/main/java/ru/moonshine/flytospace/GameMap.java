package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.moonshine.flytospace.source.Utils;

public class GameMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_map);
        Utils.setFullScreenMode(this);
    }

    public void onLvl1Click(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(animation);
    }
}