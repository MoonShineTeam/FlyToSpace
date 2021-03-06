package ru.moonshine.flytospace.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.moonshine.flytospace.R;
import ru.moonshine.flytospace.model.Task;
import ru.moonshine.flytospace.source.Utils;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private final Task task;
    private final ArrayList<Integer> answersList;
    private final LayoutInflater inflater;

    public AnswersAdapter(Context context, Task task) {
        this.task = task;
        this.answersList = task.getAnswers();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_boxes, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int answer = answersList.get(position);

        if (task.getTaskType().equals("EASY")) {
            holder.numberInBox.setVisibility(View.INVISIBLE);
            holder.recyclerView.setVisibility(View.VISIBLE);

            ItemsAdapter adapter = new ItemsAdapter(answer);
            holder.recyclerView.setAdapter(adapter);
        }
        else {
            holder.recyclerView.setVisibility(View.INVISIBLE);
            holder.numberInBox.setVisibility(View.VISIBLE);

            holder.numberInBox.setText(Integer.toString(answer));
        }
    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView numberInBox;

        @SuppressLint("ResourceType")
        ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.answer_box_items);
            numberInBox = view.findViewById(R.id.number_in_box);
        }
    }

}
