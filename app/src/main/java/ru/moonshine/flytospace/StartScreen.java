package ru.moonshine.flytospace;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import ru.moonshine.flytospace.source.Utils;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        Utils.setFullScreenMode(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setStartOffset(0);
        animation.setDuration(500);
        View bg = findViewById(R.id.BGLayout);
        bg.startAnimation(animation);

        TextView textView = findViewById(R.id.tapToContinueText);
        textView.startAnimation(tapToContinueScaleAnimation());
    }

    public void bgLayoutOnClick(View view) {
        TextView textView = findViewById(R.id.tapToContinueText);
        Utils.intentAnimation(this, MainMenu.class, R.anim.fade_in, R.anim.fade_out);
        textView.clearAnimation();
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

    @Override
    protected void onRestart() {
        TextView textView = findViewById(R.id.tapToContinueText);
        textView.startAnimation(tapToContinueScaleAnimation());
        super.onRestart();
    }

    private Animation tapToContinueScaleAnimation() {
        Animation anim = new ScaleAnimation(1,0.9f,
                1,0.9f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(1000);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }
}