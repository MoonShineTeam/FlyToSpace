package ru.moonshine.flytospace.source;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

public class Utils {
    public static void startViewAnimation(Context context, View view, int animID) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animID));
    }
}
