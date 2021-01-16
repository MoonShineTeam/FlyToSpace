package ru.moonshine.flytospace.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Level {
    private float density; // Необходимо для перевода в dp
    public Task task;
    public int id;
    public Drawable levelImage;
    public float imageHeight;
    public float imageWidth;
    private boolean isEnded;

    public Level(Context context, int id, Task task, Drawable levelImage, float imageHeight, float imageWidth) {
        this.density = context.getResources().getDisplayMetrics().density;
        this.task = task;
        this.id = id;
        this.levelImage = levelImage;
        this.imageHeight = imageHeight * density; // Перевод dp в px
        this.imageWidth = imageWidth * density; // Перевод dp в px
        this.isEnded = false;
    }

    public void endLevel() {
        this.isEnded = true;
    }

    public boolean getIsEnded() {
        return isEnded;
    }
}
