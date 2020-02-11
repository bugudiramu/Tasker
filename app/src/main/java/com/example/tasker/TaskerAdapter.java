package com.example.tasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskerAdapter extends ArrayAdapter<Tasker> {
    public TaskerAdapter(@NonNull Context context, ArrayList<Tasker> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Tasker currentTask = getItem(position);
        TextView title = listItemView.findViewById(R.id.title);
        TextView subtitle = listItemView.findViewById(R.id.subtitle);
        Button addTodoButton = listItemView.findViewById(R.id.addTodoBtn);

//        listItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_LONG).show();
//
//            }
//        });

        assert currentTask != null;
        title.setText(currentTask.getTodoTitle());
        subtitle.setText(currentTask.getTodoSubtitle());
        return listItemView;
    }
}
