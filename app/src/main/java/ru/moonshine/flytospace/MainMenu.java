package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void startGameLayoutOnClick(View view) {
        layoutClickAnimation(view);
        Intent intent = new Intent(this, GameMap.class);
        ActivityOptions option  = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in, R.anim.fade_out);
        Bundle bundle = option.toBundle();
        startActivity(intent, bundle);
    }

    public void continueFameLayoutOnClick(View view) {
        layoutClickAnimation(view);
    }

    public void startTestLayoutOnClick(View view) {
        layoutClickAnimation(view);
    }

    public void backArrowOnClick(View view) {
        layoutClickAnimation(view);
        super.onBackPressed();
    }

    private void layoutClickAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(animation);
    }

}