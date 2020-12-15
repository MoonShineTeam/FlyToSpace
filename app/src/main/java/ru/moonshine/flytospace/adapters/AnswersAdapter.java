package ru.moonshine.flytospace.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.moonshine.flytospace.R;
import ru.moonshine.flytospace.source.Utils;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private final ArrayList<Integer> answersList;
    private final LayoutInflater inflater;

    public AnswersAdapter(Context context, ArrayList<Integer> answersList) {
        this.answersList = answersList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_boxes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int answer = answersList.get(position);

        ItemsAdapter adapter = new ItemsAdapter(answer);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        @SuppressLint("ResourceType")
        ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.answer_box_items);
        }
    }

}
