package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Button btnScale = (Button)findViewById(R.id.btnStart);
        btnScale.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(final View v) {
                v.startAnimation(animScale);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        setContentView(R.layout.game_map);
                    }
                }, 250);
            }
        });
    }

    public void btnStartOnClick(View view) {
        setContentView(R.layout.game_map);
    }
}