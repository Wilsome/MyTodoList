package com.example.mytodolist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    public void addTask(View view) {

// Initialize TaskDBHelper
        TaskDBHelper dbHelper = new TaskDBHelper(this);

// Find views for user input (e.g., EditText for task name and description, Button for adding task)
        EditText editTextTaskName = findViewById(R.id.editTextText);
        EditText editTextTaskDescription = findViewById(R.id.editTextText2);

// Initialize RecyclerView and its adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TaskAdapter adapter = new TaskAdapter(this); // Initialize adapter with context
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Set OnClickListener for the "Add Task" button

            // Get task name and description from EditText fields
            String taskName = editTextTaskName.getText().toString().trim();
            String taskDescription = editTextTaskDescription.getText().toString().trim();

            // Check if task name is not empty
            if (!taskName.isEmpty()) {
                // Create a new Task object with the entered task details
                Task newTask = new Task(taskName, taskDescription);

                // Add the new task to the database using TaskDBHelper
                long taskId = dbHelper.addTask(newTask);

                // Check if the task was added successfully
                if (taskId != -1) {
                    // Clear EditText fields after adding the task
                    editTextTaskName.getText().clear();
                    editTextTaskDescription.getText().clear();

                    // Refresh RecyclerView by fetching tasks from the database
                    adapter.updateDataSet(dbHelper.getAllTasks());
                } else {
                    // Handle database insertion failure
                    Toast.makeText(this, "Failed to add task to database", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display a message to prompt the user to enter a task name
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            }
    }
}