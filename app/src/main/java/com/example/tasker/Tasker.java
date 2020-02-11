package com.example.tasker;


public class Tasker {
    private String todoTitle;
    private String todoSubtitle;

    Tasker(String title, String subtitle) {
        this.todoTitle = title;
        this.todoSubtitle = subtitle;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public String getTodoSubtitle() {
        return todoSubtitle;
    }

}
