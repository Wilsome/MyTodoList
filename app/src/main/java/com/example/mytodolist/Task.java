package com.example.mytodolist;

public class Task {
    private long id;
    private String name;
    private String description;
    private boolean complete;


    // Constructors
    public Task() {
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.complete = false; // By default, task is not complete
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
