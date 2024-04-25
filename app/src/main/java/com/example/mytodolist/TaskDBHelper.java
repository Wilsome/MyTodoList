package com.example.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "task_db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COMPLETE = "complete";

    // SQL statement to create tasks table
    private static final String SQL_CREATE_TABLE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_COMPLETE + " INTEGER DEFAULT 0)";

    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tasks table
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tasks table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Recreate the database
        onCreate(db);
    }

    // CRUD Operations

    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_COMPLETE, task.isComplete() ? 1 : 0);
        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getWritableDatabase();
            cursor = db.query(TABLE_TASKS, null, null, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Task task = new Task();
                    task.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    task.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                    task.setComplete(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMPLETE)) == 1);
                    taskList.add(task);
                }
            }
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return taskList;
    }


    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_COMPLETE, task.isComplete() ? 1 : 0);
        return db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(taskId)});
        db.close();
    }
}


