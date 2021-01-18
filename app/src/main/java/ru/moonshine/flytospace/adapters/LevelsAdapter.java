package ru.moonshine.flytospace.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.moonshine.flytospace.MainGameActivity;
import ru.moonshine.flytospace.R;
import ru.moonshine.flytospace.model.Level;
import ru.moonshine.flytospace.source.Utils;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Level> levelsList;
    private final LayoutInflater inflater;
    private final SharedPreferences prefs;

    public LevelsAdapter(Context context, ArrayList<Level> levelsList, SharedPreferences prefs) {
        this.context = context;
        this.levelsList = levelsList;
        this.inflater = LayoutInflater.from(context);
        this.prefs = prefs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_map_level, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Получение уровня
        final Level level = levelsList.get(position);

        // Установка изображения заданного размера
        holder.image.setImageDrawable(level.levelImage);
        holder.image.getLayoutParams().height = (int) level.imageHeight;
        holder.image.getLayoutParams().width = (int) level.imageWidth;
        holder.image.requestLayout();

        // Установка клика для перехода на конкретный task
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startViewAnimation(v.getContext(), v, R.anim.scale);
                Utils.intentAnimation(v.getContext(), MainGameActivity.class, R.anim.fade_in, R.anim.fade_out, level.task);
            }
        });

        final String PATH = "task" + level.task.getId() + "_score";
        holder.stars.setRating(prefs.getInt(PATH, 0));

//        if (level.task.getTaskType().equals("EASY")) {
//            holder.stars = new RatingBar(context, null, R.style.EasyRatingBar);
//        }

        // Установка ID уровня

        String taskType = level.task.getTaskType();
        if (taskType.equals("EASY")) {
            holder.levelID.setTextColor(context.getResources().getColor(R.color.smoothGreen));
        }
        else if (taskType.equals("MEDIUM")) {
            holder.levelID.setTextColor(context.getResources().getColor(R.color.yellow));
        }
        else {
            holder.levelID.setTextColor(context.getResources().getColor(R.color.darkRed));
        }
        holder.levelID.setText(Integer.toString(level.task.getId()));
    }

    @Override
    public int getItemCount() {
        return levelsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        RatingBar stars;
        TextView levelID;

        @SuppressLint("ResourceType")
        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.level_image); // Картинка уровня
            stars = view.findViewById(R.id.level_stars); // Звезды за уровень
            levelID = view.findViewById(R.id.level_id); // ID уровня
        }
    }

}

