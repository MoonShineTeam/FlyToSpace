package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setStartOffset(0);
        animation.setDuration(1000);
        View bg = findViewById(R.id.start_screen_layout);
        bg.startAnimation(animation);
    }

    public void bgLayoutOnClick(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        ActivityOptions option  = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in, R.anim.fade_out);
        /* Анимация кнопки, чтобы не забыть)))
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
            Button btnStart = findViewById(R.id.btnStart);
            btnStart.startAnimation(animation);
         */
        Bundle bundle = option.toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exitTitle)
                .setMessage(R.string.exitMessage)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }

}