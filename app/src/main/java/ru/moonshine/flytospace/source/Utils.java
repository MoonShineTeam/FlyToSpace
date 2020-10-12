package ru.moonshine.flytospace.source;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class Utils {
    public static void setFullScreenMode(Activity activity) {
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void startViewAnimation(Context context, View view, int animID) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animID));
    }

    public static void buttonAnimation(AppCompatActivity activity, Context context,
                                       int buttonID, int animID) {
        Animation animation = AnimationUtils.loadAnimation(context, animID);
        Button btn = activity.findViewById(buttonID);
        btn.startAnimation(animation);
    }

    public static void intentAnimation(Context context, Class<?> nextClass,
                                       int animInID, int animOutID) {
        Intent intent = new Intent(context, nextClass);
        ActivityOptions option  = ActivityOptions.makeCustomAnimation(context, animInID, animOutID);
        Bundle bundle = option.toBundle();
        context.startActivity(intent, bundle);
    }
}
