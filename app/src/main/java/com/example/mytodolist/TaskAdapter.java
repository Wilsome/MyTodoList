package com.example.mytodolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private TaskDBHelper dbHelper;


    public TaskAdapter(Context context) {
        dbHelper = new TaskDBHelper(context);
        taskList = dbHelper.getAllTasks();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = taskList.get(position);

        // Bind task details to TextViews
        holder.textViewTaskName.setText(task.getName());
        holder.checkBoxTask.setChecked(task.isComplete());


        // Set OnClickListener for delete button/icon
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the task from the dataset
                taskList.remove(position);

                // Notify the adapter of the dataset change
                notifyDataSetChanged();
            }
        });

        // Set OnCheckedChangeListener for CheckBox
        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setComplete(isChecked);
            dbHelper.updateTask(task);
        });
    }


    // Method to update the dataset and notify RecyclerView of the change
    public void updateDataSet(List<Task> newTaskList) {
        this.taskList = newTaskList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        TextView textViewTaskName;
        ImageButton btnDelete;

        TaskViewHolder(View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            btnDelete = itemView.findViewById(R.id.buttonDeleteTask);
        }
    }
}

