package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import ru.moonshine.flytospace.source.Utils;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Utils.setFullScreenMode(this);
    }

    public void startGameLayoutOnClick(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, GameMap.class, R.anim.fade_in, R.anim.fade_out);
    }

    public void continueFameLayoutOnClick(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
    }

    public void startTestLayoutOnClick(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
    }

    public void backArrowOnClick(View view) {
        Utils.startViewAnimation(this, view, R.anim.scale);
        Utils.intentAnimation(this, StartScreen.class, R.anim.fade_in, R.anim.fade_out);
    }

}