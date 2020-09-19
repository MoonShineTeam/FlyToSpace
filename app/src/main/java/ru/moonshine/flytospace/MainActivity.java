package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.pm.ActivityInfo;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void btnStartOnClick(View view) {
        Intent intent = new Intent(this, GameMap.class);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        ActivityOptions option  = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in, R.anim.fade_out);
        Bundle bundle = option.toBundle();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.startAnimation(animation);
        startActivity(intent, bundle);
    }
}